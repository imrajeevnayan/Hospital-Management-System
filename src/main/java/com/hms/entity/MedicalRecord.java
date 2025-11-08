package com.hms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "medical_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    
    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;
    
    @Column(name = "record_type", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private RecordType recordType;
    
    @Column(name = "chief_complaint", length = 500, nullable = false)
    private String chiefComplaint;
    
    @Column(name = "present_illness", length = 2000)
    private String presentIllness;
    
    @Column(name = "medical_history", length = 2000)
    private String medicalHistory;
    
    @Column(name = "family_history", length = 2000)
    private String familyHistory;
    
    @Column(name = "social_history", length = 1000)
    private String socialHistory;
    
    @Column(name = "allergies", length = 1000)
    private String allergies;
    
    @Column(name = "medications", length = 2000)
    private String medications;
    
    @Column(name = "physical_examination", length = 2000)
    private String physicalExamination;
    
    @Column(name = "vital_signs", length = 1000)
    private String vitalSigns;
    
    @Column(name = "weight")
    private Double weight;
    
    @Column(name = "height")
    private Double height;
    
    @Column(name = "blood_pressure_systolic")
    private Integer bloodPressureSystolic;
    
    @Column(name = "blood_pressure_diastolic")
    private Integer bloodPressureDiastolic;
    
    @Column(name = "heart_rate")
    private Integer heartRate;
    
    @Column(name = "temperature")
    private Double temperature;
    
    @Column(name = "respiratory_rate")
    private Integer respiratoryRate;
    
    @Column(name = "oxygen_saturation")
    private Double oxygenSaturation;
    
    @Column(name = "laboratory_tests", length = 2000)
    private String laboratoryTests;
    
    @Column(name = "imaging_studies", length = 2000)
    private String imagingStudies;
    
    @Column(name = "procedures", length = 2000)
    private String procedures;
    
    @Column(name = "assessment", length = 2000, nullable = false)
    private String assessment;
    
    @Column(name = "plan", length = 2000, nullable = false)
    private String plan;
    
    @Column(name = "follow_up_instructions", length = 1000)
    private String followUpInstructions;
    
    @Column(name = "next_appointment_date")
    private LocalDate nextAppointmentDate;
    
    @Column(name = "progress_notes", length = 2000)
    private String progressNotes;
    
    @Column(name = "is_confidential", nullable = false)
    private Boolean isConfidential = false;
    
    public double getBMI() {
        if (height != null && weight != null && height > 0) {
            return weight / ((height / 100) * (height / 100));
        }
        return 0.0;
    }
    
    public String getBloodPressureReading() {
        if (bloodPressureSystolic != null && bloodPressureDiastolic != null) {
            return bloodPressureSystolic + "/" + bloodPressureDiastolic;
        }
        return "Not recorded";
    }
    
    public boolean isUrgent() {
        return isConfidential != null && isConfidential;
    }
    
    public enum RecordType {
        CONSULTATION, FOLLOW_UP, EMERGENCY, SURGERY, DIAGNOSTIC, PREVENTIVE, TELEMEDICINE
    }
}