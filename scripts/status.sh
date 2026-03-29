#!/bin/bash
# Xem trạng thái + resource usage của tất cả containers
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
cd "$ROOT_DIR"

echo "📊 Container Status"
echo "════════════════════════════════════════"
docker compose ps --format "table {{.Name}}\t{{.Status}}\t{{.Ports}}" 2>/dev/null || docker compose ps

echo ""
echo "💾 Resource Usage (RAM / CPU)"
echo "════════════════════════════════════════"
docker stats --no-stream --format "table {{.Name}}\t{{.CPUPerc}}\t{{.MemUsage}}\t{{.MemPerc}}" 2>/dev/null || echo "No containers running"

echo ""
echo "🗄️  Docker Disk Usage"
echo "════════════════════════════════════════"
docker system df 2>/dev/null || echo "Docker not running"
