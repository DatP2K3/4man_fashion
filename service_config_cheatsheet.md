# 📋 Service Configuration Cheatsheet — 4Man Fashion

## Docker Configuration (docker-compose)

### Infrastructure Services

| Service | Image | Port | mem_limit | cpus | JVM Opts |
|---------|-------|:----:|:---------:|:----:|----------|
| **PostgreSQL** | postgres:latest | 5433 | 384m | 0.5 | — |
| **Elasticsearch** | elasticsearch:8.17.3 | 9200 | 768m | 0.5 | `-Xms256m -Xmx512m` |
| **Keycloak** | keycloak:latest | 8180 | 384m | 0.5 | — |
| **RabbitMQ** | rabbitmq:4.1.0-rc.2 | 5672/15672 | 256m | 0.25 | — |

### Microservices — Heavy (Nhiều logic + RabbitMQ)

| Service | Port | -Xmx | -Xms | Hikari Pool | min-idle | mem_limit | cpus |
|---------|:----:|:----:|:----:|:-----------:|:--------:|:---------:|:----:|
| **order** | 6666 | 192m | 96m | 3 | 2 | 256m | 0.25 |
| **product** | 8765 | 192m | 96m | 3 | 2 | 256m | 0.25 |
| **notification** | 3333 | 192m | 96m | 3 | 2 | 256m | 0.25 |

### Microservices — Medium (CRUD + Feign calls)

| Service | Port | -Xmx | -Xms | Hikari Pool | min-idle | mem_limit | cpus |
|---------|:----:|:----:|:----:|:-----------:|:--------:|:---------:|:----:|
| **profile** | 9999 | 128m | 64m | 3 | 2 | 256m | 0.25 |
| **cart** | 8000 | 128m | 64m | 2 | 2 | 256m | 0.25 |
| **payment** | 8008 | 128m | 64m | 2 | 2 | 256m | 0.25 |
| **dashboard** | 2222 | 128m | 64m | — | — | 256m | 0.25 |

### Microservices — Light (Đơn giản, ít traffic)

| Service | Port | -Xmx | -Xms | Hikari Pool | min-idle | mem_limit | cpus |
|---------|:----:|:----:|:----:|:-----------:|:--------:|:---------:|:----:|
| **banner** | 6565 | 128m | 64m | 2 | 1 | 256m | 0.25 |
| **shopinfo** | 8222 | 128m | 64m | 2 | 1 | 256m | 0.25 |
| **storage** | 8080 | 128m | 64m | 2 | 1 | 256m | 0.25 |
| **location** | 8111 | 128m | 64m | 2 | 1 | 256m | 0.25 |

### Microservices — Infra (Routing/Config)

| Service | Port | -Xmx | -Xms | Hikari Pool | min-idle | mem_limit | cpus |
|---------|:----:|:----:|:----:|:-----------:|:--------:|:---------:|:----:|
| **gateway** | 8686 | 128m | 64m | — | — | 256m | 0.25 |
| **discovery** | 8761 | 128m | 64m | — | — | 256m | 0.25 |
| **config** | 6969 | 128m | 64m | — | — | 256m | 0.25 |
| **elasticsearch-svc** | 5566 | 128m | 64m | — | — | 256m | 0.25 |

---

## 📊 Tổng tài nguyên

| Metric | Giá trị |
|--------|--------:|
| Tổng RAM (max) | **~3.2GB** |
| Tổng DB connections | **26** |
| Tổng RabbitMQ consumers | **6** |
| Tổng CPU cores (max) | **7 cores** |

---

## 🖥️ IntelliJ IDEA Run Configuration

Khi chạy local bằng IntelliJ, set **VM Options** trong Run Configuration cho mỗi service:

```
Run → Edit Configurations → chọn Application → Modify options → Add VM options
```

### Copy-paste VM Options

#### Heavy Services (order, product, notification)
```
-Xmx192m -Xms96m -XX:+UseG1GC
```

#### Medium Services (profile, cart, payment, dashboard)
```
-Xmx128m -Xms64m -XX:+UseG1GC
```

#### Light Services (banner, shopinfo, location, storage)
```
-Xmx128m -Xms64m -XX:+UseG1GC
```

#### Infra Services (gateway, discovery, config, elasticsearch-svc)
```
-Xmx128m -Xms64m -XX:+UseG1GC
```

### Hướng dẫn chi tiết IntelliJ

1. Click ▼ cạnh nút Run (góc trên phải) → **Edit Configurations...**
2. Chọn service (VD: `OrderApplication`)
3. Click **Modify options** → chọn **Add VM options**
4. Dán VM options tương ứng vào ô `VM options`
5. Click **Apply** → **OK**
6. Lặp lại cho 15 services

> **Lưu ý**: Chỉ cần làm **1 lần** — IntelliJ lưu config vĩnh viễn trong `.idea/`

### Nếu chạy từ Terminal

```bash
# Heavy service
java -Xmx192m -Xms96m -XX:+UseG1GC -jar order/target/*.jar

# Light service
java -Xmx128m -Xms64m -XX:+UseG1GC -jar banner/target/*.jar
```

---

## ⚠️ Troubleshooting

| Triệu chứng | Nguyên nhân | Fix |
|-------------|-------------|-----|
| `OutOfMemoryError` | -Xmx quá thấp | Tăng -Xmx thêm 64m |
| Service khởi động chậm | cpus: 0.25 giới hạn | Bỏ cpus limit khi dev |
| Connection timeout | Pool quá nhỏ | Tăng maximum-pool-size thêm 2 |
| Container bị kill | mem_limit quá thấp | Tăng mem_limit thêm 128m |
