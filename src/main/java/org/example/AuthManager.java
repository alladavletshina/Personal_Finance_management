package org.example;

import java.util.HashMap;
import java.util.Map;

public class AuthManager {
    private Map<String, String> users;
    private String currentUser;

    public AuthManager() {
        users = new HashMap<>();
    }

    // Методы для регистрации и авторизации пользователей

    public void addUser(String login, String password) {

        if (users.containsKey(login)) {
            throw new IllegalArgumentException("Пользователь с таким именем уже зарегистрирован.");
        }
        users.put(login,password);
    }

    public boolean authenticate(String login, String password) {

        String storedPassword = users.get(login);

        if (storedPassword == null || !storedPassword.equals(password)) {
            return false;
        }

        // Сохраняем текущий авторизованный пользователь
        currentUser = login;
        return true;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void displayAllUsers() {
        for (Map.Entry<String, String> entry : users.entrySet()) {
            System.out.println("Username: " + entry.getKey() + ", Password: " + entry.getValue());
        }
    }

}
