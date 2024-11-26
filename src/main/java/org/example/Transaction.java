package org.example;

public class Transaction {
    private Category category;
    private double amount;
    private boolean isIncome;

    public Transaction(Category category, double amount, boolean isIncome) {
        this.amount = amount;
        this.category = category;
        this.isIncome = isIncome;
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isIncome() {
        return isIncome;
    }
}
