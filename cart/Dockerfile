# Sử dụng Amazon Corretto JDK 21
FROM eclipse-temurin:21-jre-alpine

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR đã build vào container
COPY ./target/*.jar app.jar

EXPOSE 8000

# Chạy ứng dụng
ENTRYPOINT ["java", "-Xmx192m", "-Xms96m", "-XX:+UseG1GC", "-jar", "app.jar"]