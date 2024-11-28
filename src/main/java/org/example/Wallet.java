package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    public double balance;
    public Map<Category, Budget> budgets;

    public Wallet() {
        this.balance = 0.0;
        this.budgets = new HashMap<>();
    }

    // Методы для работы с кошельком

    public void setBudget(Category category, double budgetAmount) {
        budgets.put(category, new Budget(budgetAmount));
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
        try {
            File file = new File("filename.txt");
            FileWriter writer = new FileWriter(file, true);

            // Здесь будет логика для записи данных в файл
            writer.write("Данные для записи в файл\n");
            writer.flush();
            writer.close();
            System.out.println("Данные записаны в файл filename.txt");
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public void loadFromFile() {
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
    }

}
