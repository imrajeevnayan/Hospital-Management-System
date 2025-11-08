-- Sample data for Hospital Management System (H2-compatible)
-- Password hash: BCrypt for 'password' -> $2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi

-- Insert sample users (roles: ADMIN, DOCTOR, NURSE, PATIENT)
INSERT INTO users (first_name, last_name, email, password, phone_number, role, is_verified, is_active) VALUES
('Admin', 'User', 'admin@hms.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '+1-555-0100', 'ADMIN', TRUE, TRUE),
('Dr. John', 'Doe', 'doctor@hms.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '+1-555-0200', 'DOCTOR', TRUE, TRUE),
('Nurse Jane', 'Smith', 'nurse@hms.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '+1-555-0300', 'NURSE', TRUE, TRUE),
('Patient', 'Test', 'patient@hms.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '+1-555-0400', 'PATIENT', TRUE, TRUE);

-- Insert sample departments
INSERT INTO departments (department_name, description, location, total_beds, available_beds, is_active_department) VALUES
('General Medicine', 'General medical care and consultations', 'Building A, Floor 2', 20, 18, TRUE),
('Surgery', 'Surgical procedures and recovery', 'Building B, Floor 3', 15, 12, TRUE);

-- Insert sample patient (links to user_id=4, doctor_id=2, department_id=1)
INSERT INTO patients (user_id, primary_doctor_id, department_id, patient_id, blood_group, height_cm, weight_kg, is_active) VALUES
(4, 2, 1, 'PAT001', 'O+', 175.50, 70.25, TRUE);

-- Insert sample appointment (links to patient_id=1, doctor_id=2; nurse_id NULL for now)
INSERT INTO appointments (patient_id, doctor_id, nurse_id, appointment_date, appointment_time, duration_minutes, status, reason, is_emergency, is_active) VALUES
(1, 2, NULL, '2025-11-10', '10:00:00', 30, 'SCHEDULED', 'Routine checkup', FALSE, TRUE);