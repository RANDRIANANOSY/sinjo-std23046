package hei.tsinjo.domain.model;

import jakarta.persistence.*;
import jakarta.ws.rs.sse.OutboundSseEvent;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "payments")
public class Payment {
    // Getters et setters complets
    @Id
    private String id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    // Constructeurs, getters, setters
    public Payment() {}

    public Payment(String id, Double amount, LocalDateTime paymentDate,
                   String paymentMethod, PaymentStatus status) {
        this.id = id;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public static OutboundSseEvent.Builder builder() {
        return null;
    }

    public void setId(String id) { this.id = id; }

    public void setAmount(Double amount) { this.amount = amount; }

    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public void setStatus(PaymentStatus status) { this.status = status; }
}


