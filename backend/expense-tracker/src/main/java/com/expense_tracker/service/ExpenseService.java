package com.expense_tracker.service;


import com.expense_tracker.modal.ExpenseDto;
import com.expense_tracker.modal.ExpenseResponse;

import java.util.List;

public interface ExpenseService {

    public ExpenseDto saveExpense(ExpenseDto expenseDto);
    public ExpenseDto findExpenseById(Long id);
    public ExpenseResponse findAllExpenses(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    public ExpenseDto updateExpense(Long id, ExpenseDto expenseDto);
    public String deleteExpenseById(Long id);
}
