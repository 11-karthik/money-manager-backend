package com.moneymanager.backend.dto;

public class CategorySummary {

    private String category;
    private String type;   // INCOME / EXPENSE
    private Double total;

    public CategorySummary(String category, String type, Double total) {
        this.category = category;
        this.type = type;
        this.total = total;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public Double getTotal() {
        return total;
    }
}
