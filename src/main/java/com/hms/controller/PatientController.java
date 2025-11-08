package com.hms.controller;

import com.hms.entity.*;
import com.hms.repository.PatientRepository;
import com.hms.security.CustomUserDetails;
import com.hms.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/hms/patient")
@RequiredArgsConstructor
public class PatientController {
    
    private final UserService userService;
    private final PatientRepository patientRepository;
    private final AppointmentService appointmentService;
    private final MedicalRecordService medicalRecordService;
    private final PrescriptionService prescriptionService;
    private final BillService billService;
    
    @GetMapping("/dashboard")
    public String patientDashboard(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User patient = userService.findById(userDetails.getId()).orElse(null);
        
        if (patient == null) {
            return "redirect:/login";
        }
        
        // Get recent appointments
        List<Appointment> recentAppointments = appointmentService.getRecentAppointmentsForPatient(patient.getId(), 5);
        
        // Get recent medical records
        List<MedicalRecord> recentRecords = medicalRecordService.getRecentMedicalRecordsForPatient(patient.getId(), 5);
        
        // Get recent prescriptions
        List<Prescription> recentPrescriptions = prescriptionService.getRecentPrescriptionsForPatient(patient.getId(), 5);
        
        // Get outstanding bills
        Optional<BigDecimal> outstandingAmount = billService.getOutstandingAmountForPatient(patient.getId());
        
        model.addAttribute("patient", patient);
        model.addAttribute("recentAppointments", recentAppointments);
        model.addAttribute("recentRecords", recentRecords);
        model.addAttribute("recentPrescriptions", recentPrescriptions);
        model.addAttribute("outstandingAmount", outstandingAmount.orElse(BigDecimal.ZERO));
        model.addAttribute("currentDate", LocalDate.now());
        
        return "patient-dashboard";
    }
    
    @GetMapping("/appointments")
    public String patientAppointments(Authentication authentication, Model model,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User patient = userService.findById(userDetails.getId()).orElse(null);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("appointmentDate").descending().and(Sort.by("appointmentTime").descending()));
        Page<Appointment> appointments = appointmentService.getAppointmentsForPatient(patient.getId(), pageable);
        
        model.addAttribute("appointments", appointments);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", appointments.getTotalPages());
        model.addAttribute("totalElements", appointments.getTotalElements());
        
        return "patient-appointments";
    }
    
    @GetMapping("/medical-records")
    public String patientMedicalRecords(Authentication authentication, Model model,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User patient = userService.findById(userDetails.getId()).orElse(null);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("recordDate").descending());
        Page<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsForPatient(patient.getId(), pageable);
        
        model.addAttribute("medicalRecords", medicalRecords);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", medicalRecords.getTotalPages());
        model.addAttribute("totalElements", medicalRecords.getTotalElements());
        
        return "patient-medical-records";
    }
    
    @GetMapping("/prescriptions")
    public String patientPrescriptions(Authentication authentication, Model model,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User patient = userService.findById(userDetails.getId()).orElse(null);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("prescriptionDate").descending());
        Page<Prescription> prescriptions = prescriptionService.getPrescriptionsForPatient(patient.getId(), pageable);
        
        model.addAttribute("prescriptions", prescriptions);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prescriptions.getTotalPages());
        model.addAttribute("totalElements", prescriptions.getTotalElements());
        
        return "patient-prescriptions";
    }
    
    @GetMapping("/bills")
    public String patientBills(Authentication authentication, Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User patient = userService.findById(userDetails.getId()).orElse(null);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("billDate").descending());
        Page<Bill> bills = billService.getBillsForPatient(patient.getId(), pageable);
        
        model.addAttribute("bills", bills);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bills.getTotalPages());
        model.addAttribute("totalElements", bills.getTotalElements());
        
        return "patient-bills";
    }
    
    @GetMapping("/profile")
    public String patientProfile(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User patient = userService.findById(userDetails.getId()).orElse(null);
        
        model.addAttribute("patient", patient);
        return "patient-profile";
    }
    
    @PostMapping("/book-appointment")
    public String bookAppointment(Authentication authentication,
                                 @RequestParam Long doctorId,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate,
                                 @RequestParam String appointmentTime,
                                 @RequestParam String reason,
                                 @RequestParam(required = false) String notes,
                                 Model model) {
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findById(userDetails.getId()).orElse(null);
        Patient patient = patientRepository.findByUser(user);
        User doctor = userService.findById(doctorId).orElse(null);
        
        try {
            appointmentService.bookAppointment(patient, doctor, appointmentDate, appointmentTime, reason, notes);
            model.addAttribute("success", "Appointment booked successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to book appointment: " + e.getMessage());
        }
        
        return "redirect:/hms/patient/appointments";
    }
}