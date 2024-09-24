package com.example.locomotive.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.locomotive.dto.request.LocomotiveSummaryRequest;
import com.example.locomotive.dto.response.LocomotiveSummaryResponse;
import com.example.locomotive.services.LocomotiveService;

import lombok.RequiredArgsConstructor;


@RestController
@CrossOrigin
@RequestMapping("/summary")
@RequiredArgsConstructor
public class LocomotiveSummaryController {
    private final LocomotiveService service;

    private static final Logger logger = LoggerFactory.getLogger(LocomotiveSummaryController.class);

    @GetMapping("/all")
    public ResponseEntity<List<LocomotiveSummaryResponse>> findAllSummary(@ModelAttribute LocomotiveSummaryRequest request) {
        List<LocomotiveSummaryResponse> summaries = service.findAllSummary(request);
        logger.info("All Locomotive Summary: {}", summaries);
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/latest")
    public ResponseEntity<LocomotiveSummaryResponse> getLatestSummary() {
        LocomotiveSummaryResponse summary = service.findLatestSummary();
        logger.info("Latest Locomotive Summary: {}", summary);
        return ResponseEntity.ok(summary);
    }
    
}
