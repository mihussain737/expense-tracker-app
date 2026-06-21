package com.expense_tracker.modal;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ExpenseDto {

    @NotEmpty(message = "title can't be empty")
    @Size(min = 4, max = 100 ,message = "title should be 4 to 100 char")
    private String title;
    private BigDecimal amount;
    @NotEmpty(message = "category can't be empty")
    @Size(min = 4, max = 100 ,message = "category should be 4 to 100 char")
    private String category;
    private String description;
    private LocalDate expenseDate;
}
