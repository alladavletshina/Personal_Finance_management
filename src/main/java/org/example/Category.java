package org.example;

public class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    // Геттеры и сеттеры

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();

    }
}
