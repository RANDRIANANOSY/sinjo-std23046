package hei.tsinjo.domain.service;

import hei.tsinjo.domain.model.Donation;
import hei.tsinjo.infrastructure.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DonationService {

    private final DonationRepository donationRepository;

    /**
     * Récupère tous les dons triés par date de création décroissante
     */
    public List<Donation> findAllDonations() {
        log.info("Récupération de tous les dons");
        return donationRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * Sauvegarde un nouveau don
     */
    public Donation saveDonation(Donation donation) {
        log.info("Sauvegarde d'un nouveau don pour le donateur: {}",
                donation.getDonor().getEmail());

        Donation savedDonation = donationRepository.save(donation);

        log.info("Don sauvegardé avec l'ID: {}", savedDonation.getId());
        return savedDonation;
    }

    /**
     * Trouve un don par son ID
     */
    public Optional<Donation> findDonationById(Long id) {
        log.info("Recherche du don avec l'ID: {}", id);
        return donationRepository.findById(id);
    }

    /**
     * Trouve tous les dons d'un donateur
     */
    public List<Donation> findDonationsByDonorEmail(String email) {
        log.info("Recherche des dons pour le donateur: {}", email);
        return donationRepository.findByDonorEmailOrderByCreatedAtDesc(email);
    }

    /**
     * Calcule le montant total des dons
     */
    public Double getTotalDonations() {
        log.info("Calcul du montant total des dons");
        return donationRepository.sumTotalDonations();
    }
}
