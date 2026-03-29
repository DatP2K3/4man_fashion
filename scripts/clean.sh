#!/bin/bash
# Dọn dẹp Docker: xoá images cũ, containers dừng, volumes không dùng
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
cd "$ROOT_DIR"

echo "🧹 Docker Cleanup"
echo "════════════════════════════════════════"

echo ""
echo "Before cleanup:"
docker system df 2>/dev/null

echo ""
echo "🗑️  Removing stopped containers..."
docker container prune -f 2>/dev/null

echo ""
echo "🗑️  Removing dangling images..."
docker image prune -f 2>/dev/null

echo ""
echo "🗑️  Removing unused networks..."
docker network prune -f 2>/dev/null

echo ""
echo "After cleanup:"
docker system df 2>/dev/null

echo ""
echo "✅ Cleanup done!"
echo ""
echo "⚠️  To also remove unused VOLUMES (DATA LOSS): docker volume prune -f"
