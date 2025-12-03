# 🛡️ API Watchdog – Simple API Health & Latency Checker

API Watchdog is a lightweight Java / Spring Boot service that checks the **health and latency** of any HTTP/REST API.

You provide a URL – the service:

- Sends a `GET` request  
- Measures **response time**  
- Captures **HTTP status code**  
- Returns a **JSON payload** with timestamp and optional error message  
- Optionally stores history in MongoDB and sends alerts via email/Slack  

---

## ✔️ Use Cases

- Quick checks of internal/external APIs  
- Monitoring integration endpoints during development  
- Simple health probes in dev/test  
- CI/CD integration  
- Base for a small monitoring SaaS  

---

## 🚀 Getting Started

### ▶️ Run Locally (Maven)

```bash
mvn spring-boot:run
```

### 🐳 Run with Docker

Build:

```bash
docker build -t api-watchdog .
```

Run:

```bash
docker run -p 8080:8080 api-watchdog
```

---

## 🔍 API Endpoints

### 1️⃣ Public (no API key required)

```
GET /api/public/check?url={targetUrl}
```

Example:

```bash
curl "http://localhost:8080/api/public/check?url=https://google.com"
```

### 2️⃣ Protected (API key required)

Header:

```
X-API-Key: changeme
```

Example:

```bash
curl -H "X-API-Key: changeme" \
  "http://localhost:8080/api/check?url=https://google.com"
```

---

## 🔐 API Key Security

Configure in `application.properties`:

```properties
apiwatchdog.api-key.enabled=true
apiwatchdog.api-key.value=changeme
```

---

## 📊 History

### In-memory last 10 checks:

```bash
curl -H "X-API-Key: changeme" http://localhost:8080/api/history
```

### MongoDB full history

```properties
APIWATCHDOG_MONGODB_ENABLED=true
MONGODB_URI=mongodb://localhost:27017/api-watchdog
```

Endpoint:

```bash
curl -H "X-API-Key: changeme" http://localhost:8080/api/history/db
```

---

## 🔔 Alerts (Email + Slack)

### Email Alerts

```properties
APIWATCHDOG_ALERT_MAIL_ENABLED=true
APIWATCHDOG_ALERT_MAIL_TO=you@example.com
```

### Slack Alerts

```properties
APIWATCHDOG_SLACK_ENABLED=true
APIWATCHDOG_SLACK_WEBHOOK=https://hooks.slack.com/services/XXX
```

---

## 📁 Project Structure

```
api-watchdog/
 ├─ controller/
 ├─ security/
 ├─ service/
 ├─ alert/
 ├─ model/
 ├─ repository/
 ├─ application.properties
 ├─ Dockerfile
 └─ pom.xml
```

---

## 🧱 Technology Stack

- Java 21  
- Spring Boot 3  
- MongoDB (optional)  
- Docker  
- Custom API Key Filter  
- Slack email alerts  

---

## 🧾 License

MIT — free to use and modify.

---

## ✉️ Contact

Author: **David Wilson Stenberg**  
GitHub: https://github.com/davve1976

---
