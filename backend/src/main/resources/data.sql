-- Persistent Seed Data for PostgreSQL
-- Password hash: BCrypt for 'password' -> $2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi

-- 1. Insert Hardcoded Staff (ADMIN, DOCTOR, NURSE)
-- Using ON CONFLICT (email) DO NOTHING to ensure idempotence across restarts
INSERT INTO users (first_name, last_name, email, password, phone_number, role, is_verified, is_active, is_deleted, login_attempts, created_at, updated_at) 
VALUES
('Admin', 'System', 'admin@hms.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '+1-555-0100', 'ADMIN', TRUE, TRUE, FALSE, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Dr. John', 'Doe', 'doctor@hms.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '+1-555-0200', 'DOCTOR', TRUE, TRUE, FALSE, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Nurse Jane', 'Smith', 'nurse@hms.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '+1-555-0300', 'NURSE', TRUE, TRUE, FALSE, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (email) DO NOTHING;

-- 2. Insert Sample Patient (Optional, patients will register themselves)
INSERT INTO users (first_name, last_name, email, password, phone_number, role, is_verified, is_active, is_deleted, login_attempts, created_at, updated_at) 
VALUES
('Patient', 'Test', 'patient@hms.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '+1-555-0400', 'PATIENT', TRUE, TRUE, FALSE, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (email) DO NOTHING;

-- 3. Insert Departments
-- Using ON CONFLICT (id) DO NOTHING isn't possible here without IDs, so we use name if unique or just rely on manual management
-- For simplicity in this env, we use simple inserts or check existence
INSERT INTO departments (id, department_name, description, location, total_beds, available_beds, doctors_count, nurses_count, is_emergency_department, is_active_department, is_active, is_deleted)
SELECT 1, 'General Medicine', 'General medical care and consultations', 'Building A, Floor 2', 20, 18, 5, 10, FALSE, TRUE, TRUE, FALSE
WHERE NOT EXISTS (SELECT 1 FROM departments WHERE id = 1);

INSERT INTO departments (id, department_name, description, location, total_beds, available_beds, doctors_count, nurses_count, is_emergency_department, is_active_department, is_active, is_deleted)
SELECT 2, 'Surgery', 'Surgical procedures and recovery', 'Building B, Floor 3', 15, 12, 8, 12, FALSE, TRUE, TRUE, FALSE
WHERE NOT EXISTS (SELECT 1 FROM departments WHERE id = 2);

-- 4. Sample Patient Profile (Links to patient@hms.com)
INSERT INTO patients (id, user_id, patient_id, blood_group, marital_status, primary_doctor_id, department_id, follow_up_required, is_active, is_deleted)
SELECT 1, (SELECT id FROM users WHERE email = 'patient@hms.com'), 'PAT001', 'O+', 'SINGLE', (SELECT id FROM users WHERE email = 'doctor@hms.com'), 1, FALSE, TRUE, FALSE
WHERE NOT EXISTS (SELECT 1 FROM patients WHERE id = 1);

-- 5. Sample Appointment
INSERT INTO appointments (id, patient_id, doctor_id, appointment_date, appointment_time, status, reason, is_emergency, is_active, is_deleted)
SELECT 1, 1, (SELECT id FROM users WHERE email = 'doctor@hms.com'), '2025-11-10', '10:00:00', 'SCHEDULED', 'Routine checkup', FALSE, TRUE, FALSE
WHERE NOT EXISTS (SELECT 1 FROM appointments WHERE id = 1);