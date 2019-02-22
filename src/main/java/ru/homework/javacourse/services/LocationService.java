package ru.homework.javacourse.services;

import ru.homework.javacourse.models.Location;

import java.util.List;

public interface LocationService {
    Location findById(Long id);
    List<Location> getAll();
    void save(Location location);
}
