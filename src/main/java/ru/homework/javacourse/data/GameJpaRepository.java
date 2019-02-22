package ru.homework.javacourse.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.homework.javacourse.models.Game;

@Repository
public interface GameJpaRepository extends JpaRepository<Game, Long> {
}
