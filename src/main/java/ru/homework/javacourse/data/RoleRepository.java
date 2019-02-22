package ru.homework.javacourse.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.homework.javacourse.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
