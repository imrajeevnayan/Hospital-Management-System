package com.hms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public ResponseEntity<Void> home() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/hms/index.html"))
                .build();
    }

    @GetMapping("/api/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("UP");
    }
}