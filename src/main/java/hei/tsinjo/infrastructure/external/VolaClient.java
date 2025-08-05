package hei.tsinjo.infrastructure.external;
import hei.tsinjo.domain.model.Payment;
import hei.tsinjo.domain.model.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Component
public class VolaClient {

    private final WebClient webClient;
    private final String apiKey;

    public VolaClient(@Value("${vola.api.base-url}") String baseUrl,
                      @Value("${vola.api.key}") String apiKey) {
        this.webClient = (WebClient) WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("X-API-Key", apiKey)
                .build();
        this.apiKey = apiKey;
    }

    // IMPORTANT: Cette méthode DOIT retourner Mono<Payment>
    public Mono<Payment> verifyPayment(String paymentId) {
        return webClient
                .get()
                .uri("/payments/{id}", paymentId)
                .retrieve()
                .bodyToMono(VolaPaymentResponse.class)
                .map(this::mapToPayment)
                .onErrorReturn(createFailedPayment(paymentId));
    }

    private Payment mapToPayment(VolaPaymentResponse response) {
        return Payment.builder()
                .id(response.getId())
                .amount(response.getAmount())
                .paymentDate(response.getPaymentDate())
                .paymentMethod(response.getPaymentMethod())
                .status(PaymentStatus.valueOf(response.getStatus()))
                .build();
    }

    private Payment createFailedPayment(String paymentId) {
        return (Payment) Payment.builder()
                .id(paymentId)
                .data(PaymentStatus.FAILED)
                .comment(String.valueOf(LocalDateTime.now()))
                .build();
    }

    // Classe DTO pour la réponse Vola
    @Setter
    @Getter
    public static class VolaPaymentResponse {
        // Getters et setters
        private String id;
        private Double amount;
        private LocalDateTime paymentDate;
        private String paymentMethod;
        private String status;

    }
}
