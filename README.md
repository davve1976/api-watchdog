# 🛡️ API Watchdog – Simple API Health & Latency Checker

API Watchdog is a lightweight Java / Spring Boot service that checks the health and latency of any HTTP/REST API.

You provide a URL – the service:
- sends a GET request  
- measures response time  
- captures HTTP status code  
- returns a JSON payload with timestamp and optional error message  

Perfect for:
- quick checks of internal/external APIs  
- monitoring integration endpoints during development  
- simple health probes in dev/test environments  

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3+

### Clone & run

```bash
git clone https://github.com/YOUR_USERNAME/api-watchdog.git
cd api-watchdog
mvn spring-boot:run
