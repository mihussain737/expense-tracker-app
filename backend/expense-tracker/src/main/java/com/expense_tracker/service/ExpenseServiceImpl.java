package com.expense_tracker.service;

import com.expense_tracker.entity.Expense;
import com.expense_tracker.entity.User;
import com.expense_tracker.exceptionHandling.ResourceNotFoundException;
import com.expense_tracker.modal.ExpenseDto;
import com.expense_tracker.modal.ExpenseResponse;
import com.expense_tracker.repository.ExpenseRepository;
import com.expense_tracker.repository.UserRepository;
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
    private UserRepository userRepository;

    @Override
    public ExpenseDto saveExpense(String username,ExpenseDto expenseDto) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: "+username));
        Expense expense = modelMapper.map(expenseDto, Expense.class);
        expense.setUser(user);
        Expense saved = expenseRepository.save(expense);
        return modelMapper.map(saved, ExpenseDto.class);
    }

    @Override
    public ExpenseDto findExpenseById(Long id, String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: "+username));
        Expense expense = expenseRepository.findByIdAndUser(id,user).orElseThrow(()->
                new ResourceNotFoundException("Expense not found with id: "+id));
        return modelMapper.map(expense, ExpenseDto.class);
    }

    @Override
    public ExpenseResponse findAllExpenses(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder,String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: "+username));
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNumber, pageSize,sortByAndOrder);
        Page<Expense> expensePage = expenseRepository.findByUser(user,pageable);
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
    public ExpenseDto updateExpense(Long id, ExpenseDto expenseDto,String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: "+username));
        Expense expense = expenseRepository.findByIdAndUser(id,user).orElseThrow(()->
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
    public String deleteExpenseById(Long id,String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: "+username));
//        Expense expense = expenseRepository.findById(id).orElseThrow(()->
//                new ResourceNotFoundException("Expense not found with id: "+id));
        Expense expense = expenseRepository.findByIdAndUser(id,user).orElseThrow(()->
                new ResourceNotFoundException("Expense not found with id: "+id));
            expenseRepository.delete(expense);
            return "Expense has been deleted";
    }
}
