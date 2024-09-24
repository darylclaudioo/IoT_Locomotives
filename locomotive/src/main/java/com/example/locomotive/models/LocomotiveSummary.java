package com.example.locomotive.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "locomotive_summary")
public class LocomotiveSummary {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "active")
    private Integer active;

    @Column(name = "non_active")
    private Integer nonActive;

    @Column(name = "maintenance")
    private Integer maintenance;

    @Column(name = "total_locomotive")
    private Integer totalLocomotive;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
