package ru.homework.javacourse.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.homework.javacourse.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
