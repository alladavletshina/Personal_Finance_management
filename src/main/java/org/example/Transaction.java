package org.example;

import java.util.HashMap;
import java.util.Map;

public class Transaction {

    public Map<String, Double> incomes;
    public Map<String, Double> expenses;
    private Wallet wallet;

    public Transaction(Wallet wallet) {
        this.wallet = wallet;
        incomes = new HashMap<>();
        expenses = new HashMap<>();
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

        // Обновляем бюджет, если он был установлен

        updateBudget(category, amount);

        System.out.println("Расход успешно добавлен!");
        System.out.printf("%s: %.2f\n", category, wallet.budgets.get(category));
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

        System.out.println("Доход успешно добавлен!");
    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("Нет записей о расходах.");
        } else {
            System.out.println("Текущие расходы по категориям:");
            for (String category : expenses.keySet()) {
                System.out.printf("%s: %.2f\n", category, expenses.get(category));
            }
        }
    }

    public void viewIncomes() {
        if (incomes.isEmpty()) {
            System.out.println("Нет записей о доходах.");
        } else {
            System.out.println("Текущие доходы по категориям:");
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
}
