package com.moneymanager.backend.dto;

public class MonthlySummaryResponse {

    private int month;
    private double totalIncome;
    private double totalExpense;

    public MonthlySummaryResponse(int month, double totalIncome, double totalExpense) {
        this.month = month;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
    }

    public int getMonth() {
        return month;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }
}
