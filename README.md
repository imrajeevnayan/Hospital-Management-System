# Hospital Management System (Spring Boot + Thymeleaf)

A comprehensive, full-stack Hospital Management System built with **Spring Boot** and **Thymeleaf** templates, providing complete healthcare management functionality.

## 🏗️ Architecture Overview

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.2.0
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA
- **Security**: Spring Security
- **Authentication**: JWT-based (configurable)
- **View Engine**: Thymeleaf
- **Build Tool**: Maven

### Frontend (Thymeleaf Templates)
- **Styling**: Tailwind CSS
- **Responsive Design**: Mobile-first approach
- **UI Components**: Custom components with Tailwind
- **Interactive Elements**: JavaScript for dynamic functionality

## 🎯 Features

### Multi-Role Support
- **Patients**: Book appointments, view medical records, manage prescriptions, pay bills
- **Doctors**: Manage patient records, write prescriptions, schedule appointments
- **Nurses**: Monitor patients, manage medications, coordinate care
- **Administrators**: Manage users, departments, system operations, analytics

### Core Modules
1. **Authentication & Authorization**
   - Role-based access control
   - Secure login/logout
   - Password management
   - Session management

2. **Patient Management**
   - Complete patient profiles
   - Medical history tracking
   - Emergency contacts
   - Insurance information

3. **Appointment System**
   - Smart scheduling
   - Doctor availability management
   - Appointment status tracking
   - Automated reminders

4. **Medical Records**
   - Comprehensive medical history
   - Diagnosis tracking
   - Treatment plans
   - Progress notes

5. **Prescription Management**
   - Electronic prescriptions
   - Medication tracking
   - Refill management
   - Drug interaction warnings

6. **Billing & Insurance**
   - Automated bill generation
   - Insurance processing
   - Payment tracking
   - Financial reporting

7. **Department Management**
   - Hospital organization
   - Bed management
   - Equipment tracking
   - Staff assignment

## 🗄️ Database Schema

### Core Entities
- **Users**: Multi-role user system (Patient, Doctor, Nurse, Admin)
- **Patients**: Extended patient information
- **Departments**: Hospital organization units
- **Appointments**: Scheduling and management
- **Medical Records**: Patient health information
- **Prescriptions**: Medication management
- **Bills**: Financial transactions

### Key Relationships
- User ↔ Patient (One-to-One)
- Doctor ↔ Appointments (One-to-Many)
- Patient ↔ Medical Records (One-to-Many)
- Patient ↔ Prescriptions (One-to-Many)
- Patient ↔ Bills (One-to-Many)
- Department ↔ Staff (One-to-Many)

## 🚀 Quick Start

### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven 3.8+
- IDE (IntelliJ IDEA/Eclipse)

### 1. Clone and Setup
```bash
# The project is ready to run - all files are in /workspace
cd /workspace
```

### 2. Database Setup
```sql
-- Create database
CREATE DATABASE hms;

-- Create user (or use existing)
CREATE USER 'hms_user'@'localhost' IDENTIFIED BY 'hms_password';
GRANT ALL PRIVILEGES ON hms.* TO 'hms_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configuration
Update `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hms?useSSL=false&serverTimezone=UTC
    username: your_mysql_username
    password: your_mysql_password
```

### 4. Run the Application
```bash
# Build and run
./mvnw spring-boot:run

# Or using Maven
mvn clean compile
mvn spring-boot:run
```

### 5. Access the Application
- **URL**: http://localhost:8080/hms
- **Default Port**: 8080

## 👥 Demo Accounts

| Role | Email | Password | Description |
|------|-------|----------|-------------|
| **Patient** | patient@hms.com | password | Book appointments, view records |
| **Doctor** | doctor@hms.com | password | Manage patients, write prescriptions |
| **Nurse** | nurse@hms.com | password | Monitor patients, dispense medications |
| **Admin** | admin@hms.com | password | System administration, user management |

## 📁 Project Structure

```
/workspace/
├── src/main/java/com/hms/
│   ├── HospitalManagementSystemApplication.java
│   ├── config/
│   │   └── SecurityConfig.java
│   ├── entity/
│   │   ├── BaseEntity.java
│   │   ├── User.java
│   │   ├── Patient.java
│   │   ├── Appointment.java
│   │   ├── MedicalRecord.java
│   │   ├── Prescription.java
│   │   ├── Bill.java
│   │   └── Department.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── PatientRepository.java
│   │   ├── AppointmentRepository.java
│   │   ├── MedicalRecordRepository.java
│   │   ├── PrescriptionRepository.java
│   │   ├── BillRepository.java
│   │   └── DepartmentRepository.java
│   ├── service/
│   │   ├── UserService.java
│   │   ├── AppointmentService.java
│   │   ├── MedicalRecordService.java
│   │   ├── PrescriptionService.java
│   │   └── BillService.java
│   ├── controller/
│   │   ├── HomeController.java
│   │   └── PatientController.java
│   └── security/
│       ├── CustomUserDetailsService.java
│       ├── CustomUserDetails.java
│       └── RestAuthenticationEntryPoint.java
├── src/main/resources/
│   ├── application.yml
│   ├── schema.sql
│   ├── data.sql
│   └── templates/
│       ├── layouts/
│       │   └── base.html
│       └── *.html (view templates)
└── pom.xml
```

## 🔧 Configuration

### Application Properties
```yaml
# Database Configuration
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hms
    username: hms_user
    password: hms_password

# JPA Configuration
jpa:
  hibernate:
    ddl-auto: create-drop  # Creates schema automatically
  show-sql: true
  properties:
    hibernate:
      dialect: org.hibernate.dialect.MySQL8Dialect

# Security Configuration
hms:
  security:
    jwt:
      secret: mySecretKey123456789
      expiration: 86400  # 24 hours
```

### Security Roles
- `ROLE_PATIENT`: Patient portal access
- `ROLE_DOCTOR`: Clinical workflow access
- `ROLE_NURSE`: Care coordination access
- `ROLE_ADMIN`: Full system access

## 🎨 Frontend Features

### Design System
- **CSS Framework**: Tailwind CSS
- **Color Scheme**: Healthcare-appropriate blue tones
- **Typography**: Clean, readable fonts
- **Components**: Reusable UI components
- **Responsive**: Mobile-first design

### User Interface
- **Role-based Dashboards**: Customized for each user type
- **Navigation**: Intuitive sidebar navigation
- **Forms**: Interactive forms with validation
- **Data Tables**: Sortable, searchable tables
- **Charts**: Visual data representation

## 🔒 Security Features

### Authentication
- Form-based login
- Session management
- Password encryption (BCrypt)
- Remember me functionality

### Authorization
- Role-based access control
- Method-level security
- Route protection
- CSRF protection

### Data Security
- SQL injection prevention
- XSS protection
- Input validation
- Secure session handling

## 📊 API Endpoints

### Authentication
- `POST /hms/login` - User authentication
- `GET /hms/logout` - User logout

### Patient Portal
- `GET /hms/patient/dashboard` - Patient dashboard
- `GET /hms/patient/appointments` - Patient appointments
- `GET /hms/patient/medical-records` - Medical records
- `GET /hms/patient/prescriptions` - Prescriptions
- `GET /hms/patient/bills` - Billing information
- `POST /hms/patient/book-appointment` - Book appointment

### Doctor Portal
- `GET /hms/doctor/dashboard` - Doctor dashboard
- `GET /hms/doctor/appointments` - Doctor appointments
- `GET /hms/doctor/patients` - Doctor's patients
- `POST /hms/doctor/medical-records` - Create medical record
- `POST /hms/doctor/prescriptions` - Write prescription

### Admin Portal
- `GET /hms/admin/dashboard` - Admin dashboard
- `GET /hms/admin/users` - User management
- `GET /hms/admin/departments` - Department management
- `GET /hms/admin/reports` - System reports

## 🧪 Testing

### Demo Data
The application includes comprehensive demo data:
- Sample users for all roles
- Mock patient records
- Sample appointments
- Test prescriptions
- Demo billing data

### Sample Queries
```sql
-- View all patients
SELECT u.first_name, u.last_name, u.email, p.patient_id
FROM users u 
JOIN patients p ON u.id = p.user_id 
WHERE u.role = 'PATIENT';

-- View today's appointments
SELECT a.*, dp.first_name as doctor_name, pt.first_name as patient_name
FROM appointments a
JOIN users dp ON a.doctor_id = dp.id
JOIN users pt ON a.patient_id = pt.id
WHERE a.appointment_date = CURDATE();
```

## 🚀 Deployment

### Development
```bash
# Run with hot reload
mvn spring-boot:run
```

### Production
```bash
# Create production build
mvn clean package -Pprod

# Run JAR file
java -jar target/hms-1.0.0.jar
```

### Environment Variables
```bash
# Database
DB_URL=jdbc:mysql://localhost:3306/hms
DB_USERNAME=hms_user
DB_PASSWORD=hms_password

# Security
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400

# Mail
MAIL_HOST=smtp.gmail.com
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password
```

## 🔄 Development Roadmap

### Current Features ✅
- [x] Multi-role authentication
- [x] Patient management
- [x] Appointment scheduling
- [x] Medical records
- [x] Prescription management
- [x] Billing system
- [x] Department management
- [x] Role-based dashboards
- [x] Responsive design
- [x] Demo data

### Future Enhancements 📋
- [ ] Real-time notifications
- [ ] Video consultations
- [ ] Advanced analytics
- [ ] Mobile app integration
- [ ] AI-powered diagnostics
- [ ] Electronic health records
- [ ] Laboratory integration
- [ ] Pharmacy management
- [ ] Insurance verification
- [ ] Audit logging

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**RAJEEV NAYAN** - *Hospital Management System Development*

---

**🏥 Hospital Management System - Streamlining Healthcare Operations**