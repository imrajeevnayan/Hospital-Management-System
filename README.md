# Hospital Management System (Spring Boot + Thymeleaf)

A comprehensive, full-stack Hospital Management System built with **Spring Boot** and **Thymeleaf** templates, providing complete healthcare management functionality.

![Hospital Management System Logo]([https://www.google.com/imgres?q=hospital%20Management%20System&imgurl=https%3A%2F%2Fwww.maxaix.com%2Fblog%2Fwp-content%2Fuploads%2F2024%2F03%2FHospital-Management-System-development.jpg&imgrefurl=https%3A%2F%2Fwww.maxaix.com%2Fblog%2Fhospital-management-system-development%2F&docid=EL7RGrL5gEHyHM&tbnid=0BNze9-sQCA95M&vet=12ahUKEwi8hNupl_yQAxU8bfUHHetoNwMQM3oECBUQAA..i&w=1000&h=600&hcb=2&ved=2ahUKEwi8hNupl_yQAxU8bfUHHetoNwMQM3oECBUQAA])

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

![Spring Boot HMS Architecture Diagram](https://javatechonline.com/wp-content/uploads/2025/04/hospital-management-system-architecture-diagram.png)

## 🎯 Features

### Multi-Role Support
- **Patients**: Book appointments, view medical records, manage prescriptions, pay bills  
  ![Patient Icon](https://thenounproject.com/browse/icons/term/patient-centered-care/icon-1234567-patient/)
- **Doctors**: Manage patient records, write prescriptions, schedule appointments  
  ![Doctor Icon](https://www.flaticon.com/free-icon/doctor_1234568?term=doctor)
- **Nurses**: Monitor patients, manage medications, coordinate care  
  ![Nurse Icon](https://www.vecteezy.com/free-vector/medical-staff-icon/nurse-1234569)
- **Administrators**: Manage users, departments, system operations, analytics  
  ![Admin Icon](https://www.shutterstock.com/image-vector/admin-icon-healthcare-1234570)

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

![Hospital ER Diagram](https://creately.com/static/assets/examples/er-diagram-hospital-management-system.png)

## 📱 Screenshots
Without manually taking screenshots of your HTML pages (e.g., `admin-dashboard.html`, `doctor-dashboard.html`, etc.), you can embed representative placeholder images in the README using services like [via.placeholder.com](https://via.placeholder.com/) or [LoremFlickr](https://loremflickr.com/). These generate dynamic, themed images on-the-fly based on text or tags—no uploads or tools needed. They render directly in GitHub Markdown.

For a more polished look, replace placeholders with free stock UI mockups from sites like Freepik or Dribbble (download PNGs and upload to your repo for relative links like `![Alt](images/admin-dashboard.png)`). Here's an example integration using placeholders:

### Index.html (Homepage)
![Index Homepage UI](https://via.placeholder.com/1200x600/007BFF/FFFFFF?text=Hospital+Index+Homepage)

### Login.html
![Login Page UI](https://via.placeholder.com/1200x700/10B981/FFFFFF?text=Hospital+Login+Page)

### Register.html (Register Page)
![Register Page UI](https://via.placeholder.com/1200x700/3B82F6/FFFFFF?text=Hospital+Register+Form)

### Admin-Dashboard.html
![Admin Dashboard UI](https://via.placeholder.com/1200x700/8B5CF6/FFFFFF?text=Admin+Dashboard)

### Doctor-Dashboard.html
![Doctor Dashboard UI](https://via.placeholder.com/1200x700/F59E0B/FFFFFF?text=Doctor+Dashboard)

### Nurse-Dashboard.html
![Nurse Dashboard UI](https://via.placeholder.com/1200x700/EF4444/FFFFFF?text=Nurse+Dashboard)

### Patient-Dashboard.html
![Patient Dashboard UI](https://via.placeholder.com/1200x700/06B6D4/FFFFFF?text=Patient+Dashboard)

**Pro Tip**: Customize placeholders with colors (e.g., hex codes for healthcare blue tones) and text. For themed images, use `https://loremflickr.com/1200/700/dashboard,medical,hospital` (randomizes relevant photos). To make permanent, download and commit to `/images/` folder in your repo.


## 👥 Demo Accounts

| Role | Email | Password | Description |
|------|-------|----------|-------------|
| **Patient** | patient@hms.com | password | Book appointments, view records |
| **Doctor** | doctor@hms.com | password | Manage patients, write prescriptions |
| **Nurse** | nurse@hms.com | password | Monitor patients, dispense medications |
| **Admin** | admin@hms.com | password | System administration, user management |

## 📁 Project Structure

```
/HMS/
├── backend/               # Spring Boot REST API
│   ├── src/main/java/     # Java source code
│   ├── src/main/resources/ # Configuration & static assets
│   └── pom.xml            # Maven configuration
├── frontend/              # Modern React/Vite UI
│   ├── public/            # Static assets
│   ├── src/               # React components & logic
│   └── package.json       # Node dependencies
└── README.md              # Project documentation
```

## 🚀 Quick Start

### 1. Backend Setup (Spring Boot)
```bash
cd backend
mvn clean spring-boot:run
```

### 2. Frontend Setup (React/Vite)
```bash
cd frontend
npm install
npm run dev
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

![Responsive Hospital Dashboard UI](https://cdn.dribbble.com/userupload/12578692/file/original-abc123-dashboard-hospital-status-ui.jpg)

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
