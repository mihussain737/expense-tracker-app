package com.expense_tracker.controller;

import com.expense_tracker.modal.ExpenseDto;
import com.expense_tracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDto> addExpense(@RequestBody ExpenseDto expenseDto) {
        ExpenseDto savedExpense = expenseService.saveExpense(expenseDto);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDto>> findAllExpenses() {
        List<ExpenseDto> savedExpenses = expenseService.findAllExpenses();
        return new ResponseEntity<>(savedExpenses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto>  findExpenseById(@PathVariable Long id) {
        ExpenseDto expenseDto = expenseService.findExpenseById(id);
        return ResponseEntity.ok(expenseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpenseById(@PathVariable Long id) {
        String message = expenseService.deleteExpenseById(id);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long id,@RequestBody ExpenseDto expenseDto) {
        ExpenseDto updatedExpense = expenseService.updateExpense(id, expenseDto);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }
}
