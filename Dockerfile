# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-22 AS builder
WORKDIR /app
COPY . .
RUN mvn -q -DskipTests clean package

# Stage 2: Run
FROM eclipse-temurin:22-jre
WORKDIR /app
COPY --from=builder /app/target/digg.jar digg.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","digg.jar"]