#!/bin/bash
# Build toàn bộ JAR + Docker images (ghi đè image cũ)
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
cd "$ROOT_DIR"

export JAVA_HOME=$(/usr/libexec/java_home -v 21 2>/dev/null || echo "$JAVA_HOME")

MODULES=(
  order product notification profile cart payment
  dashboard banner shopinfo storage location
  gateway discovery config elasticsearch
)

echo "🔨 Building all modules..."
START=$(date +%s)

# 1. Maven build all JARs
echo ""
echo "📦 Step 1: Maven package (skip tests)..."
mvn clean package -DskipTests -Dspotless.check.skip=true -Dspotless.apply.skip=true -pl '!elasticsearch' -q
echo "✅ Maven build done"

# 2. Docker build all images
echo ""
echo "🐳 Step 2: Docker build all images..."
FAILED=()
for module in "${MODULES[@]}"; do
  if [ -f "$module/Dockerfile" ]; then
    echo -n "  Building $module... "
    if docker build -t "datp2k3/${module}_service" "$module/" -q > /dev/null 2>&1; then
      echo "✅"
    else
      echo "❌"
      FAILED+=("$module")
    fi
  fi
done

END=$(date +%s)
DURATION=$((END - START))

echo ""
echo "════════════════════════════════════════"
if [ ${#FAILED[@]} -eq 0 ]; then
  docker image prune -f --filter "dangling=true" > /dev/null 2>&1
  echo "✅ All ${#MODULES[@]} images built successfully (${DURATION}s)"
  echo "🧹 Old images cleaned"
else
  echo "❌ Failed: ${FAILED[*]}"
  exit 1
fi
