package com.hms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "departments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department extends BaseEntity {
    
    @Column(name = "department_name", length = 100, nullable = false, unique = true)
    private String departmentName;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "location", length = 100)
    private String location;
    
    @Column(name = "floor_number")
    private Integer floorNumber;
    
    @Column(name = "building", length = 100)
    private String building;
    
    @Column(name = "contact_number", length = 20)
    private String contactNumber;
    
    @Column(name = "email", length = 255)
    private String email;
    
    @Column(name = "head_of_department_id")
    private Long headOfDepartmentId;
    
    @Column(name = "is_emergency_department", nullable = false)
    private Boolean isEmergencyDepartment = false;
    
    @Column(name = "is_surgical", nullable = false)
    private Boolean isSurgical = false;
    
    @Column(name = "operating_hours_start", length = 8)
    private String operatingHoursStart;
    
    @Column(name = "operating_hours_end", length = 8)
    private String operatingHoursEnd;
    
    @Column(name = "working_days", length = 50)
    private String workingDays;
    
    @Column(name = "total_beds")
    private Integer totalBeds;
    
    @Column(name = "available_beds")
    private Integer availableBeds;
    
    @Column(name = "icu_beds")
    private Integer icuBeds;
    
    @Column(name = "emergency_beds")
    private Integer emergencyBeds;
    
    @Column(name = "specialties", length = 1000)
    private String specialties;
    
    @Column(name = "services", length = 2000)
    private String services;
    
    @Column(name = "equipment", length = 2000)
    private String equipment;
    
    @Column(name = "doctors_count", nullable = false)
    private Integer doctorsCount = 0;
    
    @Column(name = "nurses_count", nullable = false)
    private Integer nursesCount = 0;
    
    @Column(name = "is_active_department", nullable = false)
    private Boolean isActiveDepartment = true;
    
    public boolean hasAvailableBeds() {
        return availableBeds != null && availableBeds > 0;
    }
    
    public int getOccupiedBeds() {
        if (totalBeds == null || availableBeds == null) return 0;
        return totalBeds - availableBeds;
    }
    
    public double getBedOccupancyRate() {
        if (totalBeds == null || totalBeds == 0) return 0.0;
        return (double) getOccupiedBeds() / totalBeds * 100;
    }
    
    public boolean isOpen() {
        // This would need to be enhanced with actual time checking
        return isActiveDepartment;
    }
}