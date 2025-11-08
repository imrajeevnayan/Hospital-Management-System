package com.hms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prescription extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    
    @Column(name = "prescription_date", nullable = false)
    private LocalDate prescriptionDate;
    
    @Column(name = "medication_name", length = 200, nullable = false)
    private String medicationName;
    
    @Column(name = "generic_name", length = 200)
    private String genericName;
    
    @Column(name = "dosage", length = 100, nullable = false)
    private String dosage;
    
    @Column(name = "frequency", length = 100, nullable = false)
    private String frequency;
    
    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;
    
    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;
    
    @Column(name = "quantity_per_dose")
    private Integer quantityPerDose;
    
    @Column(name = "instructions", length = 1000, nullable = false)
    private String instructions;
    
    @Column(name = "side_effects", length = 1000)
    private String sideEffects;
    
    @Column(name = "contraindications", length = 1000)
    private String contraindications;
    
    @Column(name = "drug_interactions", length = 1000)
    private String drugInteractions;
    
    @Column(name = "refills_allowed", nullable = false)
    private Integer refillsAllowed = 0;
    
    @Column(name = "refills_used", nullable = false)
    private Integer refillsUsed = 0;
    
    @Column(name = "prescribed_quantity", nullable = false)
    private Integer prescribedQuantity;
    
    @Column(name = "dispensed_quantity", nullable = false)
    private Integer dispensedQuantity = 0;
    
    @Column(name = "dispensed_date")
    private LocalDate dispensedDate;
    
    @Column(name = "dispensed_by")
    private String dispensedBy;
    
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status;
    
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(name = "stop_reason", length = 500)
    private String stopReason;
    
    @Column(name = "stop_date")
    private LocalDate stopDate;
    
    @Column(name = "pharmacy_name", length = 200)
    private String pharmacyName;
    
    @Column(name = "pharmacy_address", length = 500)
    private String pharmacyAddress;
    
    @Column(name = "pharmacy_phone", length = 20)
    private String pharmacyPhone;
    
    @Column(name = "nurse_remark", length = 1000)
    private String nurseRemark;
    
    @Column(name = "patient_remark", length = 1000)
    private String patientRemark;
    
    @Column(name = "is_emergency", nullable = false)
    private Boolean isEmergency = false;
    
    @Column(name = "is_controlled_substance", nullable = false)
    private Boolean isControlledSubstance = false;
    
    @Column(name = "controlled_substance_class", length = 20)
    private String controlledSubstanceClass;
    
    public boolean isActive() {
        return status == PrescriptionStatus.PRESCRIBED || status == PrescriptionStatus.DISPENSED;
    }
    
    public boolean isExpired() {
        return endDate != null && LocalDate.now().isAfter(endDate);
    }
    
    public boolean canRefill() {
        return refillsAllowed > refillsUsed && !isExpired();
    }
    
    public boolean isFullCourseCompleted() {
        return dispensedQuantity >= prescribedQuantity;
    }
    
    public int getRemainingRefills() {
        return refillsAllowed - refillsUsed;
    }
    
    public double getProgressPercentage() {
        if (prescribedQuantity == 0) return 0.0;
        return (double) dispensedQuantity / prescribedQuantity * 100;
    }
    
    public enum PrescriptionStatus {
        PRESCRIBED, DISPENSED, DISCONTINUED, COMPLETED, EXPIRED, CANCELLED
    }
}