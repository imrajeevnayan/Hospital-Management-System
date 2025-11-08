package com.hms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {
    
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    
    @Column(unique = true, nullable = false, length = 255)
    private String email;
    
    @Column(nullable = false, length = 255)
    private String password;
    
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;
    
    @Column(name = "profile_picture", length = 500)
    private String profilePicture;
    
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @Column(length = 255)
    private String address;
    
    @Column(length = 100)
    private String city;
    
    @Column(length = 50)
    private String state;
    
    @Column(length = 20)
    private String zipCode;
    
    @Column(length = 100)
    private String country;
    
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;
    
    @Column(name = "verification_token", length = 255)
    private String verificationToken;
    
    @Column(name = "reset_password_token", length = 255)
    private String resetPasswordToken;
    
    @Column(name = "reset_password_expires")
    private LocalDateTime resetPasswordExpires;
    
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    @Column(name = "login_attempts", nullable = false)
    private Integer loginAttempts = 0;
    
    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;
    
    // For doctors
    @Column(name = "license_number", length = 50)
    private String licenseNumber;
    
    @Column(name = "specialization", length = 100)
    private String specialization;
    
    @Column(name = "experience_years")
    private Integer experienceYears;
    
    @Column(name = "consultation_fee")
    private BigDecimal consultationFee;
    
    // For nurses
    @Column(name = "nurse_license", length = 50)
    private String nurseLicense;
    
    @Column(name = "certification", length = 255)
    private String certification;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
    
    @Override
    public String getUsername() {
        return email;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return lockedUntil == null || lockedUntil.isAfter(LocalDateTime.now());
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return isActive && isVerified;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public enum UserRole {
        PATIENT, DOCTOR, NURSE, ADMIN
    }
    
    public enum Gender {
        MALE, FEMALE, OTHER
    }
}