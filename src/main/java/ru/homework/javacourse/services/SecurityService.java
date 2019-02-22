package ru.homework.javacourse.services;

import ru.homework.javacourse.models.User;

public interface SecurityService {
    String findLoggedInUsername();
    User findLoggedInUser();
    void autologin(String username, String password);
    void logout();
}
