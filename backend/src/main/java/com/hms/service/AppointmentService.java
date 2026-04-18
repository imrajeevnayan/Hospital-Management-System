package com.hms.service;

import com.hms.entity.Appointment;
import com.hms.entity.Patient;
import com.hms.entity.User;
import com.hms.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService {
    
    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    
    @Transactional(readOnly = true)
    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Appointment> getRecentAppointmentsForPatient(Long patientId, int limit) {
        return appointmentRepository.findRecentAppointmentsByPatientId(patientId).stream()
                .limit(limit)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public Page<Appointment> getAppointmentsForPatient(Long patientId, Pageable pageable) {
        return appointmentRepository.findPatientAppointmentsByPatientId(patientId, pageable);
    }
    
    @Transactional(readOnly = true)
    public Page<Appointment> getAppointmentsForDoctor(Long doctorId, Pageable pageable) {
        return appointmentRepository.findDoctorAppointmentsByDoctorId(doctorId, pageable);
    }
    
    @Transactional(readOnly = true)
    public List<Appointment> getTodaysAppointmentsForDoctor(Long doctorId) {
        return appointmentRepository.findTodaysAppointments(doctorId, LocalDate.now());
    }
    
    @Transactional
    public Appointment bookAppointment(Patient patient, User doctor, LocalDate appointmentDate, 
                                     String appointmentTimeStr, String reason, String notes) {
        
        LocalTime appointmentTime = LocalTime.parse(appointmentTimeStr);
        LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);
        
        // Check for conflicts
        LocalDateTime endDateTime = appointmentDateTime.plusMinutes(30); // Assuming 30 min appointments
        List<Appointment> conflicts = appointmentRepository.findConflictingAppointments(
                doctor.getId(), appointmentDate, appointmentDateTime, endDateTime);
        
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Doctor is not available at the selected time");
        }
        
        // Check doctor availability
        if (!isDoctorAvailable(doctor, appointmentDate, appointmentTime)) {
            throw new RuntimeException("Doctor is not available on the selected date");
        }
        
        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentDate(appointmentDate)
                .appointmentTime(appointmentTime)
                .durationMinutes(30)
                .status(Appointment.AppointmentStatus.SCHEDULED)
                .reason(reason)
                .notes(notes)
                .isEmergency(false)
                .build();
        
        return appointmentRepository.save(appointment);
    }
    
    @Transactional
    public Appointment confirmAppointment(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(appointment -> {
                    appointment.setStatus(Appointment.AppointmentStatus.CONFIRMED);
                    return appointmentRepository.save(appointment);
                })
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }
    
    @Transactional
    public Appointment cancelAppointment(Long appointmentId, String cancellationReason) {
        return appointmentRepository.findById(appointmentId)
                .map(appointment -> {
                    if (!appointment.canBeCancelled()) {
                        throw new RuntimeException("Appointment cannot be cancelled");
                    }
                    appointment.setStatus(Appointment.AppointmentStatus.CANCELLED);
                    appointment.setCancellationReason(cancellationReason);
                    appointment.setCancellationDate(LocalDateTime.now());
                    return appointmentRepository.save(appointment);
                })
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }
    
    @Transactional
    public Appointment completeAppointment(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(appointment -> {
                    appointment.setStatus(Appointment.AppointmentStatus.COMPLETED);
                    appointment.setActualVisitTime(LocalDateTime.now());
                    return appointmentRepository.save(appointment);
                })
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }
    
    @Transactional
    public Appointment startAppointment(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(appointment -> {
                    appointment.setStatus(Appointment.AppointmentStatus.IN_PROGRESS);
                    appointment.setActualVisitTime(LocalDateTime.now());
                    return appointmentRepository.save(appointment);
                })
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }
    
    @Transactional(readOnly = true)
    public boolean isDoctorAvailable(User doctor, LocalDate date, LocalTime time) {
        // Check for existing appointments at the same time
        List<Appointment> existingAppointments = appointmentRepository
                .findAppointmentsByDoctorAndDateRange(doctor.getId(), date, date);
        
        LocalDateTime requestedTime = LocalDateTime.of(date, time);
        LocalDateTime endTime = requestedTime.plusMinutes(30);
        
        return existingAppointments.stream()
                .noneMatch(apt -> isTimeConflict(requestedTime, endTime, 
                        LocalDateTime.of(apt.getAppointmentDate(), apt.getAppointmentTime()),
                        LocalDateTime.of(apt.getAppointmentDate(), apt.getAppointmentTime()).plusMinutes(apt.getDurationMinutes())));
    }
    
    private boolean isTimeConflict(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }
    
    @Transactional(readOnly = true)
    public List<Appointment> getUpcomingAppointments() {
        return appointmentRepository.findUpcomingAppointments(LocalDate.now());
    }
    
    @Transactional(readOnly = true)
    public List<Appointment> getEmergencyAppointments() {
        return appointmentRepository.findEmergencyAppointments();
    }
    
    @Transactional
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}