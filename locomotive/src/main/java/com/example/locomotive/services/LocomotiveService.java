package com.example.locomotive.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.locomotive.dto.request.LocomotiveSummaryRequest;
import com.example.locomotive.dto.response.LocomotiveSummaryResponse;
import com.example.locomotive.models.LocomotiveStatus;
import com.example.locomotive.repositories.LocomotiveStatusRepo;
import com.example.locomotive.repositories.LocomotiveSummaryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__({ @Autowired }))
public class LocomotiveService {
    private final LocomotiveSummaryRepo summaryRepo;
    private final LocomotiveStatusRepo statusRepo;

    private static final Logger logger = LoggerFactory.getLogger(LocomotiveService.class);

    public List<LocomotiveSummaryResponse> findAllSummary(LocomotiveSummaryRequest request) {
        Page<LocomotiveSummaryResponse> summaries = summaryRepo.findAllSummary(request.getUpdatedAt(), request.getPageable());

        logger.info("All Summary: {}", summaries);

        return summaries.getContent();
    }

    public LocomotiveSummaryResponse findLatestSummary() {
        LocomotiveSummaryResponse summary = summaryRepo.findLatestSummary();
        logger.info("Latest Summary: {}", summary);
        return summary;
    }

    public List<LocomotiveStatus> findAllStatus() {
        List<LocomotiveStatus> statuses = statusRepo.findAll();
        logger.info("All Status: {}", statuses);
        return statuses;
    }


}
