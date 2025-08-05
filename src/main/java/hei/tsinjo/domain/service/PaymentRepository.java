package hei.tsinjo.domain.service;

import hei.tsinjo.domain.model.Payment;
import hei.tsinjo.domain.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findByStatus(PaymentStatus status);
}
