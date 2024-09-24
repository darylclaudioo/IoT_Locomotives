package com.example.locomotive.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.locomotive.models.LocomotiveStatus;

@Repository
@EnableJpaRepositories
public interface LocomotiveStatusRepo extends JpaRepository<LocomotiveStatus, UUID> {
    
    @Query("SELECT l FROM LocomotiveStatus l WHERE l.locomotiveName = :name")
    public Optional<LocomotiveStatus> findByName(String name);
}
