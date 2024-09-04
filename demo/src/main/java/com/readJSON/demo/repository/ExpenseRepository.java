package com.readJSON.demo.repository;

import com.readJSON.demo.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, String> {
}
