# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /src/advshop
COPY . .
RUN ./gradlew clean bootJar

# Run stage
FROM eclipse-temurin:21-jre-alpine AS runner
WORKDIR /opt/advshop

# Koyeb/Render will provide PORT; app should bind to it
EXPOSE 8080

COPY --from=builder /src/advshop/build/libs/*.jar app.jar

ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]