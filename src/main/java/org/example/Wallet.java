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

    public static void main(String[] args) {
        Wallet wallet = new Wallet();

        Category food = new Category("Food");
        Category transport = new Category("Transport");

        wallet.setBudget(food, 3000.0);     // Установлен бюджет на еду в размере 3000 рублей
        wallet.setBudget(transport, 2000.0); // Установлен бюджет на транспорт в размере 2000 рублей

        Transaction income = new Transaction(food, 8000.0, true);       // Доход в размере 8000 рублей
        Transaction expenseFood = new Transaction(food, 1200.0, false); // Расход на еду в размере 1200 рублей
        Transaction expenseTransport = new Transaction(transport, 600.0, false); // Расход на транспорт в размере 600 рублей

        wallet.recordTransaction(income);
        wallet.recordTransaction(expenseFood);
        wallet.recordTransaction(expenseTransport);

        System.out.println("Текущий баланс: " + wallet.getBalance());          // Текущий баланс: 6200.0
        System.out.println("Оставшийся бюджет на еду: " + wallet.getRemainingBudget(food)); // Оставшийся бюджет на еду: 1800.0
        System.out.println("Оставшийся бюджет на транспорт: " + wallet.getRemainingBudget(transport)); // Оставшийся бюджет на транспорт: 1400.0
    }
}
