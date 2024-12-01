package org.example;

import java.util.HashMap;
import java.util.Map;

public class AuthManager {
    public Map<String, String> users;
    private String currentUser;
    private String currentPassword;

    public AuthManager() {
        users = new HashMap<>();
    }

    // Методы для регистрации и авторизации пользователей

    public void addUser(String login, String password) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Логин не может быть пустым.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Пароль не может быть пустым.");
        }

        if (users.containsKey(login)) {
            throw new IllegalArgumentException("Пользователь с логином '" + login + "' уже зарегистрирован.");
        }

        users.put(login, password);
    }

    public boolean authenticate(String login, String password) {

        String storedPassword = users.get(login);

        if (storedPassword == null || !storedPassword.equals(password)) {
            return false;
        }

        // Сохраняем текущий авторизованный пользователь
        currentUser = login;
        currentPassword = password;
        return true;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }


}
