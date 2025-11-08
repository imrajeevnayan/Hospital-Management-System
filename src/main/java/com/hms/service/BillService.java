package com.hms.service;

import com.hms.entity.Bill;
import com.hms.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class BillService {
    
    private final BillRepository billRepository;
    
    @Transactional(readOnly = true)
    public Optional<Bill> findById(Long id) {
        return billRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Bill> findAll() {
        return billRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Page<Bill> getBillsForPatient(Long patientId, Pageable pageable) {
        return billRepository.findBillsByPatientId(patientId, pageable);
    }
    
    @Transactional(readOnly = true)
    public Optional<BigDecimal> getOutstandingAmountForPatient(Long patientId) {
        BigDecimal outstandingAmount = billRepository.getTotalOutstandingAmount(patientId);
        return Optional.ofNullable(outstandingAmount);
    }
    
    @Transactional
    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }
    
    @Transactional
    public Bill updateBill(Bill bill) {
        return billRepository.save(bill);
    }
    
    @Transactional
    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }
    
    @Transactional
    public Bill addPayment(Long billId, BigDecimal amount, Bill.PaymentMethod paymentMethod, String paymentReference) {
        return billRepository.findById(billId)
                .map(bill -> {
                    bill.addPayment(amount);
                    if (bill.isPaid()) {
                        bill.setPaymentStatus(Bill.PaymentStatus.PAID);
                        bill.setStatus(Bill.BillStatus.PAID);
                    } else {
                        bill.setPaymentStatus(Bill.PaymentStatus.PARTIALLY_PAID);
                        bill.setStatus(Bill.BillStatus.PARTIALLY_PAID);
                    }
                    bill.setPaymentDate(LocalDate.now());
                    bill.setPaymentMethod(paymentMethod);
                    bill.setPaymentReference(paymentReference);
                    return billRepository.save(bill);
                })
                .orElseThrow(() -> new RuntimeException("Bill not found"));
    }
    
    @Transactional(readOnly = true)
    public List<Bill> getOverdueBills() {
        return billRepository.findOverdueBills(LocalDate.now());
    }
    
    @Transactional(readOnly = true)
    public BigDecimal getTotalBillsInPeriod(LocalDate startDate, LocalDate endDate) {
        return billRepository.getTotalBillsInPeriod(startDate, endDate);
    }
    
    @Transactional(readOnly = true)
    public BigDecimal getTotalCollectionsInPeriod(LocalDate startDate, LocalDate endDate) {
        return billRepository.getTotalCollectionsInPeriod(startDate, endDate);
    }
    
    @Transactional(readOnly = true)
    public Page<Bill> searchBills(Long patientId, String search, Pageable pageable) {
        return billRepository.searchBills(patientId, search, pageable);
    }
}