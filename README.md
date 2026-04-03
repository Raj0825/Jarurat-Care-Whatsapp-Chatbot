# 🤖 WhatsApp Chatbot Backend — Jarurat Care Assignment

A Spring Boot REST API that simulates a WhatsApp chatbot backend.
Built as part of the Jarurat Care internship selection assignment.

---

## 📌 Features

- ✅ `POST /webhook` — Receives simulated WhatsApp messages and returns predefined replies
- ✅ `GET /logs` — View all incoming messages logged in memory
- ✅ `GET /health` — Health check endpoint (used by Render for deployment)
- ✅ Input validation with proper error responses
- ✅ Full unit + integration test coverage
- ✅ Docker + Render deployment support

---

## 🚀 Quick Start (Local)

### Prerequisites
- Java 17+
- Maven 3.8+

### Run Locally

```bash
# 1. Clone the repository
git clone https://github.com/YOUR_USERNAME/whatsapp-chatbot.git
cd whatsapp-chatbot

# 2. Build and run
mvn spring-boot:run
```

Server starts at: **http://localhost:8080**

---

## 📡 API Endpoints

### 1. POST /webhook — Send a Message

```bash
curl -X POST http://localhost:8080/webhook \
  -H "Content-Type: application/json" \
  -d '{"from": "+919876543210", "message": "Hi"}'
```

**Response:**
```json
{
  "to": "+919876543210",
  "reply": "Hello! 👋 Welcome to Jarurat Care. How can I assist you today?",
  "status": "delivered",
  "timestamp": "2024-01-15T10:30:01"
}
```

---

### 2. Predefined Replies

| Message Sent     | Bot Reply                                     |
|------------------|-----------------------------------------------|
| `Hi` / `Hello`   | Hello! 👋 Welcome to Jarurat Care...          |
| `Bye` / `Goodbye`| Goodbye! 👋 Thank you for contacting...       |
| `help`           | Menu with 3 options (Book Nurse, Home Care...) |
| `1`              | Book a Nurse confirmation                     |
| `2`              | Home Care Services confirmation               |
| `3`              | Emergency Support response                    |
| `Thanks`         | You're welcome! 😊                            |
| Any other text   | Sorry, I didn't understand that 🤔            |

---

### 3. GET /logs — View All Messages

```bash
curl http://localhost:8080/logs
```

**Response:**
```json
{
  "total_messages": 3,
  "logs": [
    {
      "id": 1,
      "from": "+919876543210",
      "receivedMessage": "Hi",
      "botReply": "Hello! 👋 Welcome to Jarurat Care...",
      "receivedAt": "2024-01-15T10:30:01"
    }
  ]
}
```

---

### 4. GET /health — Health Check

```bash
curl http://localhost:8080/health
```

---

## 🧪 Run Tests

```bash
mvn test
```

---

## 🐳 Run with Docker

```bash
# Build image
docker build -t whatsapp-chatbot .

# Run container
docker run -p 8080:8080 whatsapp-chatbot
```

---

## ☁️ Deploy on Render (Free Hosting)

1. Push this repository to GitHub
2. Go to [render.com](https://render.com) → **New Web Service**
3. Connect your GitHub repo
4. Render auto-detects `render.yaml` and deploys using Docker
5. Your API will be live at: `https://whatsapp-chatbot-jarurat.onrender.com`

---

## 🏗️ Project Structure

```
whatsapp-chatbot/
├── src/
│   ├── main/java/com/jarurat/chatbot/
│   │   ├── WhatsappChatbotApplication.java   ← Entry point
│   │   ├── controller/
│   │   │   └── WebhookController.java        ← REST endpoints
│   │   ├── service/
│   │   │   └── ChatbotService.java           ← Reply logic + logging
│   │   └── model/
│   │       ├── IncomingMessage.java          ← Request DTO
│   │       ├── BotResponse.java              ← Response DTO
│   │       └── MessageLog.java               ← Log record
│   └── main/resources/
│       └── application.properties
├── Dockerfile
├── render.yaml
└── pom.xml
```

---

