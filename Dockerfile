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

CMD ["java", "-jar", "api-watchdog.jar"]
