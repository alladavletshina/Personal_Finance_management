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

        System.out.println("Установлен бюджет в размере " + budgets.get(category) + " рублей по категории " + category);
    }

    public void viewBudgets() {
        if (budgets.isEmpty()) {
            System.out.println("Нет записей о бюджетах.");
        } else {
            System.out.println("Текущие бюджеты по категориям:");
            for (String category : budgets.keySet()) {
                System.out.printf("%s: %.2f\n", category, budgets.get(category));
            }
        }
    }

    /*public void loadFromFile() {
        try {
            File file = new File("filename.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line.trim());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

}
