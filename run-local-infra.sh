#!/bin/bash

# ====================================================
# 4Man Fashion - Local Infrastructure Startup Script
# ====================================================
# The script brings up the core infrastructure services
# (Database, Message Queue, Search Engine, IAM) without 
# starting the 4Man microservices. Useful for local 
# development via IntelliJ/Eclipse.
# ====================================================

echo "🚀 Bắt đầu khởi chạy Local Infrastructure cho 4Man Fashion..."

# Tải biến môi trường từ .env nếu tồn tại
if [ -f .env ]; then
  export $(cat .env | grep -v '#' | awk '/=/ {print $1}')
  echo "✅ Đã nạp thành công các biến cấu hình từ file .env"
else
  echo "⚠️ Cảnh báo: Không tìm thấy file .env, docker có thể sẽ dùng giá trị mặc định!"
fi

echo "🔄 Đang khởi động Postgres, RabbitMQ, Elasticsearch và Keycloak..."

# Chỉ khởi chạy các container cốt lõi
docker-compose up -d postgres rabbitmq elasticsearch keycloak

echo ""
echo "==================================================="
echo "🎉 INFRASTRUCTURE ĐÃ ĐƯỢC KHỞI CHẠY THÀNH CÔNG!"
echo "==================================================="
echo "📊 Các công cụ để bạn kiểm tra trạng thái:"
echo " 🔹 PostgreSQL:     localhost:5433"
echo " 🔹 RabbitMQ UI:    http://localhost:15672 (admin/admin)"
echo " 🔹 Elasticsearch:  http://localhost:9200"
echo " 🔹 Keycloak Admin: http://localhost:8180 (admin/admin)"
echo ""
echo "👨‍💻 Bây giờ bạn có thể mở IDE (IntelliJ/VSCode) và bấm Run các Microservices nhé!"
