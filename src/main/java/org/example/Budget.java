package org.example;

public class Budget {
    private Category category;
    private double limit;
    private double remaining;

    public Budget(Category category, double limit) {
        this.category = category;
        this.limit = limit;
        this.remaining = limit;
    }

    // Методы для работы с бюджетом
}
