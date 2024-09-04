package com.readJSON.demo.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.OffsetDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Expense {

    @Id
    private String expenseId;
    private String category;
    private Double amount;
    private OffsetDateTime  timestamp;
}
