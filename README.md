# ✈️ Flight Reservation Microservices System

A production-style **microservices-based airline booking system** built using Spring Boot, designed with real-world distributed architecture, service-to-service communication, and modular scalability.

---

## 🚀 Key Highlights

- 🧩 Microservices architecture (Reservation + Check-In services)
- 🔁 Inter-service communication using REST APIs
- 🪑 Dynamic seat selection with availability tracking
- 📄 PDF ticket generation with QR code
- 📧 Automated email delivery with ticket attachment
- 🧱 Clean layered architecture (Controller → Service → Repository)

---

## 🏗️ Microservices Architecture

This system is split into independent services:

### 1️⃣ Flight Reservation Service (Port 8080)
- Flight search
- Booking & reservation
- Seat allocation
- PDF ticket generation
- Email service

### 2️⃣ Flight Check-In Service (Port 8081)
- Fetch reservation details via REST
- Complete passenger check-in

### 🔁 Communication Flow
- Check-In service calls Reservation service via REST (`RestTemplate`)
- Services are loosely coupled and independently deployable

---

## 🛠️ Tech Stack

| Layer        | Technology |
|-------------|-----------|
| Backend     | Spring Boot, Spring MVC |
| Persistence | Spring Data JPA, Hibernate |
| Frontend    | Thymeleaf |
| Database    | MySQL |
| Communication | REST APIs |
| PDF         | iText (QR Code) |
| Build Tool  | Maven |
| Java        | 21+ |

---

## ✨ Features

### 🔐 User Management
- Registration & login with validation  

### 🔍 Flight Search
- Search by source, destination, and date  

### 🪑 Seat Selection
- Real-time seat availability per flight  
- Prevents double booking  

### 🧾 Booking System
- Complete reservation flow  
- Generates unique PNR  

### 📄 Ticket Generation
- PDF boarding pass with QR code  
- Downloadable via UI  

### 📧 Email Service
- Sends ticket automatically after booking  

### 🔁 Check-In System
- REST-based integration  
- Fetch reservation → complete check-in  

---

## 🔁 End-to-End Flow

User → Search Flights → Select Flight → Select Seat  
→ Enter Details → Book → Generate Ticket → Email Sent  
→ Check-In via separate microservice  

---

## 📁 Project Structure

flight-reservation-system/
├── flight-reservation/   (Microservice 1)
│   ├── controllers/
│   ├── services/
│   ├── repos/
│   ├── entities/
│   └── templates/
│
├── flight-checkin/       (Microservice 2)
│   ├── controllers/
│   ├── services/
│   └── templates/

---

## 🔗 API Endpoints

### Reservation Service (8080)
- GET /findFlights  
- POST /completeReservation  
- GET /reservations/{id}  

### Check-In Service (8081)
- GET /startCheckIn  
- POST /completeCheckIn  

---

## ⚙️ Setup & Run

### Prerequisites
- Java 21+
- Maven
- MySQL  

### Clone Repository
git clone https://github.com/Manish12858/flight-reservation-system.git  
cd flight-reservation-system  

### Run Services

Start Reservation Service:
cd flight-reservation  
mvn spring-boot:run  

Start Check-In Service:
cd flight-checkin  
mvn spring-boot:run  

---

## 🧠 Engineering Challenges Solved

- Designed service-to-service communication via REST  
- Resolved endpoint mismatches between microservices  
- Handled Thymeleaf null pointer issues  
- Implemented classpath-based resource loading for PDF  
- Fixed PDF streaming and download issues  
- Managed data consistency across services  

---

## 📈 Future Improvements

- API Gateway (Spring Cloud Gateway)
- real payment gateway
- Service Discovery (Eureka Server)  
- Authentication (Spring Security + JWT)  
- Distributed tracing & logging  
- Docker containerization  

---

## 💡 Why This Project Stands Out

- Microservices-based design (not monolithic)  
- Real-world airline workflow  
- Service integration via REST  
- Full-stack implementation  
- Production-style problem solving  

---

## 👨‍💻 Author

Manish Kumar  
Java Backend Developer  

---

⭐ If you found this project useful, consider giving it a star!
