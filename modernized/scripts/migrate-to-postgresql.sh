#!/bin/bash
# ============================================================
# migrate-to-postgresql.sh
# Full migration script: HSQLDB → PostgreSQL for PKS Insurance
# Usage: ./migrate-to-postgresql.sh [--dry-run]
# ============================================================
set -euo pipefail

DRY_RUN=false
[[ "${1:-}" == "--dry-run" ]] && DRY_RUN=true

# ── Configuration ────────────────────────────────────────────
PG_HOST="${PG_HOST:-localhost}"
PG_PORT="${PG_PORT:-5432}"
PG_DB="${PG_DB:-insurance_db}"
PG_USER="${PG_USER:-insurance_user}"
PG_PASS="${PG_PASS:-insurance_pass}"
HSQLDB_URL="${HSQLDB_URL:-jdbc:hsqldb:file:./struts/trunk/mydb/}"
BACKUP_DIR="${BACKUP_DIR:-/tmp/insurance_migration}"

export PGPASSWORD="$PG_PASS"

echo "====================================="
echo " PKS Insurance — DB Migration"
echo " HSQLDB → PostgreSQL $PG_DB"
echo " Dry run: $DRY_RUN"
echo "====================================="

# Step 1: Create backup directory
mkdir -p "$BACKUP_DIR"
echo "[1/7] Backup directory: $BACKUP_DIR"

# Step 2: Check PostgreSQL connectivity
echo "[2/7] Testing PostgreSQL connectivity..."
pg_isready -h "$PG_HOST" -p "$PG_PORT" -U "$PG_USER" -d "$PG_DB" || {
  echo "ERROR: Cannot connect to PostgreSQL. Please ensure PostgreSQL is running."
  exit 1
}
echo "       OK — PostgreSQL is ready."

# Step 3: Create the database and user if not exists
echo "[3/7] Ensuring database and user exist..."
if [ "$DRY_RUN" = false ]; then
  psql -h "$PG_HOST" -p "$PG_PORT" -U postgres -tc \
    "SELECT 1 FROM pg_database WHERE datname = '$PG_DB'" | grep -q 1 || \
    psql -h "$PG_HOST" -p "$PG_PORT" -U postgres -c \
    "CREATE DATABASE $PG_DB OWNER $PG_USER;"
  echo "       Database $PG_DB ready."
else
  echo "       [DRY RUN] Would create database $PG_DB"
fi

# Step 4: Apply Flyway migrations (user-service schema)
echo "[4/7] Applying users schema (Flyway V1)..."
USERS_MIGRATION_SQL="$BACKUP_DIR/V1__users.sql"
cat > "$USERS_MIGRATION_SQL" << 'EOSQL'
CREATE TABLE IF NOT EXISTS users (
    ssn             VARCHAR(20)  PRIMARY KEY,
    password_hash   VARCHAR(255) NOT NULL,
    first_name      VARCHAR(100),
    last_name       VARCHAR(100),
    gender          CHAR(1),
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
    id      BIGSERIAL    PRIMARY KEY,
    ssn     VARCHAR(20)  NOT NULL REFERENCES users(ssn) ON DELETE CASCADE,
    role    VARCHAR(50)  NOT NULL DEFAULT 'ROLE_USER',
    UNIQUE(ssn, role)
);

CREATE TABLE IF NOT EXISTS vehicles (
    ssn             VARCHAR(20)  PRIMARY KEY,
    type            VARCHAR(10),
    model           VARCHAR(100),
    make            VARCHAR(100),
    reg_no          VARCHAR(20),
    policy_type     VARCHAR(100),
    policy_amount   INTEGER,
    mf_year         CHAR(4),
    total_accident  INTEGER DEFAULT 0,
    quote_date      DATE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS quotes (
    id                BIGSERIAL  PRIMARY KEY,
    ssn               VARCHAR(20) NOT NULL,
    policy_type       VARCHAR(100),
    policy_amount     INTEGER,
    risk_factor       NUMERIC(10,4),
    premium_annual    INTEGER,
    premium_quarterly INTEGER,
    premium_monthly   INTEGER,
    calculated_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_user_roles_ssn ON user_roles(ssn);
CREATE INDEX IF NOT EXISTS idx_vehicles_reg_no ON vehicles(reg_no);
CREATE INDEX IF NOT EXISTS idx_quotes_ssn ON quotes(ssn);
EOSQL

if [ "$DRY_RUN" = false ]; then
  psql -h "$PG_HOST" -p "$PG_PORT" -U "$PG_USER" -d "$PG_DB" -f "$USERS_MIGRATION_SQL"
  echo "       Schema applied successfully."
else
  echo "       [DRY RUN] Would apply schema from $USERS_MIGRATION_SQL"
fi

# Step 5: Pre-migration backup
echo "[5/7] Creating pre-data-migration snapshot..."
SNAPSHOT="$BACKUP_DIR/pre_migration_$(date +%Y%m%d_%H%M%S).dump"
if [ "$DRY_RUN" = false ]; then
  pg_dump -h "$PG_HOST" -p "$PG_PORT" -U "$PG_USER" -d "$PG_DB" -F c -f "$SNAPSHOT"
  echo "       Snapshot saved: $SNAPSHOT"
else
  echo "       [DRY RUN] Would snapshot to $SNAPSHOT"
fi

# Step 6: Validate schema
echo "[6/7] Validating schema..."
TABLES=$(psql -h "$PG_HOST" -p "$PG_PORT" -U "$PG_USER" -d "$PG_DB" -t -c \
  "SELECT table_name FROM information_schema.tables WHERE table_schema='public' ORDER BY table_name;")
echo "       Tables: $TABLES"

# Step 7: Seed admin user (BCrypt hash for 'admin123')
echo "[7/7] Seeding default admin user..."
ADMIN_HASH='$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'
if [ "$DRY_RUN" = false ]; then
  psql -h "$PG_HOST" -p "$PG_PORT" -U "$PG_USER" -d "$PG_DB" -c "
    INSERT INTO users (ssn, password_hash, first_name, last_name, enabled)
    VALUES ('adminadmin', '$ADMIN_HASH', 'System', 'Administrator', true)
    ON CONFLICT (ssn) DO NOTHING;

    INSERT INTO user_roles (ssn, role)
    VALUES ('adminadmin', 'ROLE_ADMIN')
    ON CONFLICT DO NOTHING;
  "
  echo "       Admin user seeded (SSN: adminadmin)."
else
  echo "       [DRY RUN] Would seed admin user."
fi

echo ""
echo "====================================="
echo " Migration complete!"
echo " Database: $PG_DB @ $PG_HOST:$PG_PORT"
echo "====================================="
