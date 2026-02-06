FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

# Fix Windows line endings + permission
RUN sed -i 's/\r$//' mvnw
RUN chmod +x mvnw

# Build the app
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["sh", "-c", "java -jar target/*.jar"]