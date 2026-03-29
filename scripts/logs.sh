#!/bin/bash
# Xem logs của 1 hoặc nhiều service
# Usage: ./scripts/logs.sh order              (follow logs)
#        ./scripts/logs.sh order product      (multiple)
#        ./scripts/logs.sh --tail 50 order    (last 50 lines)
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
cd "$ROOT_DIR"

TAIL_LINES=100
SERVICES=()

# Parse arguments
while [[ $# -gt 0 ]]; do
  case $1 in
    --tail)
      TAIL_LINES="$2"
      shift 2
      ;;
    *)
      SERVICES+=("${1}-service")
      shift
      ;;
  esac
done

if [ ${#SERVICES[@]} -eq 0 ]; then
  echo "Usage: $0 [--tail N] <module1> [module2] ..."
  echo "Example: $0 order"
  echo "         $0 --tail 50 order product"
  exit 1
fi

docker compose logs -f --tail "$TAIL_LINES" "${SERVICES[@]}"
