package hei.tsinjo.domain.service;


import hei.tsinjo.domain.model.Payment;
import hei.tsinjo.domain.model.PaymentStatus;
import hei.tsinjo.infrastructure.external.VolaClient;
import hei.tsinjo.infrastructure.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class VolaServiceTest {

    @Mock
    private VolaClient volaClient;

    @Mock
    private PaymentRepository paymentRepository;

    private VolaService volaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        volaService = new VolaService(volaClient, (hei.tsinjo.domain.repository.PaymentRepository) paymentRepository);
    }

    @Test
    void should_verify_payment_successfully() {
        // Given
        String paymentId = "pay_test_001";
        Payment succeededPayment = new Payment(paymentId, 50.0, LocalDateTime.now(),
                "Mobile Money", PaymentStatus.SUCCEEDED);

        when(volaClient.verifyPayment(paymentId))
                .thenReturn(Mono.just(succeededPayment));
        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(succeededPayment);

        // When
        volaService.verifyPaymentAsync(paymentId);

        // Then
        // Attendre un peu pour que l'opération asynchrone se termine
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        verify(volaClient, atLeastOnce()).verifyPayment(paymentId);
        verify(paymentRepository, atLeastOnce()).save(any(Payment.class));
    }

    @Test
    void should_handle_payment_verification_failure() {
        // Given
        String paymentId = "pay_test_failed";

        when(volaClient.verifyPayment(paymentId))
                .thenReturn(Mono.error(new RuntimeException("API Error")));

        // When
        volaService.verifyPaymentAsync(paymentId);

        // Then
        // L'erreur doit être gérée gracieusement
        verify(volaClient, atLeastOnce()).verifyPayment(paymentId);
    }

    @Test
    void should_check_pending_payments_periodically() {
        // Given
        Payment pendingPayment1 = new Payment("pay_001", 25.0, LocalDateTime.now(),
                "Mobile Money", PaymentStatus.VERIFYING);
        Payment pendingPayment2 = new Payment("pay_002", 30.0, LocalDateTime.now(),
                "Carte bancaire", PaymentStatus.VERIFYING);

        List<Payment> pendingPayments = Arrays.asList(pendingPayment1, pendingPayment2);

        when(paymentRepository.findByStatus(PaymentStatus.VERIFYING))
                .thenReturn(pendingPayments);
        when(volaClient.verifyPayment(anyString()))
                .thenReturn(Mono.just(new Payment()));

        // When
        volaService.checkPendingPayments();

        // Then
        verify(paymentRepository).findByStatus(PaymentStatus.VERIFYING);
        verify(volaClient, times(2)).verifyPayment(anyString());
    }
}

