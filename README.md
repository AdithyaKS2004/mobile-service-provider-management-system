Mobile Service Provider Management System

A full-stack telecom service provider management system built with Java, Spring Boot, Spring Security, JPA/Hibernate, MySQL, HTML, JavaScript, and Tailwind CSS.

The system provides separate ADMIN and CUSTOMER workflows for managing customers, SIM cards, mobile plans, subscriptions, recharge, and usage.

Features

Authentication & Security

Customer registration with full name, email, phone number, and password validation.

Strong password validation with uppercase, lowercase, number, and special-character requirements.

Password hashing using BCrypt.

Session-based authentication using Spring Security.

Role-based authorization for ADMIN and CUSTOMER users.

Secure logout and current-user session handling.

Unique email and phone number validation.

Email OTP verification for account activation.

Six-digit OTP generation using SecureRandom.

OTPs stored as password hashes instead of plaintext.

OTP expiry, single-use validation, resend functionality, and maximum-attempt protection.

Centralized exception handling and validation responses.

Admin Module

Admin dashboard.

Customer management.

SIM card management.

Create SIM cards with phone and IMSI details.

View pending SIM activation requests.

Approve SIM activation requests.

Create and manage mobile plans.

Update plan details.

Activate or deactivate plans.

View active plans.

Customer Module

Customer dashboard.

View available SIM cards.

Request SIM activation.

View owned SIM cards.

View mobile plans.

Subscribe to plans.

Manage active subscriptions.

Recharge services.

Simulate mobile usage.

Track remaining data and talktime balances.

Ownership validation to prevent access to another customer's SIM or subscription data.

Usage & Subscription Management

Active subscription validation before usage.

Subscription expiry validation.

Automatic subscription status update when expired.

Data balance deduction for simulated data usage.

Talktime balance deduction for simulated calls.

Validation for insufficient data or talktime balance.

Transaction and recharge handling.

Technology Stack

Backend

Java 21

Spring Boot 4.1.0

Spring MVC / REST APIs

Spring Data JPA

Hibernate

Spring Security

Spring Validation

Spring Mail

Maven

Database

MySQL

H2 for testing where configured

Frontend

HTML5

Vanilla JavaScript

Fetch API

Tailwind CSS

Tools

IntelliJ IDEA

Visual Studio Code

MySQL Workbench

Git

GitHub

Architecture

The backend follows a layered architecture:

Controller
    ↓
Service
    ↓
Repository
    ↓
Entity / Database

Authentication and authorization are handled through Spring Security, while frontend requests communicate with the backend through REST APIs using session credentials.

Main Backend Modules

Authentication
├── Registration
├── Login / Logout
├── Current User
├── Email Verification
└── Session-based Authorization

Admin
├── Customer Management
├── SIM Management
└── Plan Management

Customer
├── SIM Activation
├── Plan Subscription
├── Recharge
└── Usage Tracking

Important API Endpoints

Authentication

POST /api/auth/register
POST /api/auth/login
POST /api/auth/logout
GET  /api/auth/me
POST /api/auth/verify-email
POST /api/auth/resend-email-verification

Plans

GET   /api/plans
POST  /api/admin/plans
PUT   /api/admin/plans/{planId}
PATCH /api/admin/plans/{planId}/status

Admin SIM Management

POST  /api/admin/sims
GET   /api/admin/sims/pending
PATCH /api/admin/sims/{simId}/approve

Customer SIM Management

GET  /api/sim/available
POST /api/sim/activate
GET  /api/sim/my

Recharge & Usage

POST /api/recharge
POST /api/sim/simulate-usage

Email Verification Flow

Customer Registration
        ↓
User Account Created
        ↓
Six-Digit Verification OTP Generated
        ↓
OTP Hashed and Stored
        ↓
OTP Sent Through SMTP Email
        ↓
Customer Enters OTP
        ↓
OTP Validated
        ↓
Email Verified
        ↓
Account Enabled
        ↓
Customer Can Login

Configuration

The application uses environment variables for database and email credentials.

DB_URL
DB_USERNAME
DB_PASSWORD

MAIL_HOST
MAIL_PORT
MAIL_USERNAME
MAIL_PASSWORD

Example mail configuration:

spring.mail.host=${MAIL_HOST}
spring.mail.port=${MAIL_PORT:587}
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

Sensitive credentials should be supplied through environment variables or the local VS Code debug configuration and should not be committed to Git.

Running the Backend

Configure MySQL and create the required database.

Provide the database and mail environment variables.

Open the project at the repository root so the .vscode configuration is available.

Start the Spring Boot application from the telecom module.

For a Maven build:

cd telecom
.\mvnw.cmd clean package

The backend runs on:

http://localhost:8080

Running the Frontend

The frontend is located in the telecom-frontend directory.

telecom-frontend/
├── index.html
├── register.html
├── verify-email.html
├── admin/
├── customer/
├── js/
└── css/

The frontend uses the generated Tailwind stylesheet:

css/output.css

The frontend communicates with the backend using the Fetch API and sends session credentials with requests.

For local development, serve the frontend from a local web server such as VS Code Live Server. The backend CORS configuration allows the local frontend origin.

Project Structure

Mobile-Service-Provider/
│
├── .gitignore
├── .vscode/
│   └── launch.json
│
├── telecom/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/provider/telecom/
│       │   │   ├── config/
│       │   │   ├── controller/
│       │   │   ├── dto/
│       │   │   ├── entity/
│       │   │   ├── enums/
│       │   │   ├── exception/
│       │   │   ├── repository/
│       │   │   ├── security/
│       │   │   └── service/
│       │   └── resources/
│       │       └── application.properties
│       └── test/
│
└── telecom-frontend/
    ├── index.html
    ├── register.html
    ├── verify-email.html
    ├── admin/
    ├── customer/
    ├── js/
    └── css/

Git Repository

The project is maintained as a single Git repository containing both the Spring Boot backend and the frontend.

Backend  → telecom/
Frontend → telecom-frontend/

Sensitive local configuration is excluded from version control.      
