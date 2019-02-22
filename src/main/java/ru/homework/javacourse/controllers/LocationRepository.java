package ru.homework.javacourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.homework.javacourse.models.Location;
import ru.homework.javacourse.data.LocationJpaRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LocationRepository {

    private LocationJpaRepository locationJpaRepository;

    @Autowired
    public LocationRepository(LocationJpaRepository locationJpaRepository) {
        this.locationJpaRepository = locationJpaRepository;
    }

    @GetMapping("/location")
    @Transactional
    public List<Location> getLocation() {
        List<Location> list = new ArrayList<>();
        locationJpaRepository.findAll().forEach(testEntity -> list.add(testEntity));
        return list;
    }

    @PostMapping("/location")
    public void addLocation(@RequestBody Location object) {
        locationJpaRepository.save(object);
    }

    @DeleteMapping("/location")
    public void deleteLocation(@RequestBody Location object) {
        locationJpaRepository.delete(object);
    }

    @PutMapping("/location/{id}")
    public void updateLocation(@RequestBody Location newLocation, @PathVariable Long id) {

        locationJpaRepository.findById(id)
                .map(location -> {
                    location.setDescription(newLocation.getDescription());
                    location.setName(newLocation.getName());
                    location.setState(newLocation.getState());
                    return locationJpaRepository.save(location);
                })
                .orElseGet(() -> {
                    newLocation.setIdLocation(id);
                    return locationJpaRepository.save(newLocation);
                });
    }
}
