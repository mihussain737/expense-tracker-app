package com.expense_tracker.controller;

import com.expense_tracker.modal.ExpenseDto;
import com.expense_tracker.modal.ExpenseResponse;
import com.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<ExpenseDto> addExpense(@Valid @RequestBody ExpenseDto expenseDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ExpenseDto savedExpense = expenseService.saveExpense(username,expenseDto);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ExpenseResponse> findAllExpenses(
//            @RequestParam(name = "keyword", defaultValue = "", required = false) String keyword,
//            @RequestParam(name = "category", defaultValue = "", required = false) String category,
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
//        List<ExpenseDto> savedExpenses = expenseService.findAllExpenses();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ExpenseResponse savedExpenses = expenseService.findAllExpenses(pageNumber, pageSize, sortBy, sortOrder,username);
        return new ResponseEntity<>(savedExpenses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto>  findExpenseById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ExpenseDto expenseDto = expenseService.findExpenseById(id,username);
        return ResponseEntity.ok(expenseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpenseById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String message = expenseService.deleteExpenseById(id,username);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long id,@RequestBody ExpenseDto expenseDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ExpenseDto updatedExpense = expenseService.updateExpense(id, expenseDto, username);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }
}
