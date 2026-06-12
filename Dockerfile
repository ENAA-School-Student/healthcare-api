FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copier depuis le sous-dossier medical-system (chemin relatif depuis la racine du repo)
COPY medical-system/ .

# Donner les droits d'exécution à mvnw
RUN chmod +x mvnw

# Compiler le projet
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]