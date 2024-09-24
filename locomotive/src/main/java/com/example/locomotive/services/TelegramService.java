package com.example.locomotive.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.locomotive.dto.response.LocomotiveSummaryResponse;
import com.example.locomotive.repositories.LocomotiveSummaryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__({ @Autowired }))
public class TelegramService {
    private final LocomotiveSummaryRepo summaryRepo;

    public LocomotiveSummaryResponse getLatestSummary() {
        return summaryRepo.findLatestSummary();
    }


}
