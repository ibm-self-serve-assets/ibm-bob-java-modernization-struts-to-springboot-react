-- PKS Auto Insurance — User Service Schema
-- Run this once before starting user-service

CREATE TABLE IF NOT EXISTS users (
    ssn             VARCHAR(20)  PRIMARY KEY,
    password_hash   VARCHAR(255) NOT NULL,
    first_name      VARCHAR(100),
    last_name       VARCHAR(100),
    gender          VARCHAR(1),
    date_of_birth   DATE,
    mobile_no       VARCHAR(15),
    email           VARCHAR(255),
    city            VARCHAR(100),
    blood_group     VARCHAR(5),
    driving_licence VARCHAR(50),
    enabled         BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS user_roles (
    id   BIGSERIAL   PRIMARY KEY,
    ssn  VARCHAR(20) NOT NULL REFERENCES users(ssn) ON DELETE CASCADE,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
    UNIQUE(ssn, role)
);

CREATE INDEX IF NOT EXISTS idx_users_email     ON users(email);
CREATE INDEX IF NOT EXISTS idx_user_roles_ssn  ON user_roles(ssn);

-- Default admin user  (password: adminpass, BCrypt rounds=10)
INSERT INTO users (ssn, password_hash, first_name, last_name, gender, email, enabled, created_at, updated_at)
VALUES (
    'adminadmin',
    '$2a$10$4TeQlwzLvBFyUdOxRaEQgu80p714C9b08vx9NsADein1zDxZAD5ry',
    'Admin', 'User', 'M', 'admin@pks.com',
    TRUE, now(), now()
) ON CONFLICT (ssn) DO NOTHING;

INSERT INTO user_roles (ssn, role)
VALUES ('adminadmin', 'ROLE_ADMIN')
ON CONFLICT (ssn, role) DO NOTHING;
