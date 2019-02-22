package ru.homework.javacourse.services;

import ru.homework.javacourse.models.Role;
import ru.homework.javacourse.models.User;

public interface RoleService {
    Role findRoleByName(String name);
    void createRole(String name);
    void deleteRole(String name);
    void addRoleToUser(Role role, User user);
    void removeRoleFromUser(Role role, User user);


}
