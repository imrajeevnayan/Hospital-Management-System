# 🏥 HealthSync HMS - Advanced Hospital Management System

<div align="center">

![HealthSync Banner](https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?ixlib=rb-4.0.3&auto=format&fit=crop&w=1200&q=80)

[![GitHub stars](https://img.shields.io/github/stars/imrajeevnayan/Hospital-Management-System?style=for-the-badge&color=007BFF)](https://github.com/imrajeevnayan/Hospital-Management-System/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/imrajeevnayan/Hospital-Management-System?style=for-the-badge&color=6C757D)](https://github.com/imrajeevnayan/Hospital-Management-System/network/members)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-6DB33F?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-Modern_UI-38B2AC?style=for-the-badge&logo=tailwind-css)](https://tailwindcss.com)

**Modernizing Healthcare Management with a Decoupled, Robust, and User-Centric Digital Ecosystem.**

[Explore Documentation](#-getting-started) • [View Features](#-key-features) • [Report Bug](https://github.com/imrajeevnayan/Hospital-Management-System/issues) • [Request Feature](https://github.com/imrajeevnayan/Hospital-Management-System/issues)

</div>

---

## 🌟 Introduction

**HealthSync HMS** is a next-generation Hospital Management System designed to bridge the gap between healthcare providers and patients. Built with a decoupled architecture, it features a high-performance **Spring Boot API** and a stunning, responsive **Tailwind CSS Frontend**. 

Whether you are a doctor managing appointments or a patient seeking care, HealthSync provides a seamless, secure, and intuitive experience.

---

## 🚀 Key Features

### 👨‍⚕️ For Doctors
- **Smart Dashboard**: Real-time overview of appointments and patient status.
- **Digital Prescriptions**: Generate and manage electronic prescriptions instantly.
- **Medical History**: Access comprehensive patient records at your fingertips.

### 🤒 For Patients
- **Easy Booking**: Schedule appointments with specialized doctors in seconds.
- **Personal Records**: Secure access to medical history, bills, and prescriptions.
- **Payment Portal**: Intuitive billing and insurance tracking.

### 🛡️ For Administrators
- **User Management**: Control roles and permissions across the entire system.
- **Department Control**: Organize hospital units and staff assignments efficiently.
- **System Analytics**: Gain insights into hospital operations and financial health.

---

## 🛠️ Technology Stack

| Architecture | Technologies |
| :--- | :--- |
| **Backend** | Spring Boot 3.2, Spring Security (JWT), Spring Data JPA, MySQL 8.0 |
| **Frontend** | Vanilla JavaScript, Tailwind CSS (CDN), Google Fonts (Inter) |
| **Deployment** | Maven, GitHub Actions, Docker (Ready) |
| **Design** | Glassmorphism, Premium Color Palette, Responsive Layouts |

---

## 📁 Project Structure

```bash
Hospital-Management-System/
├── 📂 backend         # Spring Boot Core (Java, REST API, Security)
│   ├── src/           # Controller, Service, Repository, Entity
│   └── pom.xml        # Maven Dependencies
├── 📂 frontend        # Modern UI (HTML, CSS, JavaScript)
│   ├── public/        # Static assets and Fragments
│   └── index.html     # Entry Point
└── 📄 README.md       # Project Documentation
```

---

## 🏁 Getting Started

Follow these simple steps to get your own instance of HealthSync HMS running locally.

### 📋 Prerequisites
*   **Java 17+** (JDK)
*   **MySQL Server** (Running)
*   **Maven** (Optional, `./mvnw` included)

### 1️⃣ Database Setup
Create a database named `hms` and configure your credentials in `backend/src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hms
    username: your_username
    password: your_password
```

### 2️⃣ Run Backend
```bash
cd backend
./mvnw spring-boot:run
```

### 3️⃣ Launch Frontend
Since the frontend is built with ultra-modern static technologies, simply open the `frontend/index.html` in your favorite browser. No build steps required!

---

## 🎨 User Interface Preview

<div align="center">

| Admin Dashboard | Patient Portal |
| :---: | :---: |
| ![Admin](https://via.placeholder.com/400x250/007BFF/FFFFFF?text=Admin+Dashboard+UI) | ![Patient](https://via.placeholder.com/400x250/10B981/FFFFFF?text=Patient+Portal+UI) |

| Doctor View | Login Page |
| :---: | :---: |
| ![Doctor](https://via.placeholder.com/400x250/F59E0B/FFFFFF?text=Doctor+Interface) | ![Login](https://via.placeholder.com/400x250/3B82F6/FFFFFF?text=Secure+Login+Page) |

</div>

---

## 🤝 Contributing

Contributions make the open-source community an amazing place!
1.  **Fork** the Project
2.  **Create** your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  **Commit** your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  **Push** to the Branch (`git push origin feature/AmazingFeature`)
5.  **Open** a Pull Request

---

## 📄 License

Distributed under the **MIT License**. See `LICENSE` for more information.

---

## 👨‍💻 Developed By

**[Rajeev Nayan](https://github.com/imrajeevnayan)**
*Passionate Full-Stack Developer & UI Enthusiast*

---

<div align="center">

**Hospital Management System Development** • **Spring Boot API** • **Tailwind CSS UI**

[Back To Top](#-healthsync-hms---advanced-hospital-management-system)

</div>

<!-- SEO Tags (Hidden) -->
<!-- 
Keywords: Hospital Management System, Spring Boot Hospital App, React Hospital UI, Tailwind CSS Medical Dashboard, HMS Java, Healthcare Management Software, Open Source HMS, Medical Records System, Doctor Appointment Booking System.
-->
