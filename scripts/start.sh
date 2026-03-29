#!/bin/bash
# Start toàn bộ containers (infra trước, services sau)
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
cd "$ROOT_DIR"

echo "🚀 Starting 4Man Fashion..."
echo ""

# 1. Start infra first
echo "📡 Step 1: Starting infrastructure..."
docker compose up -d postgres rabbitmq elasticsearch keycloak
echo "  Waiting for PostgreSQL healthcheck..."
sleep 10

# 2. Start discovery (Eureka) - other services depend on it
echo ""
echo "🔍 Step 2: Starting Discovery service..."
docker compose up -d discovery-service
sleep 5

# 3. Start all remaining services
echo ""
echo "🎯 Step 3: Starting all services..."
docker compose up -d

echo ""
echo "════════════════════════════════════════"
echo "✅ All services started!"
echo ""
echo "📋 Service URLs:"
echo "  Gateway:        http://localhost:8686"
echo "  Discovery:      http://localhost:8761"
echo "  Keycloak:       http://localhost:8180"
echo "  RabbitMQ:       http://localhost:15672"
echo "  Elasticsearch:  http://localhost:9200"
echo ""
echo "📊 Container status:"
docker compose ps --format "table {{.Name}}\t{{.Status}}\t{{.Ports}}" 2>/dev/null || docker compose ps
