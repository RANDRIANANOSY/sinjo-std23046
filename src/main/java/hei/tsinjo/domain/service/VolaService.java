package hei.tsinjo.domain.service;

import hei.tsinjo.domain.model.Payment;
import hei.tsinjo.domain.model.PaymentStatus;
import hei.tsinjo.domain.repository.PaymentRepository;
import hei.tsinjo.infrastructure.external.VolaClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VolaService {

    private final VolaClient volaClient;
    private final PaymentRepository paymentRepository;

    @Async
    public void verifyPaymentAsync(String paymentId) {
        log.info("Démarrage de la vérification asynchrone pour le paiement: {}", paymentId);

        Mono.fromCallable(() -> verifyPaymentWithRetry(paymentId))
                .delayElement(Duration.ofSeconds(2))
                .subscribe(
                        payment -> {
                            paymentRepository.save(payment);
                            log.info("Paiement {} vérifié avec statut: {}", paymentId, payment.getStatus());
                        },
                        error -> log.error("Erreur lors de la vérification du paiement {}: {}", paymentId, error.getMessage())
                );
    }

    private Payment verifyPaymentWithRetry(String paymentId) {
        int maxRetries = 10;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try {
                Payment payment = volaClient.verifyPayment(paymentId).block();

                if (payment != null && payment.getStatus() != PaymentStatus.VERIFYING) {
                    return payment;
                }

                Thread.sleep(5000);
                retryCount++;
                log.info("Tentative {} de vérification pour le paiement {}", retryCount, paymentId);

            } catch (Exception e) {
                log.warn("Erreur lors de la vérification du paiement {} (tentative {}): {}",
                        paymentId, retryCount + 1, e.getMessage());
                retryCount++;

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        Payment failedPayment = new Payment();
        failedPayment.setId(paymentId);
        failedPayment.setStatus(PaymentStatus.FAILED);
        log.error("Échec de la vérification du paiement {} après {} tentatives", paymentId, maxRetries);
        return failedPayment;
    }

    @Scheduled(fixedRate = 30000)
    public void checkPendingPayments() {
        List<Payment> verifyingPayments = paymentRepository.findByStatus(PaymentStatus.VERIFYING);

        log.info("Vérification de {} paiements en attente", verifyingPayments.size());

        verifyingPayments.forEach(payment -> verifyPaymentAsync(payment.getId()));
    }
}