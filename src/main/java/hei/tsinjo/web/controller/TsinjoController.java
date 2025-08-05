package hei.tsinjo.web.controller;
import hei.tsinjo.domain.model.*;
import hei.tsinjo.domain.service.DonationService;
import hei.tsinjo.domain.service.HelpService;
import hei.tsinjo.domain.service.VolaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TsinjoController {

    private final DonationService donationService;
    private final HelpService helpService;
    private final VolaService volaService;

    @GetMapping("/")
    public String index(Model model) {
        log.info("Affichage de la page principale Tsinjo");

        // Récupérer tous les dons et aides
        List<Donation> donations = donationService.findAllDonations();
        List<Help> helps = helpService.findAllHelps();

        // Créer une liste unifiée pour l'affichage anti-chronologique
        List<TransactionView> transactions = new ArrayList<>();

        // Ajouter les dons
        donations.forEach(donation ->
                transactions.add(new TransactionView(
                        donation.getCreatedAt(),
                        "DON",
                        donation.getDonor().getFullName(),
                        donation.getDonor().getEmail(),
                        donation.getPayment().getAmount(),
                        donation.getPayment().getPaymentMethod(),
                        donation.getPayment().getStatus().toString(),
                        null // pas de description pour les dons
                )));

        // Ajouter les aides
        helps.forEach(help ->
                transactions.add(new TransactionView(
                        help.getCreatedAt(),
                        "AIDE",
                        help.getBeneficiary().getFullName(),
                        help.getBeneficiary().getEmail(),
                        help.getPayment().getAmount(),
                        help.getPayment().getPaymentMethod(),
                        help.getPayment().getStatus().toString(),
                        help.getAccidentDescription()
                )));

        // Trier par ordre anti-chronologique
        transactions.sort(Comparator.comparing(TransactionView::getDate).reversed());

        model.addAttribute("transactions", transactions);
        model.addAttribute("donationForm", new DonationForm());

        log.info("Page principale chargée avec {} transactions", transactions.size());
        return "index";
    }

    @PostMapping("/donations")
    public String submitDonation(@ModelAttribute DonationForm form) {
        log.info("Soumission d'un nouveau don pour: {}", form.getDonorEmail());

        try {
            // Créer le donateur avec Lombok Builder
            Donor donor = Donor.builder()
                    .email(form.getDonorEmail())
                    .fullName(form.getDonorFullName())
                    .build();

            // Créer le paiement en état VERIFYING avec Lombok Builder
            Payment payment = Payment.builder()
                    .id(form.getPaymentId())
                    .amount(form.getAmount())
                    .paymentDate(LocalDateTime.now())
                    .paymentMethod(form.getPaymentMethod())
                    .status(PaymentStatus.VERIFYING)
                    .build();

            // Créer le don avec Lombok Builder
            Donation donation = Donation.builder()
                    .donor(donor)
                    .payment(payment)
                    .build();

            donationService.saveDonation(donation);

            // Lancer la vérification asynchrone
            volaService.verifyPaymentAsync(form.getPaymentId());

            log.info("Don soumis avec succès, vérification Vola en cours pour: {}", form.getPaymentId());

        } catch (Exception e) {
            log.error("Erreur lors de la soumission du don: {}", e.getMessage(), e);
        }

        return "redirect:/";
    }

    // Classes DTO avec Lombok
    @Data
    public static class DonationForm {
        private String donorEmail;
        private String donorFullName;
        private Double amount;
        private String paymentMethod;
        private String paymentId;
    }

    @Data
    @AllArgsConstructor
    public static class TransactionView {
        private LocalDateTime date;
        private String type;
        private String personName;
        private String personEmail;
        private Double amount;
        private String paymentMethod;
        private String status;
        private String description;
    }
}

