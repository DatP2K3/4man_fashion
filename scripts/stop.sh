#!/bin/bash
# Stop toàn bộ containers
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
cd "$ROOT_DIR"

echo "🛑 Stopping all containers..."
docker compose down

echo "✅ All containers stopped"
echo ""

# Optional: show disk usage
echo "💾 Docker disk usage:"
docker system df 2>/dev/null || echo "(Docker not running)"
