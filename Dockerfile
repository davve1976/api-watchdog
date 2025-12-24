FROM eclipse-temurin:22-jdk AS build

WORKDIR /app
COPY . .

# ✅ Installera Maven i containern
RUN apt-get update && apt-get install -y maven

# ✅ Bygg projektet med din pom.xml
RUN mvn -q -DskipTests package

# Runtime image
FROM eclipse-temurin:22-jre
WORKDIR /app

COPY --from=build /app/target/api-watchdog.jar ./api-watchdog.jar

# Expose the default port (can be overridden by PORT env var in production)
EXPOSE 8080

# Use a shell command so we can forward env vars into Java system properties.
# This ensures the app listens on the PORT provided by hosting platforms (e.g. Railway)
CMD ["sh", "-c", "java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-production} -Dserver.port=${PORT:-8080} -jar api-watchdog.jar"]