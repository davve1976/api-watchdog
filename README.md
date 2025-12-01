
# 🛡️ API Watchdog – Simple API Health & Latency Checker

[🟢 Live Demo](https://api-watchdog-production.up.railway.app)

![Status](https://img.shields.io/badge/deployed-success-brightgreen)
![Java](https://img.shields.io/badge/java-21-blue)
![Spring Boot](https://img.shields.io/badge/spring--boot-3-green)
![Docker](https://img.shields.io/badge/docker-ready-informational)
![Railway](https://img.shields.io/badge/railway-deployed-purple)

API Watchdog is a lightweight Java / Spring Boot service that checks the **health and latency** of any HTTP/REST API.

You provide a URL – the service:
- Sends a `GET` request  
- Measures **response time**  
- Captures **HTTP status code**  
- Returns a **JSON payload** with timestamp and optional error message  

## ✔️ Perfect for
- Quick checks of internal/external APIs  
- Monitoring integration endpoints during development  
- Simple health probes in dev/test environments  
- Part of a CI/CD pipeline or Docker stack

## 🚀 Getting Started

### Run Locally (Maven)
mvn spring-boot:run

### Run with Docker
docker build -t api-watchdog .
docker run -p 8080:8080 api-watchdog

### API Usage
GET http://localhost:8080/healthcheck?url=https://google.com

### Example Response
{
  "url": "https://google.com",
  "status": 200,
  "latencyMs": 112,
  "timestamp": "2025-11-30T21:10:00Z"
}

### Authentication

For `/api/**` endpoints you must provide an API key header:

`X-API-Key: <your-key>`

Configured via:

```properties
apiwatchdog.api-key.enabled=true
apiwatchdog.api-key.value=your-secret
```


### Project Structure
```bash
api-watchdog/
 ├─ src/main/java/com/apiwatchdog
 │   ├─ controller/HealthCheckController.java
 │   ├─ service/ApiCheckService.java
 │   └─ model/ApiResponse.java
 ├─ src/main/resources/application.properties
 ├─ Dockerfile
 ├─ pom.xml
 └─ README.md
 ```

### SaaS Plans (coming soon)
Free – 10 checks/day  
Pro – €5/mo, unlimited checks  
Business – €15/mo, email alerts + Slack

### License
MIT – Free to use, improve, or build upon.

### Contact
Author: David Wilson Stenberg  
GitHub: https://github.com/davve1976

Made with ❤️ for developers who want quick API monitoring.
