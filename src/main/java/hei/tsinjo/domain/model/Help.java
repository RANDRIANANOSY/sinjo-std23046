package hei.tsinjo.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "helps")
public class Help {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "beneficiary_id", nullable = false)
    private Beneficiary beneficiary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(nullable = false, length = 1000)
    private String accidentDescription;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Constructeurs
    public Help() {
        this.createdAt = LocalDateTime.now();
    }

    public Help(Beneficiary beneficiary, Payment payment, String accidentDescription) {
        this();
        this.beneficiary = beneficiary;
        this.payment = payment;
        this.accidentDescription = accidentDescription;
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Beneficiary getBeneficiary() { return beneficiary; }
    public void setBeneficiary(Beneficiary beneficiary) { this.beneficiary = beneficiary; }
    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }
    public String getAccidentDescription() { return accidentDescription; }
    public void setAccidentDescription(String accidentDescription) { this.accidentDescription = accidentDescription; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

