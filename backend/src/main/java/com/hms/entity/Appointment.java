package com.hms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
@Table(name = "appointments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id")
    private User nurse;
    
    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;
    
    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;
    
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;
    
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    
    @Column(name = "reason", length = 500, nullable = false)
    private String reason;
    
    @Column(name = "notes", length = 1000)
    private String notes;
    
    @Column(name = "symptoms", length = 1000)
    private String symptoms;
    
    @Column(name = "diagnosis", length = 1000)
    private String diagnosis;
    
    @Column(name = "treatment_plan", length = 2000)
    private String treatmentPlan;
    
    @Column(name = "follow_up_date")
    private LocalDate followUpDate;
    
    @Column(name = "cancellation_reason", length = 500)
    private String cancellationReason;
    
    @Column(name = "cancellation_date")
    private LocalDateTime cancellationDate;
    
    @Column(name = "actual_visit_time")
    private LocalDateTime actualVisitTime;
    
    @Column(name = "visit_duration_minutes")
    private Integer visitDurationMinutes;
    
    @Column(name = "prescription_count")
    private Integer prescriptionCount;
    
    @Column(name = "is_emergency", nullable = false)
    private Boolean isEmergency = false;
    
    @Column(name = "consultation_fee")
    private BigDecimal consultationFee;
    
    @Column(name = "paid_amount")
    private BigDecimal paidAmount;
    
    public boolean isCompleted() {
        return status == AppointmentStatus.COMPLETED;
    }
    
    public boolean isUpcoming() {
        LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);
        return appointmentDateTime.isAfter(LocalDateTime.now()) && 
               (status == AppointmentStatus.SCHEDULED || status == AppointmentStatus.CONFIRMED);
    }
    
    public boolean canBeCancelled() {
        LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);
        return appointmentDateTime.isAfter(LocalDateTime.now()) && 
               (status == AppointmentStatus.SCHEDULED || status == AppointmentStatus.CONFIRMED);
    }
    
    public enum AppointmentStatus {
        SCHEDULED, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED, NO_SHOW, RESCHEDULED
    }
}