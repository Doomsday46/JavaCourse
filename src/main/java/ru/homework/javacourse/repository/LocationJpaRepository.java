package ru.homework.javacourse.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.homework.javacourse.model.LocationEntity;

@Repository
public interface LocationJpaRepository extends CrudRepository<LocationEntity, Long> {
}
