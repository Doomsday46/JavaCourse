package ru.homework.javacourse.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.homework.javacourse.model.GameEntity;

@Repository
public interface GameJpaRepository extends CrudRepository<GameEntity, Long> {
}
