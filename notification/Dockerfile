# Eclipse Temurin JRE 21 on Alpine Linux
FROM eclipse-temurin:21-jre-alpine

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR đã build vào container
COPY ./target/*.jar app.jar

# Mở cổng 3333
EXPOSE 3333

# Chạy ứng dụng
ENTRYPOINT ["java", "-Xmx192m", "-Xms96m", "-XX:+UseG1GC", "-jar", "app.jar"]