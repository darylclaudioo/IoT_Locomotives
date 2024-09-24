package com.example.locomotive.models;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Document(collection = "locomotives")
public class Locomotive {
    
    @Id
    @Field("kodeLoco")
    private UUID kodeLoco;

    @Field("namaLoco")
    private String namaLoco;

    @Field("dimensiLoco")
    private String dimensiLoco;

    @Field("status")
    private String status;

    @Field("tanggal")
    private String tanggal;
    
    @Field("jam")
    private String jam;
}
