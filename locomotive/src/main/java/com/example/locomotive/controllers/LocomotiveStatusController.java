package com.example.locomotive.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.locomotive.models.LocomotiveStatus;
import com.example.locomotive.services.LocomotiveService;

import lombok.RequiredArgsConstructor;


@RestController
@CrossOrigin
@RequestMapping("/status")
@RequiredArgsConstructor
public class LocomotiveStatusController {
    private final LocomotiveService service;

    private static final Logger logger = LoggerFactory.getLogger(LocomotiveSummaryController.class);

    @GetMapping("/all")
    public ResponseEntity<List<LocomotiveStatus>> findAllStatus() {
        List<LocomotiveStatus> statuses = service.findAllStatus();
        logger.info("All Locomotive Status: {}", statuses);
        return ResponseEntity.ok(statuses);
    }
    
}
