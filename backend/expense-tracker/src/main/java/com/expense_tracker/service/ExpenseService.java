package com.expense_tracker.service;


import com.expense_tracker.modal.ExpenseDto;

import java.util.List;

public interface ExpenseService {

    public ExpenseDto saveExpense(ExpenseDto expenseDto);
    public ExpenseDto findExpenseById(Long id);
    public List<ExpenseDto> findAllExpenses();
    public ExpenseDto updateExpense(Long id, ExpenseDto expenseDto);
    public String deleteExpenseById(Long id);
}
