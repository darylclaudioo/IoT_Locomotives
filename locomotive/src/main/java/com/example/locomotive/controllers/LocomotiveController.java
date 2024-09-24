package com.example.locomotive.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.locomotive.models.Locomotive;
import com.example.locomotive.services.MongoService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/locomotives")
@RequiredArgsConstructor
public class LocomotiveController {
    private final MongoService service;

    @GetMapping("/all")
    public ResponseEntity<List<Locomotive>> findAllLocomotive() {
        return ResponseEntity.ok(service.fetchAllLocomotives());
    }
}
