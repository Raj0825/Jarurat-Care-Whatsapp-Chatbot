# ============================================================
# Dockerfile — WhatsApp Chatbot (Jarurat Care)
# Multi-stage build for smaller final image
# ============================================================

# Stage 1: Build with Maven
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built JAR
COPY --from=build /app/target/whatsapp-chatbot-1.0.0.jar app.jar

# Expose port (Render uses PORT env variable)
EXPOSE 8080

# Health check for Render
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD wget -qO- http://localhost:8080/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
