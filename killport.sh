#!/bin/bash

# ====================================================
# Script giúp giải phóng (kill) ứng dụng kẹt trên một cổng (Port) ở Macbook
# ====================================================

if [ -z "$1" ]; then
  echo "❌ Lỗi: Bạn chưa cung cấp số Cổng (Port) cần tắt!"
  echo "👉 Cách dùng: ./killport.sh <port_number>"
  echo "   Ví dụ: ./killport.sh 6666"
  exit 1
fi

PORT=$1

echo "🔍 Đang tìm kiếm tiến trình chiếm dụng cổng $PORT..."
# Lấy PID của quá trình đang lắng nghe trên cổng (lsof -t trả về mảng PID list)
PID=$(lsof -ti tcp:$PORT)

if [ -z "$PID" ]; then
  echo "✅ Tuyệt vời! Không có ứng dụng nào đang chạy (hoặc kẹt) ở cổng $PORT cả."
else
  echo "☠️ Phát hiện tiến trình [PID = $PID] đang chiếm cổng $PORT. Tiến hành buộc tắt (kill -9)..."
  kill -9 $PID
  echo "🎉 Đã dẹp loạn xong! Cổng $PORT đã được giải phóng hoàn toàn."
fi
