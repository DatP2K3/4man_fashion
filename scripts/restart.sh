#!/bin/bash
# Restart 1 service (rebuild + restart container)
# Usage: ./scripts/restart.sh order
#        ./scripts/restart.sh product notification
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
cd "$ROOT_DIR"

export JAVA_HOME=$(/usr/libexec/java_home -v 21 2>/dev/null || echo "$JAVA_HOME")

if [ $# -eq 0 ]; then
  echo "Usage: $0 <module1> [module2] ..."
  echo "Example: $0 order"
  echo "         $0 product notification"
  exit 1
fi

for module in "$@"; do
  SERVICE="${module}-service"

  echo "🔄 Restarting $module..."

  # 1. Maven build
  echo "  📦 Building JAR..."
  mvn clean package -pl "$module" -am -DskipTests -Dspotless.check.skip=true -Dspotless.apply.skip=true -q

  # 2. Docker build
  echo "  🐳 Building image..."
  docker build -t "datp2k3/${module}_service" "$module/" -q > /dev/null 2>&1

  # 3. Restart container
  echo "  🔄 Restarting container..."
  docker compose up -d --force-recreate --no-deps "$SERVICE"

  echo "  ✅ $module restarted"
  echo ""
done
