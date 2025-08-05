-- Données de test pour preprod
INSERT INTO payments (id, amount, payment_date, payment_method, status) VALUES
                                                                            ('pay_preprod_001', 25.00, '2025-01-21 10:15:00', 'Mobile Money', 'SUCCEEDED'),
                                                                            ('pay_preprod_002', 30.00, '2025-01-20 14:30:00', 'Espèces', 'VERIFYING'),
                                                                            ('pay_preprod_003', 40.00, '2025-01-19 09:45:00', 'Carte bancaire', 'FAILED');

INSERT INTO donors (email, full_name) VALUES
                                          ('test1@hei.school', 'RAKOTO Test'),
                                          ('test2@hei.school', 'RASOA Test'),
                                          ('test3@hei.school', 'RABE Test');

INSERT INTO donations (donor_id, payment_id, created_at) VALUES
                                                             (1, 'pay_preprod_001', '2025-01-21 10:15:00'),
                                                             (2, 'pay_preprod_002', '2025-01-20 14:30:00'),
                                                             (3, 'pay_preprod_003', '2025-01-19 09:45:00');

INSERT INTO payments (id, amount, payment_date, payment_method, status) VALUES
    ('pay_help_preprod_001', 35.00, '2025-01-18 11:20:00', 'Mobile Money', 'SUCCEEDED');

INSERT INTO beneficiaries (email, full_name) VALUES
    ('beneficiaire.test@hei.school', 'RATSIA Test');

INSERT INTO helps (beneficiary_id, payment_id, accident_description, created_at) VALUES
    (1, 'pay_help_preprod_001', 'Test de fonctionnement pour accident simulé', '2025-01-18 11:20:00');
