package com.expense_tracker.service;

import com.expense_tracker.entity.Expense;
import com.expense_tracker.exceptionHandling.ResourceNotFoundException;
import com.expense_tracker.modal.ExpenseDto;
import com.expense_tracker.modal.ExpenseResponse;
import com.expense_tracker.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ExpenseResponse findAllExpenses(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNumber, pageSize,sortByAndOrder);

        Page<Expense> expensePage = expenseRepository.findAll(pageable);
        List<Expense> allExpenses = expensePage.getContent();
        List<ExpenseDto> expenseDtos = new ArrayList<>();
        allExpenses.forEach(expense->expenseDtos.add(modelMapper.map(expense, ExpenseDto.class)));

        ExpenseResponse expenseResponse=new ExpenseResponse();
        expenseResponse.setContent(expenseDtos);
        expenseResponse.setPageNumber(expensePage.getNumber());
        expenseResponse.setPageSize(expensePage.getSize());
        expenseResponse.setTotalPages(expensePage.getTotalPages());
        expenseResponse.setTotalElements(expensePage.getTotalElements());
        expenseResponse.setIsLastPage(expensePage.isLast());
        return expenseResponse;
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
