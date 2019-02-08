package ru.homework.javacourse.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.homework.javacourse.model.PlayerEntity;

@Repository
public interface PlayerJpaRepository extends CrudRepository<PlayerEntity, Long> {
}
