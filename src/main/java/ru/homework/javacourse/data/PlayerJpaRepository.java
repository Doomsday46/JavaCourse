package ru.homework.javacourse.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.homework.javacourse.models.Player;

@Repository
public interface PlayerJpaRepository extends JpaRepository<Player, Long> {
}
