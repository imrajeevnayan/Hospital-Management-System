package com.hms.service;

import com.hms.entity.MedicalRecord;
import com.hms.repository.MedicalRecordRepository;
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
public class MedicalRecordService {
    
    private final MedicalRecordRepository medicalRecordRepository;
    
    @Transactional(readOnly = true)
    public Optional<MedicalRecord> findById(Long id) {
        return medicalRecordRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<MedicalRecord> findAll() {
        return medicalRecordRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<MedicalRecord> getRecentMedicalRecordsForPatient(Long patientId, int limit) {
        return medicalRecordRepository.findRecentMedicalRecordsByPatientId(patientId, LocalDate.now().minusMonths(6))
                .stream()
                .limit(limit)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public Page<MedicalRecord> getMedicalRecordsForPatient(Long patientId, Pageable pageable) {
        return medicalRecordRepository.findMedicalRecordsByPatientId(patientId, pageable);
    }
    
    @Transactional
    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }
    
    @Transactional
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }
    
    @Transactional
    public void deleteMedicalRecord(Long id) {
        medicalRecordRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<MedicalRecord> getMedicalRecordsByRecordType(Long patientId, MedicalRecord.RecordType recordType) {
        return medicalRecordRepository.findMedicalRecordsByPatientAndType(patientId, recordType);
    }
    
    @Transactional(readOnly = true)
    public Page<MedicalRecord> searchMedicalRecords(Long patientId, String search, Pageable pageable) {
        return medicalRecordRepository.searchMedicalRecords(patientId, search, pageable);
    }
}