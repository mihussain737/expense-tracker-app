package com.expense_tracker.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ExpenseDto {


    private String title;

    private BigDecimal amount;

    private String category;

    private String description;

    private LocalDate expenseDate;
}
