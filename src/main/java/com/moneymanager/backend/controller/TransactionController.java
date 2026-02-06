package com.moneymanager.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.moneymanager.backend.dto.CategorySummary;
import com.moneymanager.backend.dto.DashboardSummary;
import com.moneymanager.backend.dto.TransactionRequest;
import com.moneymanager.backend.model.Transaction;
import com.moneymanager.backend.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // ADD TRANSACTION
    @PostMapping
    public Transaction addTransaction(@Valid @RequestBody TransactionRequest request) {

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setDivision(request.getDivision());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setUserId(request.getUserId());

        return transactionService.addTransaction(transaction);
    }

    // ✅ FIXED ENDPOINT (NOW SAFE)
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUser(@PathVariable String userId) {
        return transactionService.getTransactionsByUser(userId);
    }

    @GetMapping("/range")
    public List<Transaction> getTransactionsBetweenDates(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to) {

        return transactionService.getTransactionsBetween(from, to);
    }

    @GetMapping("/filter")
    public List<Transaction> filterTransactions(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String division) {

        return transactionService.filterTransactions(type, category, division);
    }

    @GetMapping("/dashboard/weekly")
    public List<DashboardSummary> getWeeklyDashboard() {
        return transactionService.getWeeklyDashboard();
    }

    @GetMapping("/dashboard/monthly")
    public List<DashboardSummary> getMonthlyDashboard() {
        return transactionService.getMonthlyDashboard();
    }

    @GetMapping("/dashboard/yearly")
    public List<DashboardSummary> getYearlyDashboard() {
        return transactionService.getYearlyDashboard();
    }

    @GetMapping("/dashboard/category")
    public List<CategorySummary> getCategoryDashboard() {
        return transactionService.getCategorySummary();
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(
            @PathVariable String id,
            @Valid @RequestBody TransactionRequest request) {

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setDivision(request.getDivision());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setUserId(request.getUserId());

        return transactionService.updateTransaction(id, transaction);
    }

    @DeleteMapping("/cleanup")
    public String cleanup() {
        transactionService.cleanInvalidTransactions();
        return "Invalid transactions deleted";
    }
}