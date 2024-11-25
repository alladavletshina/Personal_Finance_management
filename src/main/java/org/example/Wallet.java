package org.example;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
    public double balance;
    private List<Transaction> transactions;
    private List<Budget> budgets;

    public Wallet() {
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
        this.budgets = new ArrayList<>();
    }

    // Методы для работы с кошельком

    public void safeToFile() {
        // Здесь будет логика для записи данных в файл
    }

    public void loadFromFile() {
        // Здесь будет логика для чтения данных из файла
    }
}
