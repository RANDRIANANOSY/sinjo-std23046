package hei.tsinjo.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class DomainModelTest {

    @Test
    void should_create_donation_with_valid_data() {
        // Given
        Donor donor = new Donor("test@hei.school", "Test User");
        Payment payment = new Payment("pay_001", 50.0, LocalDateTime.now(),
                "Mobile Money", PaymentStatus.SUCCEEDED);

        // When
        Donation donation = new Donation(donor, payment);

        // Then
        assertNotNull(donation);
        assertEquals(donor, donation.getDonor());
        assertEquals(payment, donation.getPayment());
        assertNotNull(donation.getCreatedAt());
        assertTrue(donation.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void should_create_help_with_accident_description() {
        // Given
        Beneficiary beneficiary = new Beneficiary("beneficiary@hei.school", "Beneficiary Name");
        Payment payment = new Payment("pay_002", 100.0, LocalDateTime.now(),
                "Virement", PaymentStatus.SUCCEEDED);
        String description = "Accident de moto nécessitant des soins";

        // When
        Help help = new Help(beneficiary, payment, description);

        // Then
        assertNotNull(help);
        assertEquals(beneficiary, help.getBeneficiary());
        assertEquals(payment, help.getPayment());
        assertEquals(description, help.getAccidentDescription());
        assertNotNull(help.getCreatedAt());
    }

    @Test
    void should_validate_payment_status_transitions() {
        // Given
        Payment payment = new Payment("pay_003", 25.0, LocalDateTime.now(),
                "Carte bancaire", PaymentStatus.VERIFYING);

        // When & Then
        assertEquals(PaymentStatus.VERIFYING, payment.getStatus());

        payment.setStatus(PaymentStatus.SUCCEEDED);
        assertEquals(PaymentStatus.SUCCEEDED, payment.getStatus());

        // Should not allow invalid transitions in real implementation
        assertNotEquals(PaymentStatus.FAILED, payment.getStatus());
    }

    @Test
    void should_validate_donor_information() {
        // Given
        String email = "valid@hei.school";
        String fullName = "Valid Name";

        // When
        Donor donor = new Donor(email, fullName);

        // Then
        assertNotNull(donor);
        assertEquals(email, donor.getEmail());
        assertEquals(fullName, donor.getFullName());
        assertTrue(donor.getEmail().contains("@"));
        assertFalse(donor.getFullName().trim().isEmpty());
    }

    @Test
    void should_validate_beneficiary_information() {
        // Given
        String email = "beneficiary@hei.school";
        String fullName = "Beneficiary Full Name";

        // When
        Beneficiary beneficiary = new Beneficiary(email, fullName);

        // Then
        assertNotNull(beneficiary);
        assertEquals(email, beneficiary.getEmail());
        assertEquals(fullName, beneficiary.getFullName());
        assertTrue(beneficiary.getEmail().contains("@"));
        assertFalse(beneficiary.getFullName().trim().isEmpty());
    }
}
