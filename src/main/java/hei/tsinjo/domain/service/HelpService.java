package hei.tsinjo.domain.service;

import hei.tsinjo.domain.model.Help;
import hei.tsinjo.infrastructure.repository.HelpRepository;
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
public class HelpService {

    private final HelpRepository helpRepository;

    /**
     * Récupère toutes les aides triées par date de création décroissante
     */
    public List<Help> findAllHelps() {
        log.info("Récupération de toutes les aides");
        return helpRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * Sauvegarde une nouvelle aide
     */
    public Help saveHelp(Help help) {
        log.info("Sauvegarde d'une nouvelle aide pour le bénéficiaire: {}",
                help.getBeneficiary().getEmail());

        Help savedHelp = helpRepository.save(help);

        log.info("Aide sauvegardée avec l'ID: {}", savedHelp.getId());
        return savedHelp;
    }

    /**
     * Trouve une aide par son ID
     */
    public Optional<Help> findHelpById(Long id) {
        log.info("Recherche de l'aide avec l'ID: {}", id);
        return helpRepository.findById(id);
    }

    /**
     * Trouve toutes les aides d'un bénéficiaire
     */
    public List<Help> findHelpsByBeneficiaryEmail(String email) {
        log.info("Recherche des aides pour le bénéficiaire: {}", email);
        return helpRepository.findByBeneficiaryEmailOrderByCreatedAtDesc(email);
    }

    /**
     * Calcule le montant total des aides
     */
    public Double getTotalHelps() {
        log.info("Calcul du montant total des aides");
        return helpRepository.sumTotalHelps();
    }
}
