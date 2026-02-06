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
    // BASIC QUERIES
    // ==========================
    List<Transaction> findByUserId(String userId);

    List<Transaction> findByTransactionDateBetween(
            LocalDateTime from,
            LocalDateTime to);

    void deleteByAmountIsNull();

    // ==========================
    // FILTER (FIXED ✅)
    // ==========================
    @Query("""
        {
          $and: [
            { $or: [ { ?0: { $exists: false } }, { type: ?0 } ] },
            { $or: [ { ?1: { $exists: false } }, { category: ?1 } ] },
            { $or: [ { ?2: { $exists: false } }, { division: ?2 } ] }
          ]
        }
    """)
    List<Transaction> filterTransactions(
            String type,
            String category,
            String division);

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
}