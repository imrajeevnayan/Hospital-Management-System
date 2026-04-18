package com.hms.controller;

import com.hms.entity.*;
import com.hms.repository.PatientRepository;
import com.hms.security.CustomUserDetails;
import com.hms.service.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/hms/api/patient")
@RequiredArgsConstructor
public class PatientController {
    
    private final UserService userService;
    private final PatientRepository patientRepository;
    private final AppointmentService appointmentService;
    private final MedicalRecordService medicalRecordService;
    private final PrescriptionService prescriptionService;
    private final BillService billService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> patientDashboard(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User patient = userService.findById(userDetails.getId()).orElse(null);
        
        if (patient == null) {
            return ResponseEntity.status(401).build();
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("patient", patient);
        data.put("recentAppointments", appointmentService.getRecentAppointmentsForPatient(patient.getId(), 5));
        data.put("recentRecords", medicalRecordService.getRecentMedicalRecordsForPatient(patient.getId(), 5));
        data.put("recentPrescriptions", prescriptionService.getRecentPrescriptionsForPatient(patient.getId(), 5));
        
        Optional<BigDecimal> outstandingAmount = billService.getOutstandingAmountForPatient(patient.getId());
        data.put("outstandingAmount", outstandingAmount.orElse(BigDecimal.ZERO));
        data.put("currentDate", LocalDate.now());
        
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/appointments")
    public ResponseEntity<Page<Appointment>> patientAppointments(Authentication authentication,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("appointmentDate").descending().and(Sort.by("appointmentTime").descending()));
        return ResponseEntity.ok(appointmentService.getAppointmentsForPatient(userDetails.getId(), pageable));
    }
    
    @GetMapping("/medical-records")
    public ResponseEntity<Page<MedicalRecord>> patientMedicalRecords(Authentication authentication,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("recordDate").descending());
        return ResponseEntity.ok(medicalRecordService.getMedicalRecordsForPatient(userDetails.getId(), pageable));
    }
    
    @GetMapping("/prescriptions")
    public ResponseEntity<Page<Prescription>> patientPrescriptions(Authentication authentication,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("prescriptionDate").descending());
        return ResponseEntity.ok(prescriptionService.getPrescriptionsForPatient(userDetails.getId(), pageable));
    }
    
    @GetMapping("/bills")
    public ResponseEntity<Page<Bill>> patientBills(Authentication authentication,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("billDate").descending());
        return ResponseEntity.ok(billService.getBillsForPatient(userDetails.getId(), pageable));
    }
    
    @GetMapping("/profile")
    public ResponseEntity<User> patientProfile(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userService.findById(userDetails.getId()).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/book-appointment")
    public ResponseEntity<Map<String, String>> bookAppointment(Authentication authentication,
                                                              @RequestBody AppointmentRequest request) {
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findById(userDetails.getId()).orElse(null);
        Patient patient = patientRepository.findByUser(user);
        User doctor = userService.findById(request.getDoctorId()).orElse(null);
        
        Map<String, String> response = new HashMap<>();
        try {
            appointmentService.bookAppointment(patient, doctor, request.getAppointmentDate(), request.getAppointmentTime(), request.getReason(), request.getNotes());
            response.put("message", "Appointment booked successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Failed to book appointment: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Data
    public static class AppointmentRequest {
        @NotNull
        private Long doctorId;
        @NotNull
        private LocalDate appointmentDate;
        @NotNull
        private String appointmentTime;
        @NotBlank
        private String reason;
        private String notes;
    }
}
}