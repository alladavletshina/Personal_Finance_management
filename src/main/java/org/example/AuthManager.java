package org.example;

import java.util.HashMap;
import java.util.Map;

public class AuthManager {
    private Map<String, String> users;

    public AuthManager() {
        users = new HashMap<>();
    }

    // Методы для регистрации и авторизации пользователей

    public void addUser(User user) {
        String login = user.getLogin();
        String password = user.getPassword();

        if (users.containsKey(login)) {
            throw new IllegalArgumentException("Пользователь с таким именем уже зарегистрирован.");
        }
        users.put(login,password);
    }

    public boolean authenticate(User user) {
        String login = user.getLogin();
        String password = user.getPassword();

        String storedPassword = users.get(login);
        return storedPassword != null && storedPassword.equals(password);
    }

    public void displayAllUsers() {
        for (Map.Entry<String, String> entry : users.entrySet()) {
            System.out.println("Username: " + entry.getKey() + ", Password: " + entry.getValue());
        }
    }

}
