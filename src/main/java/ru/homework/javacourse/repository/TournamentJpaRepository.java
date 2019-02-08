package ru.homework.javacourse.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.homework.javacourse.model.TournamentEntity;

@Repository
public interface TournamentJpaRepository extends CrudRepository<TournamentEntity, Long> {
}
