# 📱 Mobile Service Provider Management System

A full-stack web application for managing mobile service provider operations, including customers, SIM cards, mobile plans, subscriptions, recharge, usage tracking, and secure customer authentication.

---

## 🚀 Overview

The **Mobile Service Provider Management System** provides separate workflows for administrators and customers.

### 👨‍💼 Admin
- Manage customers
- Manage SIM cards
- Create and manage mobile plans
- View and manage provider-side operations

### 👤 Customer
- Register and verify account through email OTP
- Login securely
- View available mobile plans
- Activate available SIM cards
- Subscribe to plans
- Recharge plans
- Simulate and track usage
- View personal subscriptions

---

## ✨ Features

### 🔐 Authentication & Security

- Role-based access control using **Spring Security**
- Session-based authentication
- Secure password hashing using **BCrypt**
- Strong password validation
- Unique email and mobile number validation
- Email OTP-based account verification
- OTP expiry and attempt-limit protection
- Secure ownership checks for customer resources
- Centralized exception handling
- Environment-based configuration for sensitive credentials

### 📶 SIM Management

- View available SIM cards
- Activate a SIM for a customer
- View the customer's active SIM cards
- Validate SIM ownership before customer operations

### 📋 Mobile Plans

- Create and manage mobile plans
- View active plans
- Display plan details and benefits
- Customer access to available plans

### 💳 Subscription & Recharge

- Subscribe to mobile plans
- Track active subscriptions
- Recharge customer plans
- Maintain subscription validity and remaining benefits

### 📊 Usage Management

- Simulate mobile usage
- Track remaining data
- Track remaining talk-time
- Validate available balance before usage
- Prevent unauthorized usage on another customer's SIM

### 📧 Email Verification

- Generate a six-digit verification OTP
- Send OTP through SMTP email
- Store OTP securely as a hash
- OTP expiry support
- Maximum verification attempts
- Single-use verification codes
- Enable customer account after successful email verification

---

## 🏗️ Architecture

The backend follows a layered architecture:

```text
                    ┌─────────────────────┐
                    │      Frontend       │
                    │ HTML / JS / Tailwind│
                    └──────────┬──────────┘
                               │
                               │ REST API
                               ▼
                    ┌─────────────────────┐
                    │    Controllers      │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │      Services       │
                    │ Business Logic      │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │   Repositories      │
                    │ Spring Data JPA     │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │       MySQL         │
                    └─────────────────────┘

# 🛠️ Tech Stack

## Backend
- **Java 21**[cite: 1]
- **Spring Boot 4.1.0**[cite: 1]
- Spring MVC / REST[cite: 1]
- Spring Security[cite: 1]
- Spring Data JPA[cite: 1]
- Hibernate[cite: 1]
- Maven[cite: 1]

### Database
- **MySQL**[cite: 1]
- H2 for testing[cite: 1]

### Frontend
- HTML5[cite: 1]
- Vanilla JavaScript[cite: 1]
- Tailwind CSS[cite: 1]
- Fetch API[cite: 1]

### Tools
- IntelliJ IDEA[cite: 1]
- Visual Studio Code[cite: 1]
- MySQL Workbench[cite: 1]
- Git[cite: 1]
- GitHub[cite: 1]

---

## 📁 Project Structure

```text
Mobile-Service-Provider/
│
├── .vscode/
│   └── launch.json
│
├── telecom/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/provider/telecom/
│   │   │   │       ├── config/
│   │   │   │       ├── controller/
│   │   │   │       ├── dto/
│   │   │   │       ├── entity/
│   │   │   │       ├── enums/
│   │   │   │       ├── exception/
│   │   │   │       ├── repository/
│   │   │   │       ├── security/
│   │   │   │       └── service/
│   │   │   │
│   │   │   └── resources/
│   │   │
│   │   └── test/
│   │
│   ├── pom.xml
│   └── mvnw.cmd
│
├── telecom-frontend/
│   ├── css/
│   ├── js/
│   ├── admin/
│   ├── customer/
│   ├── index.html
│   ├── register.html
│   └── verify-email.html
│
└── README.md
```[cite: 1]

---

## 🔑 Authentication Flow

```text
┌───────────────┐
│   Register    │
└───────┬───────┘
        │
        ▼
┌──────────────────────┐
│ Validate user input  │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ Save customer        │
│ account              │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ Generate Email OTP   │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ Verify Email OTP     │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ Enable Account       │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│       Login          │
└──────────────────────┘
```[cite: 1]

---

## 🔒 Security Highlights

| Security Feature | Implementation |
|---|---|
| Password protection | BCrypt |
| Authentication | Spring Security + HTTP Session |
| Authorization | Role-based access |
| Email verification | OTP |
| OTP storage | Hashed |
| OTP expiry | 10 minutes |
| OTP attempts | Maximum 5 |
| Email uniqueness | Database constraint |
| Phone uniqueness | Database constraint |
| Resource ownership | Service-layer validation |
| Sensitive configuration | Environment variables |[cite: 1]

---

## 🌐 Main API Endpoints

### Authentication

```text
POST /api/auth/register
POST /api/auth/login
GET  /api/auth/me
POST /api/auth/logout

POST /api/auth/verify-email
POST /api/auth/resend-email-verification
```[cite: 1]

### SIM

```text
GET  /api/sim/available
POST /api/sim/activate
GET  /api/sim/my
```[cite: 1]

### Plans

```text
GET /api/plans
```[cite: 1]

### Recharge

```text
POST /api/recharge
```[cite: 1]

### Usage

```text
POST /api/sim/simulate-usage
```[cite: 1]

---

## ⚙️ Setup & Installation

### 1. Clone the repository

```bash
git clone [https://github.com/AdithyaKS2004/mobile-service-provider-management-system.git](https://github.com/AdithyaKS2004/mobile-service-provider-management-system.git)
cd mobile-service-provider-management-system
```[cite: 1]

### 2. Configure MySQL

Create the database:

```sql
CREATE DATABASE telecom;
```[cite: 1]

Configure the database credentials through environment variables:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```[cite: 1]

### 3. Configure Email

The application uses Gmail SMTP for email verification.[cite: 1]

```text
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=<your-gmail-address>
MAIL_PASSWORD=<your-gmail-app-password>
```[cite: 1]

Use a **Gmail App Password**, not your normal Gmail password.[cite: 1]

### 4. Start the Spring Boot backend

```bash
cd telecom
.\mvnw.cmd spring-boot:run
```[cite: 1]

The backend runs on:

```text
http://localhost:8080
```[cite: 1]

### 5. Start the frontend

Open the `telecom-frontend` folder using a local development server.[cite: 1]

The frontend communicates with the backend through REST APIs.[cite: 1]

---

## 🧪 Application Flow

### Customer Flow

```text
Register
   ↓
Email Verification
   ↓
Login
   ↓
Customer Dashboard
   ↓
View Plans
   ↓
Activate SIM
   ↓
Subscribe
   ↓
Recharge
   ↓
Simulate Usage
   ↓
Track Remaining Benefits
```[cite: 1]

### Admin Flow

```text
Admin Login
    ↓
Admin Dashboard
    ↓
Manage Customers
    ↓
Manage SIM Cards
    ↓
Manage Mobile Plans
```[cite: 1]

---

## 🗄️ Core Domain Model

The main entities in the system are:

```text
User
 ├── Customer
 ├── Admin
 └── Authentication / Verification

SimCard
   │
   └── User

Plan
   │
   └── Subscription

Subscription
   ├── SimCard
   ├── Plan
   ├── Remaining Data
   ├── Remaining Talktime
   └── Expiry

VerificationCode
   └── User
```[cite: 1]

---

## 📌 Project Highlights

- Full-stack implementation using **Java + Spring Boot + JavaScript**[cite: 1]
- RESTful backend architecture[cite: 1]
- Database-driven application using JPA/Hibernate[cite: 1]
- Secure session-based authentication[cite: 1]
- Role-based authorization[cite: 1]
- Email-based OTP verification[cite: 1]
- Modular Controller–Service–Repository design[cite: 1]
- Responsive frontend using Tailwind CSS[cite: 1]
- Environment-based configuration for credentials[cite: 1]

---

## 👨‍💻 Author

**Adithya KS**[cite: 1]

GitHub:  
https://github.com/AdithyaKS2004[cite: 1]

---

## 📜 License

This project was developed as an academic/project implementation for learning and demonstration purposes.[cite: 1]
