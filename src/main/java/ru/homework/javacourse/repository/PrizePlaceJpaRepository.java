package ru.homework.javacourse.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.homework.javacourse.model.PrizePlaceEntity;

@Repository
public interface PrizePlaceJpaRepository extends CrudRepository<PrizePlaceEntity, Long> {
}
