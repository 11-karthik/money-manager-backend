FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

# ✅ Fix permission (THIS WAS MISSING OR NOT APPLIED)
RUN chmod +x mvnw

# ✅ Build using mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/*.jar"]