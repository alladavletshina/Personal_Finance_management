package org.example;

public class Budget {
    private Category category;
    private double budgetAmount;
    private double spentAmount;

    public Budget(double budgetAmount) {
        this.budgetAmount = budgetAmount;
        this.spentAmount = 0.0;
    }

    // Методы для работы с бюджетом

    public double getRemainingAmount() {
        return budgetAmount - spentAmount;
    }

    public void setSpentAmount(double amount) {
        spentAmount += amount;
    }
}
