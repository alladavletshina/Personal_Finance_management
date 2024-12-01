package org.example;

import java.util.HashMap;
import java.util.Map;

public class Wallet {
    public Map<String, Double> budgets;
    AuthManager authManager;

    public Wallet(AuthManager authManager) {
        budgets = new HashMap<>();
        this.authManager = authManager;
    }

    // Методы для работы с кошельком

    public void setBudget(String category, double amount) {
        // Проверяем, существует ли уже данная категория в карте
        if (budgets.containsKey(category)) {
            // Если да, обновляем значение, прибавляя новую сумму к старой
            double currentAmount = budgets.get(category);
            budgets.put(category, amount);
            System.out.println();
            System.out.println("Категория " + category + " уже существовала в бюджете. Старое значение: " + currentAmount + "\nВы назначили новое значение: " + amount);
        } else {
            // Если нет, добавляем новую запись
            budgets.put(category, amount);
        }
    }

    public Map<String, Double> getBudgets() {
        return budgets;
    }

}

