package com.moneymanager.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.moneymanager.backend.dto.CategorySummary;

import com.moneymanager.backend.dto.DashboardSummary;
import com.moneymanager.backend.model.Transaction;
import com.moneymanager.backend.repository.TransactionRepository;
import java.time.Duration;
import java.util.Optional;
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction addTransaction(Transaction transaction) {
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll(
                Sort.by(Sort.Direction.DESC, "transactionDate"));
    }

    public List<Transaction> getTransactionsBetween(
            LocalDateTime from,
            LocalDateTime to) {
        return transactionRepository.findByTransactionDateBetween(from, to);
    }
    public List<DashboardSummary> getWeeklyDashboard() {
        return transactionRepository.getWeeklySummary();
    }

    public List<DashboardSummary> getMonthlyDashboard() {
        return transactionRepository.getMonthlySummary();
    }

    public List<DashboardSummary> getYearlyDashboard() {
        return transactionRepository.getYearlySummary();
    }

    public List<Transaction> filterTransactions(
            String type,
            String category,
            String division) {
        return transactionRepository.filterTransactions(type, category, division);
    }
    public List<CategorySummary> getCategorySummary() {
        return transactionRepository.getCategorySummary();
    }
    
    public Transaction updateTransaction(String id, Transaction updatedData) {

        Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // ⏱️ 12-hour restriction
        Duration duration = Duration.between(
                existing.getCreatedAt(),
                LocalDateTime.now()
        );

        if (duration.toHours() > 12) {
            throw new RuntimeException("Editing is allowed only within 12 hours");
        }

        // ✅ Update allowed fields
        existing.setAmount(updatedData.getAmount());
        existing.setType(updatedData.getType());
        existing.setCategory(updatedData.getCategory());
        existing.setDivision(updatedData.getDivision());
        existing.setDescription(updatedData.getDescription());
        existing.setTransactionDate(updatedData.getTransactionDate());
        existing.setUpdatedAt(LocalDateTime.now());

        return transactionRepository.save(existing);
    }
    
    public List<Transaction> getTransactionsByUser(String userId) {
        return transactionRepository.findByUserId(userId);
    }

    public void cleanInvalidTransactions() {
        transactionRepository.deleteByAmountIsNull();
    }
}
