# Sử dụng Amazon Corretto JDK 21
FROM eclipse-temurin:21-jre-ubi9-minimal

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR đã build vào container
COPY ./target/*.jar app.jar

# Mở cổng 8686
EXPOSE 8686

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]