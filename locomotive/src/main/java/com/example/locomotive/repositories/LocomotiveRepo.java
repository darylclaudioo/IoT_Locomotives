package com.example.locomotive.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.locomotive.models.Locomotive;

@Repository
public interface LocomotiveRepo extends MongoRepository<Locomotive, UUID> {
    
}
