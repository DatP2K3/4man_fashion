# 📋 4Man Fashion — Hướng dẫn Docker & Scripts

## 📁 Cấu trúc Scripts

```
scripts/
├── build-all.sh      # Build toàn bộ 15 modules
├── build.sh          # Build 1 hoặc nhiều module
├── start.sh          # Khởi động tất cả containers
├── stop.sh           # Dừng tất cả containers
├── restart.sh        # Rebuild + restart 1 module
├── restart-all.sh    # Rebuild + restart tất cả
├── logs.sh           # Xem logs service
├── status.sh         # Xem RAM/CPU/Status
└── clean.sh          # Dọn rác Docker (giữ data)
```

---

## 🚀 Các lệnh thường dùng

### Build

```bash
# Build TẤT CẢ modules (Maven + Docker images) — ghi đè image cũ, tự xoá bản cũ
./scripts/build-all.sh

# Build 1 module cụ thể
./scripts/build.sh order

# Build nhiều module
./scripts/build.sh order product notification
```

### Khởi động / Dừng

```bash
# Start tất cả (infra → discovery → services, đúng thứ tự)
./scripts/start.sh

# Dừng tất cả
./scripts/stop.sh
```

### Restart (khi sửa code xong muốn cập nhật)

```bash
# Restart 1 service (rebuild JAR + image + restart container)
./scripts/restart.sh order

# Restart nhiều service
./scripts/restart.sh order product

# Restart TẤT CẢ (rebuild all + restart all)
./scripts/restart-all.sh
```

### Xem logs

```bash
# Xem logs order (follow mode, tự cập nhật)
./scripts/logs.sh order

# Xem logs nhiều service
./scripts/logs.sh order product

# Xem 50 dòng cuối
./scripts/logs.sh --tail 50 order
```

### Giám sát

```bash
# Xem trạng thái + RAM/CPU tất cả containers
./scripts/status.sh
```

### Dọn rác

```bash
# Xoá images cũ, containers dừng, networks không dùng
# ⚠️ KHÔNG xoá volumes (database data an toàn)
./scripts/clean.sh
```

---

## ⚙️ Cấu hình tài nguyên các service

### Infrastructure

| Service | Port | RAM limit | CPU |
|---------|:----:|:---------:|:---:|
| PostgreSQL | 5433 | 384m | 0.5 |
| Elasticsearch | 9200 | 768m (JVM: 512m) | 0.5 |
| Keycloak | 8180 | 384m | 0.5 |
| RabbitMQ | 5672 / 15672 | 256m | 0.25 |

### Microservices — Heavy

| Service | Port | -Xmx | -Xms | Hikari Pool | min-idle | RAM limit | CPU |
|---------|:----:|:----:|:----:|:-----------:|:--------:|:---------:|:---:|
| order | 6666 | 192m | 96m | 3 | 2 | 256m | 0.25 |
| product | 8765 | 192m | 96m | 3 | 2 | 256m | 0.25 |
| notification | 3333 | 192m | 96m | 3 | 2 | 256m | 0.25 |

### Microservices — Medium

| Service | Port | -Xmx | -Xms | Hikari Pool | min-idle | RAM limit | CPU |
|---------|:----:|:----:|:----:|:-----------:|:--------:|:---------:|:---:|
| profile | 9999 | 128m | 64m | 3 | 2 | 256m | 0.25 |
| cart | 8000 | 128m | 64m | 2 | 2 | 256m | 0.25 |
| payment | 8008 | 128m | 64m | 2 | 2 | 256m | 0.25 |
| dashboard | 2222 | 128m | 64m | — | — | 256m | 0.25 |

### Microservices — Light

| Service | Port | -Xmx | -Xms | Hikari Pool | min-idle | RAM limit | CPU |
|---------|:----:|:----:|:----:|:-----------:|:--------:|:---------:|:---:|
| banner | 6565 | 128m | 64m | 2 | 1 | 256m | 0.25 |
| shopinfo | 8222 | 128m | 64m | 2 | 1 | 256m | 0.25 |
| storage | 8080 | 128m | 64m | 2 | 1 | 256m | 0.25 |
| location | 8111 | 128m | 64m | 2 | 1 | 256m | 0.25 |

### Microservices — Infra

| Service | Port | -Xmx | -Xms | RAM limit | CPU |
|---------|:----:|:----:|:----:|:---------:|:---:|
| gateway | 8686 | 128m | 64m | 256m | 0.25 |
| discovery | 8761 | 128m | 64m | 256m | 0.25 |
| config | 6969 | 128m | 64m | 256m | 0.25 |
| elasticsearch-svc | 5566 | 128m | 64m | 256m | 0.25 |

### Tổng tài nguyên

| Metric | Giá trị |
|--------|--------:|
| Tổng RAM (max) | ~3.2GB |
| Tổng DB connections | 26 |
| Tổng RabbitMQ consumers | 6 |

---

## 🖥️ Chạy Local bằng IntelliJ IDEA

Khi chạy bằng IntelliJ (không dùng Docker), set VM Options:

```
Run → Edit Configurations → chọn Application → Modify options → Add VM options
```

### VM Options copy-paste:

**Heavy (order, product, notification):**
```
-Xmx192m -Xms96m -XX:+UseG1GC
```

**Medium (profile, cart, payment, dashboard):**
```
-Xmx128m -Xms64m -XX:+UseG1GC
```

**Light + Infra (tất cả còn lại):**
```
-Xmx128m -Xms64m -XX:+UseG1GC
```

> Chỉ cần set 1 lần — IntelliJ lưu vĩnh viễn trong `.idea/`

---

## 🌐 Service URLs

| Service | URL |
|---------|-----|
| Gateway (API chính) | http://localhost:8686 |
| Discovery (Eureka) | http://localhost:8761 |
| Keycloak (Auth) | http://localhost:8180 |
| RabbitMQ (Management) | http://localhost:15672 |
| Elasticsearch | http://localhost:9200 |

---

## 🏗️ VPS khuyến nghị (Production)

| Cấu hình | Tiết kiệm | An toàn |
|-----------|:---------:|:-------:|
| vCPU | 2 | 4 |
| RAM | 4GB + 2GB Swap | 8GB |
| SSD | 40GB | 80GB |
| Giá/tháng | ~300-600k | ~700k-1.2tr |

### Setup swap trên VPS (nếu dùng 4GB RAM):

```bash
sudo fallocate -l 2G /swapfile
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile
echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
```

---

## ⚠️ Troubleshooting

| Triệu chứng | Nguyên nhân | Fix |
|-------------|-------------|-----|
| `OutOfMemoryError` | -Xmx quá thấp | Tăng thêm 64m trong Dockerfile |
| Service khởi động chậm | cpus: 0.25 giới hạn | Tạm bỏ cpus limit |
| Connection timeout | Connection pool nhỏ | Tăng `maximum-pool-size` thêm 2 |
| Container bị kill | `mem_limit` quá thấp | Tăng thêm 128m trong docker-compose |
| Port đã bị dùng | Port conflict | `lsof -i :PORT` để check |

---

## 📝 Lưu ý quan trọng

1. **Luôn build trước khi start**: `./scripts/build-all.sh` → `./scripts/start.sh`
2. **Sửa code 1 module**: Dùng `./scripts/restart.sh <module>` để cập nhật nhanh
3. **Java 21 bắt buộc**: Đảm bảo `JAVA_HOME` trỏ đến Java 21
4. **Data an toàn**: `stop.sh` và `clean.sh` KHÔNG xoá database data (volumes)
5. **Thêm service mới**: Phải thêm `-Xmx` trong Dockerfile + `mem_limit` + `cpus` trong docker-compose.yml
