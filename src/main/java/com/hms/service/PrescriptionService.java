package com.hms.service;

import com.hms.entity.Prescription;
import com.hms.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class PrescriptionService {
    
    private final PrescriptionRepository prescriptionRepository;
    
    @Transactional(readOnly = true)
    public Optional<Prescription> findById(Long id) {
        return prescriptionRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Prescription> findAll() {
        return prescriptionRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Prescription> getRecentPrescriptionsForPatient(Long patientId, int limit) {
        return prescriptionRepository.findPrescriptionsByPatientId(patientId, Pageable.ofSize(limit))
                .getContent();
    }
    
    @Transactional(readOnly = true)
    public Page<Prescription> getPrescriptionsForPatient(Long patientId, Pageable pageable) {
        return prescriptionRepository.findPrescriptionsByPatientId(patientId, pageable);
    }
    
    @Transactional
    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }
    
    @Transactional
    public Prescription updatePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }
    
    @Transactional
    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }
    
    @Transactional
    public Prescription dispensePrescription(Long prescriptionId, Integer dispensedQuantity, String dispensedBy) {
        return prescriptionRepository.findById(prescriptionId)
                .map(prescription -> {
                    prescription.setStatus(Prescription.PrescriptionStatus.DISPENSED);
                    prescription.setDispensedQuantity(dispensedQuantity);
                    prescription.setDispensedDate(LocalDate.now());
                    prescription.setDispensedBy(dispensedBy);
                    return prescriptionRepository.save(prescription);
                })
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
    }
    
    @Transactional(readOnly = true)
    public List<Prescription> getRefillablePrescriptions(Long patientId) {
        return prescriptionRepository.findRefillablePrescriptions(patientId);
    }
    
    @Transactional(readOnly = true)
    public List<Prescription> getEmergencyPrescriptions() {
        return prescriptionRepository.findEmergencyPrescriptions();
    }
    
    @Transactional(readOnly = true)
    public Page<Prescription> searchPrescriptions(Long patientId, String search, Pageable pageable) {
        return prescriptionRepository.searchPrescriptions(patientId, search, pageable);
    }
}