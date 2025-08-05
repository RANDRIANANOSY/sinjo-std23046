-- Table des paiements
CREATE TABLE payments (
                          id VARCHAR(255) PRIMARY KEY,
                          amount DECIMAL(10,2) NOT NULL,
                          payment_date TIMESTAMP NOT NULL,
                          payment_method VARCHAR(100) NOT NULL,
                          status VARCHAR(20) NOT NULL DEFAULT 'VERIFYING'
);

-- Table des donateurs
CREATE TABLE donors (
                        id BIGSERIAL PRIMARY KEY,
                        email VARCHAR(255) NOT NULL,
                        full_name VARCHAR(255) NOT NULL
);

-- Table des bénéficiaires
CREATE TABLE beneficiaries (
                               id BIGSERIAL PRIMARY KEY,
                               email VARCHAR(255) NOT NULL,
                               full_name VARCHAR(255) NOT NULL
);

-- Table des dons
CREATE TABLE donations (
                           id BIGSERIAL PRIMARY KEY,
                           donor_id BIGINT NOT NULL REFERENCES donors(id),
                           payment_id VARCHAR(255) NOT NULL REFERENCES payments(id),
                           created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Table des aides
CREATE TABLE helps (
                       id BIGSERIAL PRIMARY KEY,
                       beneficiary_id BIGINT NOT NULL REFERENCES beneficiaries(id),
                       payment_id VARCHAR(255) NOT NULL REFERENCES payments(id),
                       accident_description TEXT NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Index pour les performances
CREATE INDEX idx_donations_created_at ON donations(created_at DESC);
CREATE INDEX idx_helps_created_at ON helps(created_at DESC);
CREATE INDEX idx_payments_status ON payments(status);