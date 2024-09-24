package com.example.locomotive.dto.response;

import java.util.UUID;

import com.example.locomotive.models.LocomotiveStatus;

import lombok.Data;

@Data
public class LocomotiveStatusResponse {

    private UUID id;
    private String locomotiveName;
    private Integer active;
    private Integer nonActive;
    private Integer maintenance;
    private Integer totalLocomotive;

    public LocomotiveStatusResponse(LocomotiveStatus locomotiveStatus) {
        this.id = locomotiveStatus.getId();
        this.locomotiveName = locomotiveStatus.getLocomotiveName();
        this.active = locomotiveStatus.getActive();
        this.nonActive = locomotiveStatus.getNonActive();
        this.maintenance = locomotiveStatus.getMaintenance();
        this.totalLocomotive = locomotiveStatus.getTotalLocomotive();
    }
}
