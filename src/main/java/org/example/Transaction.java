package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Transaction {

    public Map<String, Double> incomes;
    public Map<String, Double> expenses;
    private Wallet wallet;

    public Transaction(Wallet wallet) {
        this.wallet = wallet;
        incomes = new HashMap<>();
        expenses = new HashMap<>();
    }

    public Map<String, Double> getExpenses() {
        return expenses;
    }

    public Map<String, Double> getIncomes() {
        return incomes;
    }

    public void addExpence(String category, double amount) {
        // Проверяем, существует ли уже данная категория в карте
        if (expenses.containsKey(category)) {
            // Если да, обновляем значение, прибавляя новую сумму к старой
            double currentAmount = expenses.get(category);
            expenses.put(category, currentAmount + amount);
        } else {
            // Если нет, добавляем новую запись
            expenses.put(category, amount);
        }

    }

    public void addIncome(String category, double amount) {
        // Проверяем, существует ли уже данная категория в карте
        if (incomes.containsKey(category)) {
            // Если да, обновляем значение, прибавляя новую сумму к старой
            double currentAmount = incomes.get(category);
            incomes.put(category, currentAmount + amount);
        } else {
            // Если нет, добавляем новую запись
            incomes.put(category, amount);
        }

    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("Нет записей о расходах.");
        } else {
            System.out.println("Расходы по категориям:");
            for (String category : expenses.keySet()) {
                System.out.printf("%s: %.2f\n", category, expenses.get(category));
            }
        }
    }

    public void viewIncomes() {
        if (incomes.isEmpty()) {
            System.out.println("Нет записей о доходах.");
        } else {
            System.out.println("Доходы по категориям:");
            for (String category : incomes.keySet()) {
                System.out.printf("%s: %.2f\n", category, incomes.get(category));
            }
        }
    }

    private void updateBudget(String category, double amount) {
        // Обновляем бюджет, если он был установлен
        if (wallet.budgets.containsKey(category)) {
            double remainingBudget = wallet.budgets.get(category) - amount;
            wallet.budgets.put(category, remainingBudget);
        }
    }

    public double getTotalIncome() {
        return incomes.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public double getTotalExpense() {
        return expenses.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public double getSpentAmount(String category) {
        // Используем getOrDefault для защиты от NPE
        Double spentAmount = expenses.getOrDefault(category, 0.0);
        return spentAmount;
    }

    public void viewBudgets() {
        if (wallet.budgets.isEmpty()) {
            System.out.println("Нет записей о бюджетах.");
        } else {
            System.out.println("Бюджеты по категориям:");
            for (String category : wallet.budgets.keySet()) {
                final double startBudget = wallet.budgets.getOrDefault(category, 0.0); // Используем getOrDefault для защиты от NPE
                double spentAmount = getSpentAmount(category);
                double remainingBudget = startBudget - spentAmount;

                if (spentAmount == 0) {
                    System.out.printf("%s: %.2f, оставшийся бюджет: %.2f (расходы отсутствуют)\n", category, startBudget, remainingBudget);
                } else {
                    System.out.printf("%s: %.2f, оставшийся бюджет: %.2f\n", category, startBudget, remainingBudget);
                }
            }

        }
    }

}
