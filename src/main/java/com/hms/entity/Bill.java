package com.hms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "bills")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    
    @Column(name = "bill_number", unique = true, length = 50, nullable = false)
    private String billNumber;
    
    @Column(name = "bill_date", nullable = false)
    private LocalDate billDate;
    
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    
    @Column(name = "bill_type", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private BillType billType;
    
    @Column(name = "item_name", length = 200, nullable = false)
    private String itemName;
    
    @Column(name = "item_description", length = 1000)
    private String itemDescription;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;
    
    @Column(name = "total_amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalAmount;
    
    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;
    
    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount;
    
    @Column(name = "tax_percentage", precision = 5, scale = 2)
    private BigDecimal taxPercentage;
    
    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount;
    
    @Column(name = "final_amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal finalAmount;
    
    @Column(name = "paid_amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal paidAmount = BigDecimal.ZERO;
    
    @Column(name = "remaining_amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal remainingAmount;
    
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private BillStatus status;
    
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    
    @Column(name = "payment_method", length = 30)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    
    @Column(name = "payment_reference", length = 100)
    private String paymentReference;
    
    @Column(name = "payment_gateway", length = 50)
    private String paymentGateway;
    
    @Column(name = "invoice_number", length = 50)
    private String invoiceNumber;
    
    @Column(name = "receipt_number", length = 50)
    private String receiptNumber;
    
    @Column(name = "payment_notes", length = 1000)
    private String paymentNotes;
    
    @Column(name = "insurance_claim_number", length = 50)
    private String insuranceClaimNumber;
    
    @Column(name = "insurance_coverage_percentage", precision = 5, scale = 2)
    private BigDecimal insuranceCoveragePercentage;
    
    @Column(name = "insurance_coverage_amount", precision = 10, scale = 2)
    private BigDecimal insuranceCoverageAmount;
    
    @Column(name = "patient_payable", precision = 10, scale = 2, nullable = false)
    private BigDecimal patientPayable;
    
    @Column(name = "created_by", length = 100)
    private String createdBy;
    
    @Column(name = "updated_by", length = 100)
    private String updatedBy;
    
    @Column(name = "is_insurance_covered", nullable = false)
    private Boolean isInsuranceCovered = false;
    
    @Column(name = "waived_amount", precision = 10, scale = 2)
    private BigDecimal waivedAmount = BigDecimal.ZERO;
    
    @Column(name = "waiver_reason", length = 500)
    private String waiverReason;
    
    public boolean isPaid() {
        return paymentStatus == PaymentStatus.PAID || 
               (finalAmount != null && finalAmount.compareTo(paidAmount) <= 0);
    }
    
    public boolean isOverdue() {
        return dueDate != null && LocalDate.now().isAfter(dueDate) && !isPaid();
    }
    
    public boolean isPartiallyPaid() {
        return paymentStatus == PaymentStatus.PARTIALLY_PAID;
    }
    
    public double getPaymentProgress() {
        if (finalAmount == null || finalAmount.compareTo(BigDecimal.ZERO) == 0) return 0.0;
        return (double) paidAmount.divide(finalAmount, 2, BigDecimal.ROUND_HALF_UP).doubleValue() * 100;
    }
    
    public BigDecimal getRemainingAmount() {
        return finalAmount.subtract(paidAmount);
    }
    
    public boolean canApplyDiscount() {
        return status == BillStatus.DRAFT || status == BillStatus.PENDING;
    }
    
    public void addPayment(BigDecimal amount) {
        this.paidAmount = this.paidAmount.add(amount);
        updatePaymentStatus();
    }
    
    private void updatePaymentStatus() {
        if (paidAmount.compareTo(BigDecimal.ZERO) == 0) {
            this.paymentStatus = PaymentStatus.PENDING;
        } else if (paidAmount.compareTo(finalAmount) >= 0) {
            this.paymentStatus = PaymentStatus.PAID;
            this.status = BillStatus.PAID;
        } else {
            this.paymentStatus = PaymentStatus.PARTIALLY_PAID;
        }
    }
    
    public enum BillType {
        CONSULTATION, MEDICATION, PROCEDURE, DIAGNOSTIC, SURGERY, ROOM_CHARGE, NURSING_CARE, 
        LABORATORY, RADIOLOGY, PHYSIOTHERAPY, EMERGENCY, AMBULANCE, FOOD, MISCELLANEOUS
    }
    
    public enum BillStatus {
        DRAFT, PENDING, APPROVED, PAID, PARTIALLY_PAID, CANCELLED, REFUNDED
    }
    
    public enum PaymentStatus {
        PENDING, PARTIALLY_PAID, PAID, OVERDUE, CANCELLED
    }
    
    public enum PaymentMethod {
        CASH, CREDIT_CARD, DEBIT_CARD, BANK_TRANSFER, CHECK, INSURANCE, ONLINE, INSTALLMENT
    }
}