-- Données de test pour l'environnement de développement
INSERT INTO payments (id, amount, payment_date, payment_method, status) VALUES
                                                                            ('pay_test_001', 50.00, '2025-01-15 10:30:00', 'Mobile Money', 'SUCCEEDED'),
                                                                            ('pay_test_002', 25.00, '2025-01-14 14:20:00', 'Carte bancaire', 'SUCCEEDED'),
                                                                            ('pay_test_003', 100.00, '2025-01-13 09:15:00', 'Virement', 'SUCCEEDED');

INSERT INTO donors (email, full_name) VALUES
                                          ('rabe@hei.school', 'RABE Miandry'),
                                          ('ravo@hei.school', 'RAVO Hasina'),
                                          ('raly@hei.school', 'RALY Finaritra');

INSERT INTO beneficiaries (email, full_name) VALUES
    ('rina@hei.school', 'RINA Volatiana');

INSERT INTO donations (donor_id, payment_id, created_at) VALUES
                                                             (1, 'pay_test_001', '2025-01-15 10:30:00'),
                                                             (2, 'pay_test_002', '2025-01-14 14:20:00'),
                                                             (3, 'pay_test_003', '2025-01-13 09:15:00');

INSERT INTO payments (id, amount, payment_date, payment_method, status) VALUES
    ('pay_help_001', 75.00, '2025-01-12 16:45:00', 'Virement', 'SUCCEEDED');

INSERT INTO helps (beneficiary_id, payment_id, accident_description, created_at) VALUES
    (1, 'pay_help_001', 'Accident de moto nécessitant des soins d''urgence', '2025-01-12 16:45:00');