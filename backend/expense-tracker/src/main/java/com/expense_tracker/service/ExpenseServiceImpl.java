package com.expense_tracker.service;

import com.expense_tracker.entity.Expense;
import com.expense_tracker.exceptionHandling.ResourceNotFoundException;
import com.expense_tracker.modal.ExpenseDto;
import com.expense_tracker.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService{

    private ExpenseRepository expenseRepository;
    private ModelMapper modelMapper;

    @Override
    public ExpenseDto saveExpense(ExpenseDto expenseDto) {
        Expense expense = modelMapper.map(expenseDto, Expense.class);
        Expense saved = expenseRepository.save(expense);
        return modelMapper.map(saved, ExpenseDto.class);
    }

    @Override
    public ExpenseDto findExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Expense not found with id: "+id));
        return modelMapper.map(expense, ExpenseDto.class);
    }

    @Override
    public List<ExpenseDto> findAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        List<ExpenseDto> expenseDtos = new ArrayList<>();
        expenses.forEach(expense->expenseDtos.add(modelMapper.map(expense, ExpenseDto.class)));
        return expenseDtos;
    }

    @Override
    public ExpenseDto updateExpense(Long id, ExpenseDto expenseDto) {
        Expense expense = expenseRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Expense not found with id: "+id));
        expense.setDescription(expenseDto.getDescription());
        expense.setCategory(expenseDto.getCategory());
        expense.setExpenseDate(expenseDto.getExpenseDate());
        expense.setTitle(expenseDto.getTitle());
        expense.setAmount(expenseDto.getAmount());

        Expense savedExpense = expenseRepository.save(expense);
        return modelMapper.map(expense, ExpenseDto.class);
    }

    @Override
    public String deleteExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Expense not found with id: "+id));
        if(expense!=null){
            expenseRepository.delete(expense);
            return "Expense has been deleted";
        }else {
            return "Expense not found with id: "+id;
        }
    }

}
