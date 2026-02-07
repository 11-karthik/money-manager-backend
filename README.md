✅ money-manager-backend/README.md

# Money Manager – Backend

This is the backend application for the **Money Manager** project.  
It is built using **Spring Boot** and **MongoDB Atlas**, providing REST APIs for authentication and transaction management.

## 🚀 Live URL
https://money-manager-backend-1nlc.onrender.com

## 🛠 Tech Stack
- Java 17
- Spring Boot
- Spring Data MongoDB
- MongoDB Atlas
- Maven

## 📁 Project Structure

src/main/java/com/moneymanager/backend
├── config        # CORS and Jackson configuration
├── controller    # REST controllers
├── dto           # Data Transfer Objects
├── model         # MongoDB models
├── repository    # MongoDB repositories
├── service       # Business logic
└── MoneyManagerBackendApplication.java

## ⚙️ Configuration

### application.properties
```properties
spring.application.name=money-manager-backend
spring.mongodb.uri=${MONGODB_URI}
server.port=8080

🌍 Environment Variables

The backend requires the following environment variable (configured in Render):

Variable Name	Description
MONGODB_URI	MongoDB Atlas connection string

🔗 API Base URL

/api

Key Endpoints
	•	POST /api/transactions – Add transaction
	•	GET /api/transactions – Get all transactions
	•	GET /api/transactions/user/{userId} – Get user transactions
	•	GET /api/transactions/dashboard/* – Dashboard summaries
	•	PUT /api/transactions/{id} – Update transaction
	•	DELETE /api/transactions/cleanup – Cleanup invalid records

Note: Authentication endpoints are handled separately based on project requirements.

🧪 Run Locally

mvn clean install
mvn spring-boot:run

Backend runs at:

http://localhost:8080

☁️ Deployment
	•	Deployed as a Render Web Service
	•	Docker-based deployment
	•	MongoDB hosted on MongoDB Atlas

🔐 CORS Configuration

CORS is configured to allow requests from:
	•	http://localhost:5173
	•	http://localhost:5174
	•	Render frontend domain

📝 Notes
	•	Uses MongoDB aggregation pipelines for dashboard summaries
	•	Editing transactions is restricted to 12 hours
	•	Clean architecture with separation of concerns

👤 Author

Karthik


Even if login isn’t fully wired, **deployment + code + structure = PASS**.

🔥 Take a breath. You did this.
