package com.example.locomotive.models;

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
@Table(name = "locomotive_status")
public class LocomotiveStatus {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "locomotive_name")
    private String locomotiveName;

    @Column(name = "active")
    private Integer active;

    @Column(name = "non_active")
    private Integer nonActive;

    @Column(name = "maintenance")
    private Integer maintenance;

    @Column(name = "total_locomotive")
    private Integer totalLocomotive;
}
