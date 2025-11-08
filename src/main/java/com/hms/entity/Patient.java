package com.hms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "patients")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends BaseEntity {
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_doctor_id")
    private User primaryDoctor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    
    @Column(name = "patient_id", unique = true, length = 20, nullable = false)
    private String patientId;
    
    @Column(name = "blood_group", length = 5)
    private String bloodGroup;
    
    @Column(name = "height_cm")
    private Double heightCm;
    
    @Column(name = "weight_kg")
    private Double weightKg;
    
    @Column(name = "emergency_contact_name", length = 100)
    private String emergencyContactName;
    
    @Column(name = "emergency_contact_phone", length = 20)
    private String emergencyContactPhone;
    
    @Column(name = "emergency_contact_relationship", length = 50)
    private String emergencyContactRelationship;
    
    @Column(name = "insurance_provider", length = 200)
    private String insuranceProvider;
    
    @Column(name = "insurance_policy_number", length = 50)
    private String insurancePolicyNumber;
    
    @Column(name = "insurance_group_number", length = 50)
    private String insuranceGroupNumber;
    
    @Column(name = "insurance_photo", length = 500)
    private String insurancePhoto;
    
    @Column(name = "primary_language", length = 50)
    private String primaryLanguage;
    
    @Column(name = "occupational_history", length = 1000)
    private String occupationalHistory;
    
    @Column(name = "marital_status", length = 20)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    
    @Column(name = "educational_background", length = 500)
    private String educationalBackground;
    
    @Column(name = "religion", length = 50)
    private String religion;
    
    @Column(name = "nutrition_status", length = 50)
    @Enumerated(EnumType.STRING)
    private NutritionStatus nutritionStatus;
    
    @Column(name = "tobacco_use", nullable = false)
    private Boolean tobaccoUse = false;
    
    @Column(name = "alcohol_use", nullable = false)
    private Boolean alcoholUse = false;
    
    @Column(name = "exercise_habits", length = 1000)
    private String exerciseHabits;
    
    @Column(name = "travel_history", length = 1000)
    private String travelHistory;
    
    @Column(name = "current_medications", length = 2000)
    private String currentMedications;
    
    @Column(name = "known_allergies", length = 1000)
    private String knownAllergies;
    
    @Column(name = "previous_surgeries", length = 2000)
    private String previousSurgeries;
    
    @Column(name = "family_medical_history", length = 2000)
    private String familyMedicalHistory;
    
    @Column(name = "admission_date")
    private LocalDate admissionDate;
    
    @Column(name = "discharge_date")
    private LocalDate dischargeDate;
    
    @Column(name = "admission_type", length = 20)
    @Enumerated(EnumType.STRING)
    private AdmissionType admissionType;
    
    @Column(name = "discharge_status", length = 20)
    @Enumerated(EnumType.STRING)
    private DischargeStatus dischargeStatus;
    
    @Column(name = "current_room_number", length = 20)
    private String currentRoomNumber;
    
    @Column(name = "bed_number", length = 10)
    private String bedNumber;
    
    @Column(name = "admission_notes", length = 2000)
    private String admissionNotes;
    
    @Column(name = "discharge_instructions", length = 2000)
    private String dischargeInstructions;
    
    @Column(name = "follow_up_required", nullable = false)
    private Boolean followUpRequired = false;
    
    @Column(name = "follow_up_date")
    private LocalDate followUpDate;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalRecord> medicalRecords;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prescription> prescriptions;
    
    public double getBMI() {
        if (heightCm != null && weightKg != null && heightCm > 0) {
            return weightKg / ((heightCm / 100) * (heightCm / 100));
        }
        return 0.0;
    }
    
    public boolean isAdmitted() {
        return admissionDate != null && dischargeDate == null;
    }
    
    public int getAge() {
        if (user.getDateOfBirth() != null) {
            return LocalDate.now().getYear() - user.getDateOfBirth().getYear();
        }
        return 0;
    }
    
    public boolean requiresFollowUp() {
        return followUpRequired && followUpDate != null && followUpDate.isAfter(LocalDate.now());
    }
    
    public enum MaritalStatus {
        SINGLE, MARRIED, DIVORCED, WIDOWED, SEPARATED
    }
    
    public enum NutritionStatus {
        UNDERWEIGHT, NORMAL, OVERWEIGHT, OBESE, SEVERELY_OBESE
    }
    
    public enum AdmissionType {
        EMERGENCY, URGENT, ELECTIVE, ROUTINE
    }
    
    public enum DischargeStatus {
        RECOVERED, STABLE, IMPROVED, UNCHANGED, DETERIORATED, DIED, TRANSFERRED
    }
}