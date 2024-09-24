package com.example.locomotive.dto.request;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class LocomotiveSummaryRequest {
    private Integer page;
    private Integer size;
    
    private LocalDateTime updatedAt;

    public Pageable getPageable() {
        return PageRequest.of(page, size);
    }
}
