package com.moneymanager.backend.dto;

public class DashboardSummary {

    private String period;   // month / year / week
    private String type;     // INCOME / EXPENSE
    private Double total;

    public DashboardSummary(String period, String type, Double total) {
        this.period = period;
        this.type = type;
        this.total = total;
    }

    public String getPeriod() {
        return period;
    }

    public String getType() {
        return type;
    }

    public Double getTotal() {
        return total;
    }
}
