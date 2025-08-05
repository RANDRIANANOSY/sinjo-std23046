package hei.tsinjo.infrastructure.repository;
import hei.tsinjo.domain.model.Payment;
import hei.tsinjo.domain.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    /**
     * Trouve tous les paiements par statut
     */
    List<Payment> findByStatus(PaymentStatus status);

    /**
     * Trouve tous les paiements en cours de vérification
     */
    default List<Payment> findVerifyingPayments() {
        return findByStatus(PaymentStatus.VERIFYING);
    }
}
