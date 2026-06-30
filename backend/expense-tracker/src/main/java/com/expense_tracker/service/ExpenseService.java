package com.expense_tracker.service;


import com.expense_tracker.modal.ExpenseDto;
import com.expense_tracker.modal.ExpenseResponse;

import java.util.List;

public interface ExpenseService {

    public ExpenseDto saveExpense(String username,ExpenseDto expenseDto);
    public ExpenseDto findExpenseById(Long id,String username);
    public ExpenseResponse findAllExpenses(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder,String username);
    public ExpenseDto updateExpense(Long id, ExpenseDto expenseDto,String username);
    public String deleteExpenseById(Long id,String username);
}
