-- PKS Auto Insurance — Vehicle Service Schema
-- Run this once before starting vehicle-service

CREATE TABLE IF NOT EXISTS vehicles (
    ssn            VARCHAR(20)  PRIMARY KEY,
    type           VARCHAR(10),
    model          VARCHAR(100),
    make           VARCHAR(100),
    reg_no         VARCHAR(20),
    policy_type    VARCHAR(100),
    policy_amount  INTEGER,
    mf_year        VARCHAR(4),
    total_accident INTEGER      DEFAULT 0,
    quote_date     DATE,
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_vehicles_reg_no ON vehicles(reg_no);
