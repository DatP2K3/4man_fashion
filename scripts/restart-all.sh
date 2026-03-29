#!/bin/bash
# Restart toàn bộ: rebuild ALL + restart ALL containers
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
cd "$ROOT_DIR"

echo "🔄 Full restart — rebuild + restart ALL services"
echo ""

# 1. Build all
"$SCRIPT_DIR/build-all.sh"

# 2. Restart all containers
echo ""
echo "🔄 Recreating all containers..."
docker compose up -d --force-recreate

echo ""
echo "✅ All services rebuilt and restarted!"
echo ""
docker compose ps --format "table {{.Name}}\t{{.Status}}" 2>/dev/null || docker compose ps
