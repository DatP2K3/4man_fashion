# Eclipse Temurin JRE 21 on Alpine Linux
FROM eclipse-temurin:21-jre-alpine

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR đã build vào container
COPY ./target/*.jar app.jar

EXPOSE 8111

# Chạy ứng dụng
ENTRYPOINT ["java", "-Xmx128m", "-Xms64m", "-XX:+UseG1GC", "-jar", "app.jar"]