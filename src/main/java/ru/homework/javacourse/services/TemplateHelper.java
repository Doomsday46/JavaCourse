package ru.homework.javacourse.services;

import ru.homework.javacourse.models.User;

public interface TemplateHelper {
    boolean isLoggedIn();
    boolean isAdmin();
    boolean isAdmin(User user);
    String getUsername();

    String removeSpaces(String fieldName);
}
