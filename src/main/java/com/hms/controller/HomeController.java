package com.hms.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "login";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }
        
        // Redirect to appropriate dashboard based on user role
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(auth -> auth.replace("ROLE_", ""))
                .findFirst()
                .orElse("PATIENT");
        
        model.addAttribute("role", role);
        model.addAttribute("username", authentication.getName());
        
        return "redirect:/hms/" + role.toLowerCase() + "/dashboard";
    }
    
    @GetMapping("/hms")
    public String hmsRoot() {
        return "index";
    }
}