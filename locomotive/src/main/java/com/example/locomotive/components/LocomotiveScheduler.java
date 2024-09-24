package com.example.locomotive.components;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.locomotive.services.SchedulerService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LocomotiveScheduler {
    
    private SchedulerService service;

    @Async
    @Scheduled(fixedRate = 10000)
    public void sendDummyData() {
        service.sendDataToNode();
    }

}
