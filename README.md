# 🛡️ API Watchdog – Simple API Health & Latency Checker

![Status](https://img.shields.io/badge/status-active-success)
![Java](https://img.shields.io/badge/java-21-blue)
![Spring Boot](https://img.shields.io/badge/spring--boot-3.0-green)
![Docker](https://img.shields.io/badge/docker-ready-informational)

API Watchdog is a lightweight Java / Spring Boot service that checks the **health and latency** of any HTTP/REST API.

You provide a URL – the service:
- Sends a `GET` request  
- Measures **response time**  
- Captures **HTTP status code**  
- Returns a **JSON payload** with timestamp and optional error message  

---

## ✔️ **Perfect for**
- Quick checks of internal/external APIs  
- Monitoring integration endpoints during development  
- Simple health probes in dev/test environments  
- Part of a CI/CD pipeline or Docker stack

---

## 🚀 **Getting Started**

### 📦 Run Locally (Maven)
```bash
mvn spring-boot:run
```

### 🐳 Run with Docker
```bash
docker build -t api-watchdog .
docker run -p 8080:8080 api-watchdog
```

### 🔍 API Usage
```bash
Health Check Endpoint
GET http://localhost:8080/api/check?url=https://google.com
```

### Example Response
```bash
{
  "url": "https://google.com",
  "status": 200,
  "latencyMs": 112,
  "timestamp": "2025-11-30T21:10:00Z"
}
```

### 🌐 Dashboard

The application also serves a very simple HTML dashboard on:

```text
http://localhost:8080/
```

### 📁 Project Structure
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
 
### 📌 Technologies
```bash
Tech	Used
Java 21	✔️
Spring Boot 3	✔️
REST API	✔️
Docker	✔️
Maven	✔️
```

### 📄 License
```bash
MIT – Free to use, improve, or build upon.
```

### 🤝 Contribute
```bash
Pull requests are welcome!
Feel free to open an issue or suggest features.
```

### ✉️ Contact
```bash
Author: David Wilson Stenberg
Find me on GitHub: https://github.com/davve1976
```

### Made with ❤️ for developers who want quick API monitoring.
