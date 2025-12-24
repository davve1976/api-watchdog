# Build stage
FROM maven:3.9.9-eclipse-temurin-22 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

COPY . .
RUN mvn -B -q -DskipTests package

# Runtime stage
FROM eclipse-temurin:22-jre
WORKDIR /app

COPY --from=build /app/target/api-watchdog.jar app.jar

EXPOSE 8080

CMD ["sh", "-c", "java \
  -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-production} \
  -Dserver.port=${PORT:-8080} \
  -jar app.jar"]
