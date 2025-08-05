package hei.tsinjo.infrastructure.repository;

import hei.tsinjo.domain.model.Help;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelpRepository extends JpaRepository<Help, Long> {

    /**
     * Trouve toutes les aides triées par date de création décroissante
     */
    List<Help> findAllByOrderByCreatedAtDesc();

    /**
     * Trouve toutes les aides d'un bénéficiaire spécifique
     */
    List<Help> findByBeneficiaryEmailOrderByCreatedAtDesc(String email);

    /**
     * Trouve toutes les aides d'un bénéficiaire par son ID
     */
    List<Help> findByBeneficiaryIdOrderByCreatedAtDesc(Long beneficiaryId);

    /**
     * Calcule la somme totale de toutes les aides avec statut SUCCEEDED
     */
    @Query("SELECT COALESCE(SUM(p.amount), 0.0) FROM Help h " +
            "JOIN h.payment p WHERE p.status = 'SUCCEEDED'")
    Double sumTotalHelps();

    /**
     * Compte le nombre total d'aides
     */
    long count();
}
