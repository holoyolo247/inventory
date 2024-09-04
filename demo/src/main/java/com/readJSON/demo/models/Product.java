package com.readJSON.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @Id
    private String productId;
    private String name;
    private double price;
    private double rating;
    private long stockQuantity;

}
