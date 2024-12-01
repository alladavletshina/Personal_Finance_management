package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    public double balance;
    public Map<String, Double> budgets;

    public Wallet() {
        this.balance = 0.0;
        budgets = new HashMap<>();
    }

    // Методы для работы с кошельком

    public void setBudget(String category, double amount) {
        // Проверяем, существует ли уже данная категория в карте
        if (budgets.containsKey(category)) {
            // Если да, обновляем значение, прибавляя новую сумму к старой
            double currentAmount = budgets.get(category);
            budgets.put(category, currentAmount + amount);
        } else {
            // Если нет, добавляем новую запись
            budgets.put(category, amount);
        }

        //System.out.println("Установлен бюджет в размере " + budgets.get(category) + " рублей для категории " + category);
    }

    public Map<String, Double> getBudgets() {
        return budgets;
    }

}
