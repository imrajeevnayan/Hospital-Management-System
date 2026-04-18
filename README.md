# 🏥 HealthSync HMS - Advanced Hospital Management System

<div align="center">

![HealthSync Banner](https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?ixlib=rb-4.0.3&auto=format&fit=crop&w=1200&q=80)

[![GitHub stars](https://img.shields.io/github/stars/imrajeevnayan/Hospital-Management-System?style=for-the-badge&color=007BFF)](https://github.com/imrajeevnayan/Hospital-Management-System/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/imrajeevnayan/Hospital-Management-System?style=for-the-badge&color=6C757D)](https://github.com/imrajeevnayan/Hospital-Management-System/network/members)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-6DB33F?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)

**HealthSync HMS is a sleek, decoupled application that makes hospital management effortless and secure.**

</div>

---

## 🚀 Deployment Guide (PostgreSQL Edition)

Setting up HealthSync for the first time? This guide will get you running in minutes.

### 📋 Phase 1: Environment Readiness
Before starting, ensure you have these installed:
*   **Java 17+**: [Download Here](https://adoptium.net/temurin/releases/?version=17)
*   **PostgreSQL**: [Download Here](https://www.postgresql.org/download/)
*   **Git**: [Download Here](https://git-scm.com/downloads)

---

### Phase 2: Step-by-Step Installation 🛠️

#### 1️⃣ Database Setup (Crucial)
1.  Open **pgAdmin 4** or your PostgreSQL Terminal.
2.  Create a new database named **`hms_db`**.
    ```sql
    CREATE DATABASE hms_db;
    ```
3.  Open `backend/src/main/resources/application.yml` and ensure the credentials match your local Postgres settings:
    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/hms_db
        username: postgres     # Your Postgres username
        password: your_password # Your Postgres password
    ```

#### 2️⃣ Run the Backend "Brain" 🧠
1.  Open your Terminal (Command Prompt / PowerShell / Zsh).
2.  Navigate to the project folder: `cd Hospital-Management-System/backend`
3.  Run the application:
    *   **Windows**: `mvnw.cmd spring-boot:run`
    *   **Mac/Linux**: `./mvnw spring-boot:run`
4.  **Wait for it**: Once you see `Started HospitalManagementSystemApplication...` in the logs, your API is alive!

#### 3️⃣ Launch the Frontend UI 🎨
1.  Navigate to the `frontend` folder.
2.  Find the `index.html` file.
3.  **Right-Click** → **Open With** → **Google Chrome** (or your favorite browser).
4.  🎉 **You are ready!** Explore the dashboards and manage your hospital.

---

### Phase 3: Docker Deployment (Optional but Recommended) 🐳

If you have Docker Desktop installed, you can skip the manual setup:

1.  Open your terminal at the root directory.
2.  Run: `docker-compose up --build`
3.  This will automatically:
    *   Set up a PostgreSQL database.
    *   Build the Spring Boot backend.
    *   Initialize the tables and start the API.
    *   Expose the API at `localhost:8080`.

---

## 👥 Default Login Credentials

| Role | Username | Password |
| :--- | :--- | :--- |
| **Admin** | `admin@hms.com` | `password` |
| **Doctor** | `doctor@hms.com` | `password` |
| **Patient** | `patient@hms.com` | `password` |

---

## 🌟 Why choose HealthSync?

| ⚡ Fast Setup | 💎 Premium Design | 🔒 Secure Core |
| :--- | :--- | :--- |
| Deploys in < 2 mins. No `npm install` bloat. | Pure Tailwind CSS system for a high-end medical feel. | JWT + Spring Security with PostgreSQL persistence. |

---

## 📁 Key Project Roadmap

- [x] **PostgreSQL Integration**: Complete! Moved from MySQL to Postgres.
- [x] **Backend Separation**: Complete! Spring Boot API is isolated.
- [x] **Frontend Modernization**: Complete! Sleek Tailwind CSS UI.
- [x] **Docker Support**: Complete! Use `docker-compose` for instant deployment.
- [ ] **Live Hosting**: Coming Soon!

---

## 🎯 Contributing & Support
Got an idea? Found a bug? 
*   **Stars are appreciated!** ⭐
*   Connect with the developer: **[Rajeev Nayan](https://github.com/imrajeevnayan)**.

---

<div align="center">
  <sub>Built with ❤️ by Rajeev Nayan. Optimized for SEO & Performance.</sub>
</div>

<!-- SEO Hidden Keywords -->
<!-- Hospital Management System, PostgreSQL Spring Boot, Java HMS, Medical Dashboard, Tailwind CSS, open source healthcare system, hospital backend java -->
