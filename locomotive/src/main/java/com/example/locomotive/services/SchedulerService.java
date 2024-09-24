package com.example.locomotive.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SchedulerService {
    private final RestTemplate restTemplate = new RestTemplate();
    private Random randomizer = new Random();
    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    public void sendDataToNode() {
        String[] locomotiveNameList = { "Loco", "Motif", "Scheduler", "Random", "Nama", "Cek", "Lokomotif", "1", "Lokomotif Lagi", "Kereta" };
        String[] locomotiveDimensionList = { "200x100x200", "300x200x200", "400x300x200", "500x400x300", "600x500x300" };
        String[] statusList = { "Available", "Not Available", "Maintenance" };

        String locomotiveName = locomotiveNameList[randomizer.nextInt(locomotiveNameList.length)];
        String locomotiveDimension = locomotiveDimensionList[randomizer.nextInt(locomotiveDimensionList.length)];
        String status = statusList[randomizer.nextInt(statusList.length)];

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);

        LocalTime time = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = time.format(timeFormatter);

        Map<String, Object> data = Map.of(
                "kodeLoco", UUID.randomUUID(),
                "namaLoco", locomotiveName,
                "dimensiLoco", locomotiveDimension,
                "status", status,
                "tanggal", formattedDate,
                "jam", formattedTime
        );

        logger.info("Data sent to nodejs: {}", data);
        String url = "http://localhost:3001/receive-data";
        restTemplate.postForEntity(url, data, String.class);
    }
}
