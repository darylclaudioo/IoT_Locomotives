package com.example.locomotive.repositories;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.locomotive.dto.response.LocomotiveSummaryResponse;
import com.example.locomotive.models.LocomotiveSummary;

@Repository
@EnableJpaRepositories
public interface LocomotiveSummaryRepo extends JpaRepository<LocomotiveSummary, UUID>, JpaSpecificationExecutor<LocomotiveSummary> {
    
    @Query("""
            SELECT new com.example.locomotive.dto.response.LocomotiveSummaryResponse(l)
            FROM LocomotiveSummary l
            WHERE l.updatedAt = COALESCE(:updatedAt, l.updatedAt)
            """)
    Page<LocomotiveSummaryResponse> findAllSummary(
        LocalDateTime updatedAt,
        Pageable pageable);

    @Query("SELECT new com.example.locomotive.dto.response.LocomotiveSummaryResponse(l) FROM LocomotiveSummary l ORDER BY l.updatedAt DESC LIMIT 1")
    LocomotiveSummaryResponse findLatestSummary();
}
