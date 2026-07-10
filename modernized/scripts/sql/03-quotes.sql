-- PKS Auto Insurance — Premium Service Schema
-- Run this once before starting premium-service

CREATE TABLE IF NOT EXISTS quotes (
    id                BIGSERIAL    PRIMARY KEY,
    ssn               VARCHAR(20)  NOT NULL,
    policy_type       VARCHAR(100),
    policy_amount     INTEGER,
    risk_factor       NUMERIC(10,4),
    premium_annual    INTEGER,
    premium_quarterly INTEGER,
    premium_monthly   INTEGER,
    calculated_at     TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_quotes_ssn ON quotes(ssn);
