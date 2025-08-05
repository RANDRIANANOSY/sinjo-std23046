package hei.tsinjo.infrastructure.repository;

import hei.tsinjo.domain.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    /**
     * Trouve tous les dons triés par date de création décroissante
     */
    List<Donation> findAllByOrderByCreatedAtDesc();

    /**
     * Trouve tous les dons d'un donateur spécifique
     */
    List<Donation> findByDonorEmailOrderByCreatedAtDesc(String email);

    /**
     * Trouve tous les dons d'un donateur par son ID
     */
    List<Donation> findByDonorIdOrderByCreatedAtDesc(Long donorId);

    /**
     * Calcule la somme totale de tous les dons avec statut SUCCEEDED
     */
    @Query("SELECT COALESCE(SUM(p.amount), 0.0) FROM Donation d " +
            "JOIN d.payment p WHERE p.status = 'SUCCEEDED'")
    Double sumTotalDonations();

    /**
     * Compte le nombre total de dons
     */
    long count();
}
