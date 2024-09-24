package com.example.locomotive.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.locomotive.models.Locomotive;
import com.example.locomotive.models.LocomotiveStatus;
import com.example.locomotive.models.LocomotiveSummary;
import com.example.locomotive.repositories.LocomotiveStatusRepo;
import com.example.locomotive.repositories.LocomotiveSummaryRepo;

import lombok.RequiredArgsConstructor;

@Service("mongoService")
@RequiredArgsConstructor(onConstructor = @__({ @Autowired }))
public class MongoService {
    
    private final LocomotiveStatusRepo statusRepo;
    private final LocomotiveSummaryRepo summaryRepo;
    private final MongoTemplate mongoTemplate;

    private final Logger logger = LoggerFactory.getLogger(MongoService.class);

    public List<Locomotive> fetchAllLocomotives() {
        return mongoTemplate.findAll(Locomotive.class);
    }

    @Scheduled(fixedRate = 60000) // Every 1 minute
    public void fetchData() {
        logger.info("Fetching Locomotives");
        List<Locomotive> fetched = fetchAllLocomotives();
        logger.info("Locomotives Fetched Successfully");

        if (fetched != null && !fetched.isEmpty()) {
            updateLocomotiveStatuses(fetched);
            saveLocomotiveSummary(fetched);
        }
    }

    private void saveLocomotiveSummary(List<Locomotive> fetched) {
        long totalActive = fetched.stream().filter(loco -> "Available".equals(loco.getStatus())).count();
        long totalNotActive = fetched.stream().filter(loco -> "Not Available".equals(loco.getStatus())).count();
        long totalMaintenance = fetched.stream().filter(loco -> "Maintenance".equals(loco.getStatus())).count();
        long totalLocomotive = fetched.size();

        LocomotiveSummary summary = LocomotiveSummary.builder()
                .id(UUID.randomUUID())
                .active((int) totalActive)
                .nonActive((int) totalNotActive)
                .maintenance((int) totalMaintenance)
                .totalLocomotive((int) totalLocomotive)
                .updatedAt(LocalDateTime.now())
                .build();

        summaryRepo.save(summary);
        logger.info("Sent Summary Data to PostgreSQL: {}", summary);
    }

    private void updateLocomotiveStatuses(List<Locomotive> locomotives) {
        Map<String, LocomotiveStatus> statusMap = new HashMap<>();

        for (Locomotive loco : locomotives) {
            String locoName = loco.getNamaLoco();

            LocomotiveStatus locomotiveStatus = statusMap.computeIfAbsent(locoName, name -> {
                Optional<LocomotiveStatus> optionalLocomotive = statusRepo.findByName(name);
                if (optionalLocomotive.isPresent()) {
                    return optionalLocomotive.get();
                } else {
                    LocomotiveStatus newLocomotiveStatus = new LocomotiveStatus();
                    newLocomotiveStatus.setId(UUID.randomUUID());
                    newLocomotiveStatus.setLocomotiveName(name);
                    return newLocomotiveStatus;
                }
            });

            long totalActive = locomotives.stream().filter(locomotive -> "Available".equals(locomotive.getStatus()) && locoName.equals(locomotive.getNamaLoco())).count();
            long totalNonactive = locomotives.stream().filter(locomotive -> "Not Available".equals(locomotive.getStatus()) && locoName.equals(locomotive.getNamaLoco())).count();
            long totalMaintenance = locomotives.stream().filter(locomotive -> "Maintenance".equals(locomotive.getStatus()) && locoName.equals(locomotive.getNamaLoco())).count();
            long totalLocomotive = totalActive + totalNonactive + totalMaintenance;

            locomotiveStatus.setActive((int) totalActive);
            locomotiveStatus.setNonActive((int) totalNonactive);
            locomotiveStatus.setMaintenance((int) totalMaintenance);
            locomotiveStatus.setTotalLocomotive((int) totalLocomotive);
        }

        statusRepo.saveAll(statusMap.values());
        logger.info("Locomotive Statuses Updated Successfully");
    }
    
}
