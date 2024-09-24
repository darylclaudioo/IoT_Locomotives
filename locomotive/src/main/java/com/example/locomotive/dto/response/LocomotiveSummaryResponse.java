package com.example.locomotive.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.locomotive.models.LocomotiveSummary;

import lombok.Data;

@Data
public class LocomotiveSummaryResponse {
    private UUID id;
    private Integer active;
    private Integer nonActive;
    private Integer maintenance;
    private Integer totalLocomotive;
    private LocalDateTime updatedAt;

    public LocomotiveSummaryResponse(LocomotiveSummary locomotiveSummary) {
        this.id = locomotiveSummary.getId();
        this.active = locomotiveSummary.getActive();
        this.nonActive = locomotiveSummary.getNonActive();
        this.maintenance = locomotiveSummary.getMaintenance();
        this.totalLocomotive = locomotiveSummary.getTotalLocomotive();
        this.updatedAt = locomotiveSummary.getUpdatedAt();
    }
}
