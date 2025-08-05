-- Données de production
INSERT INTO payments (id, amount, payment_date, payment_method, status) VALUES
                                                                            ('pay_prod_001', 150.00, '2025-01-20 08:30:00', 'Virement bancaire', 'SUCCEEDED'),
                                                                            ('pay_prod_002', 75.00, '2025-01-19 16:45:00', 'Mobile Money', 'SUCCEEDED'),
                                                                            ('pay_prod_003', 200.00, '2025-01-18 12:20:00', 'Carte bancaire', 'SUCCEEDED');

INSERT INTO donors (email, full_name) VALUES
                                          ('direction@hei.school', 'Direction HEI'),
                                          ('alumni@hei.school', 'Association Alumni'),
                                          ('entreprise@partner.com', 'Entreprise Partenaire');

INSERT INTO donations (donor_id, payment_id, created_at) VALUES
                                                             (1, 'pay_prod_001', '2025-01-20 08:30:00'),
                                                             (2, 'pay_prod_002', '2025-01-19 16:45:00'),
                                                             (3, 'pay_prod_003', '2025-01-18 12:20:00');

INSERT INTO payments (id, amount, payment_date, payment_method, status) VALUES
    ('pay_help_prod_001', 120.00, '2025-01-17 14:30:00', 'Virement', 'SUCCEEDED');

INSERT INTO beneficiaries (email, full_name) VALUES
    ('etudiant.urgence@hei.school', 'RANDRIA Nirina');

INSERT INTO helps (beneficiary_id, payment_id, accident_description, created_at) VALUES
    (1, 'pay_help_prod_001', 'Hospitalisation d''urgence suite à un accident de la circulation', '2025-01-17 14:30:00');
