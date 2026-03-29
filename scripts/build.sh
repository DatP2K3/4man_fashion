#!/bin/bash
# Build 1 hoặc nhiều module cụ thể
# Usage: ./scripts/build.sh order product
#        ./scripts/build.sh notification
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
cd "$ROOT_DIR"

export JAVA_HOME=$(/usr/libexec/java_home -v 21 2>/dev/null || echo "$JAVA_HOME")

if [ $# -eq 0 ]; then
  echo "Usage: $0 <module1> [module2] ..."
  echo ""
  echo "Available modules:"
  echo "  order product notification profile cart payment"
  echo "  dashboard banner shopinfo storage location"
  echo "  gateway discovery config elasticsearch"
  exit 1
fi

for module in "$@"; do
  if [ ! -d "$module" ]; then
    echo "❌ Module '$module' not found"
    exit 1
  fi

  echo "🔨 Building $module..."

  # Maven build
  echo "  📦 Maven package..."
  mvn clean package -pl "$module" -am -DskipTests -Dspotless.check.skip=true -Dspotless.apply.skip=true -q

  # Docker build
  if [ -f "$module/Dockerfile" ]; then
    echo "  🐳 Docker build..."
    docker build -t "datp2k3/${module}_service" "$module/" -q > /dev/null 2>&1
    echo "  ✅ $module done"
  fi
  echo ""
done
