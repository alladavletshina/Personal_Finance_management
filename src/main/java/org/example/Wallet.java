package org.example;

import java.util.HashMap;
import java.util.Map;

public class Wallet {
    public double balance;
    private Map<Category, Budget> budgets;

    public Wallet() {
        this.balance = 0.0;
        this.budgets = new HashMap<>();
    }

    // Методы для работы с кошельком

    public void recordTransaction(Transaction transaction) {
        Category category = transaction.getCategory();
        double amount = transaction.getAmount();

        if (transaction.isIncome()) {
            balance += amount;
        } else {
            balance -= amount;
            updateBudget(category, amount);
        }
    }

    public void addIncome(double amount) {
        balance += amount;
    }

    /*public void addExpence(Category category, double amount) {
        balance -= amount;
    }*/

    public void setBudget(Category category, double budgetAmount) {
        budgets.put(category, new Budget(budgetAmount));
    }

    public double getBalance() {
        return balance;
    }

    public double getRemainingBudget(Category category) {
        Budget budget = budgets.get(category);
        if (budget != null) {
            return budget.getRemainingAmount();
        } else {
            return 0.0;
        }
    }

    public void updateBudget(Category category, double spentAmount){
        Budget budget = budgets.get(category);
        if (budget != null) {
            budget.setSpentAmount(spentAmount);
        }
    }

    public void safeToFile() {
        // Здесь будет логика для записи данных в файл
    }

    public void loadFromFile() {
        // Здесь будет логика для чтения данных из файла
    }

}
