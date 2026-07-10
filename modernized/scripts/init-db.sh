#!/usr/bin/env bash
# =============================================================================
# init-db.sh — PKS Auto Insurance database initialisation
#
# Usage:
#   ./init-db.sh                          # uses defaults below
#   DB_HOST=myhost DB_PASSWORD=secret ./init-db.sh
#
# Run this once before starting any service. Safe to re-run (all statements
# use IF NOT EXISTS / ON CONFLICT DO NOTHING).
# =============================================================================

set -euo pipefail

DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-5432}"
DB_NAME="${DB_NAME:-pksdb}"
DB_USER="${DB_USER:-pksadmin}"
DB_PASSWORD="${DB_PASSWORD:-pkspassword}"

PSQL="/opt/homebrew/opt/postgresql@18/bin/psql"
if ! command -v psql &>/dev/null && [ ! -x "$PSQL" ]; then
  echo "ERROR: psql not found. Add PostgreSQL bin directory to PATH or set PSQL variable."
  exit 1
fi
# If psql is on PATH, use it; otherwise fall back to the Homebrew path
command -v psql &>/dev/null && PSQL="psql"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SQL_DIR="$SCRIPT_DIR/sql"

export PGPASSWORD="$DB_PASSWORD"

run_sql() {
  local file="$1"
  echo "▶ Applying $(basename "$file") ..."
  "$PSQL" -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -f "$file"
  echo "  ✓ Done"
}

echo "========================================"
echo " PKS Auto Insurance — DB Init"
echo " Host : $DB_HOST:$DB_PORT"
echo " DB   : $DB_NAME"
echo " User : $DB_USER"
echo "========================================"
echo ""

run_sql "$SQL_DIR/01-users.sql"
run_sql "$SQL_DIR/02-vehicles.sql"
run_sql "$SQL_DIR/03-quotes.sql"

echo ""
echo "✅ All schemas initialised. You can now start the services."
