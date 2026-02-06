package com.moneymanager.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.moneymanager.backend.dto.CategorySummary;
import com.moneymanager.backend.dto.DashboardSummary;
import com.moneymanager.backend.model.Transaction;

public interface TransactionRepository
        extends MongoRepository<Transaction, String> {

    // ==========================
    // DATE RANGE
    // ==========================
    List<Transaction> findByTransactionDateBetween(
            LocalDateTime from,
            LocalDateTime to);
    
    List<Transaction> findByUserId(String userId);

    // ==========================
    // WEEKLY SUMMARY
    // ==========================
    @Aggregation(pipeline = {
        "{ $group: { _id: { week: { $isoWeek: '$transactionDate' }, year: { $isoWeekYear: '$transactionDate' }, type: '$type' }, total: { $sum: '$amount' } } }",
        "{ $project: { _id: 0, period: { $concat: [ { $toString: '$_id.year' }, '-W', { $toString: '$_id.week' } ] }, type: '$_id.type', total: 1 } }",
        "{ $sort: { period: 1 } }"
    })
    List<DashboardSummary> getWeeklySummary();

    // ==========================
    // MONTHLY SUMMARY
    // ==========================
    @Aggregation(pipeline = {
        "{ $group: { _id: { month: { $month: '$transactionDate' }, type: '$type' }, total: { $sum: '$amount' } } }",
        "{ $project: { _id: 0, period: '$_id.month', type: '$_id.type', total: 1 } }"
    })
    List<DashboardSummary> getMonthlySummary();

    // ==========================
    // YEARLY SUMMARY
    // ==========================
    @Aggregation(pipeline = {
        "{ $group: { _id: { year: { $year: '$transactionDate' }, type: '$type' }, total: { $sum: '$amount' } } }",
        "{ $project: { _id: 0, period: '$_id.year', type: '$_id.type', total: 1 } }"
    })
    List<DashboardSummary> getYearlySummary();

    // ==========================
    // CATEGORY SUMMARY
    // ==========================
    @Aggregation(pipeline = {
        "{ $group: { _id: { category: '$category', type: '$type' }, total: { $sum: '$amount' } } }",
        "{ $project: { _id: 0, category: '$_id.category', type: '$_id.type', total: 1 } }",
        "{ $sort: { category: 1 } }"
    })
    List<CategorySummary> getCategorySummary();

    // ==========================
    // FILTER
    // ==========================
    @Query("{ $and: [ "
         + " { $or: [ { ?0: null }, { type: ?0 } ] }, "
         + " { $or: [ { ?1: null }, { category: ?1 } ] }, "
         + " { $or: [ { ?2: null }, { division: ?2 } ] } "
         + "] }")
    List<Transaction> filterTransactions(
            String type,
            String category,
            String division);

    // ==========================
    // CLEANUP
    // ==========================
    void deleteByAmountIsNull();
}
