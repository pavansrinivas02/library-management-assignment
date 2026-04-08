package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document(collection="books")

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Book {

    @Id
    private String id;

    private String title;

    private String author;

    private Boolean available;

    private String borrowedBy;

    private String policy;

    private Long expiryTime;

}