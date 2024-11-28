package org.example;

import java.util.HashMap;
import java.util.Map;

public class Transaction {

    public Map<String, Double> incomes;
    public Map<String, Double> expenses;

    public Transaction() {
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

        System.out.println("Расход успешно добавлен!");
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
}
