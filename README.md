📱 Mobile Service Provider Management System

A full-stack telecom management application built with Java, Spring Boot, Spring Security, JPA/Hibernate, MySQL, Vanilla JavaScript, and Tailwind CSS.

The system provides separate Admin and Customer workflows for managing SIM cards, mobile plans, subscriptions, recharge, and usage while enforcing secure authentication and email verification.

✨ Features

🔐 Authentication & Security

Admin and Customer role-based access control

Session-based authentication using Spring Security

Secure password storage with BCrypt

Strong password validation during registration

Unique email and mobile number validation

Email OTP verification for new customer accounts

OTP expiry, attempt limits, single-use codes, and hashed OTP storage

Secure environment-based configuration for database and email credentials

Centralized exception handling and validation responses

👤 Customer Management

Customer registration and login

Email verification before account activation

Customer dashboard

View and manage assigned SIM cards

Activate available SIM cards

View mobile plans

Subscribe to plans

Recharge services

Simulate usage and update remaining data/call balance

🛠️ Admin Management

Admin authentication

Admin dashboard

Manage customers

Manage SIM cards

Manage mobile plans

View and manage telecom service data

🔄 Application Flow

                    ┌──────────────┐
                    │    User      │
                    └──────┬───────┘
                           │
                 ┌─────────▼─────────┐
                 │ Register / Login  │
                 └─────────┬─────────┘
                           │
                  New Customer?
                       ┌───┴───┐
                      Yes     No
                       │       │
               ┌──────▼───┐   │
               │ Email OTP│   │
               │ Verify   │   │
               └──────┬───┘   │
                      │        │
                      ▼        ▼
                Account Active
                      │
             ┌────────┴────────┐
             │                 │
        ┌────▼────┐       ┌────▼─────┐
        │  Admin  │       │ Customer │
        └────┬────┘       └────┬─────┘
             │                 │
      Admin Dashboard    Customer Dashboard
             │                 │
      ┌──────┼──────┐    ┌─────┼───────────────┐
      │      │      │    │     │       │       │
     SIM   Plans Customers SIM  Plans  Recharge Usage

🏗️ Architecture

The backend follows a layered Controller → Service → Repository architecture.

flowchart LR
    A[Frontend<br/>HTML + JavaScript + Tailwind] --> B[REST API]
    B --> C[Spring Security]
    C --> D[Controllers]
    D --> E[Services]
    E --> F[Spring Data JPA]
    F --> G[(MySQL)]
    E --> H[Email SMTP]

🧰 Tech Stack

Layer

Technologies

Language

Java 21, JavaScript, SQL, HTML, CSS

Backend

Spring Boot 4.1.0, Spring MVC, Spring Security

Persistence

Spring Data JPA, Hibernate

Database

MySQL

Frontend

Vanilla JavaScript, HTML5, Tailwind CSS

Authentication

Spring Security, HTTP Session, BCrypt

Email

Spring Boot Mail + SMTP

Build

Maven

Version Control

Git, GitHub

Testing Support

H2, Spring Boot Test Starters

📁 Project Structure

Mobile-Service-Provider/
│
├── .vscode/
│   └── launch.json
│
├── telecom/                  # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/provider/telecom/
│   │   │   │   ├── config/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── entity/
│   │   │   │   ├── enums/
│   │   │   │   ├── exception/
│   │   │   │   ├── repository/
│   │   │   │   ├── security/
│   │   │   │   └── service/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
│
├── telecom-frontend/         # Frontend
│   ├── admin/
│   ├── customer/
│   ├── css/
│   ├── js/
│   ├── index.html
│   ├── register.html
│   └── verify-email.html
│
└── README.md

🔒 Security Highlights

Passwords are stored using BCrypt hashing, not plaintext.

Customer registration always creates a CUSTOMER role through the public registration endpoint.

New customer accounts remain disabled until email verification is completed.

Verification codes are stored as hashes rather than plaintext.

Verification codes expire after a limited period and support a maximum number of failed attempts.

Previous active verification codes are invalidated when a new code is requested.

Database and email credentials are supplied through environment variables rather than hard-coded application configuration.

📧 Email Verification

Customer registration follows this process:

Register
   ↓
Validate details
   ↓
Create customer account
   ↓
Generate 6-digit OTP
   ↓
Hash OTP + store in database
   ↓
Send OTP by email
   ↓
Verify OTP
   ↓
Mark email as verified
   ↓
Enable account
   ↓
Login

Verification codes include:

6-digit OTP

10-minute expiry

Maximum 5 failed attempts

Single-use validation

Hashed storage

Resend support

🔌 Main API Endpoints

Authentication

POST /api/auth/register
POST /api/auth/login
POST /api/auth/verify-email
POST /api/auth/resend-email-verification
GET  /api/auth/me
POST /api/auth/logout

SIM Management

GET  /api/sim/available
POST /api/sim/activate
GET  /api/sim/my
POST /api/sim/simulate-usage

Plans & Services

GET  /api/plans
POST /api/recharge

Additional customer and admin endpoints are implemented for the respective dashboard modules.

⚙️ Configuration

Sensitive configuration is supplied through environment variables.

Database

DB_URL
DB_USERNAME
DB_PASSWORD

Email

MAIL_HOST
MAIL_PORT
MAIL_USERNAME
MAIL_PASSWORD

For Gmail SMTP, the application uses:

MAIL_HOST = smtp.gmail.com
MAIL_PORT = 587

The email account uses an App Password rather than the normal account password.

▶️ Running the Application

Backend

From the telecom directory:

./mvnw spring-boot:run

On Windows:

mvnw.cmd spring-boot:run

The backend runs on:

http://localhost:8080

Frontend

Open the frontend from the telecom-frontend directory using a local web server.

The frontend communicates with the backend through the configured API base URL and uses session credentials for authenticated requests.

✅ Current Project Scope

The implemented system currently covers:

Admin and Customer authentication

Customer registration

Strong password validation

Email OTP account verification

SIM management

Mobile plan management

Subscription management

Recharge functionality

Usage simulation

Role-based dashboards

REST API integration

MySQL persistence

Centralized exception handling

Environment-based configuration

Tailwind CSS production build

Git/GitHub version control

👨‍💻 Project

Mobile Service Provider Management System

Built as a full-stack software project using modern Java/Spring Boot backend development and a lightweight JavaScript frontend.
