package ru.homework.javacourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.homework.javacourse.model.GameEntity;
import ru.homework.javacourse.model.LocationEntity;
import ru.homework.javacourse.repository.LocationJpaRepository;

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
    public List<LocationEntity> getLocation() {
        List<LocationEntity> list = new ArrayList<>();
        locationJpaRepository.findAll().forEach(testEntity -> list.add(testEntity));
        return list;
    }

    @PostMapping("/location")
    public void addLocation(@RequestBody LocationEntity object) {
        locationJpaRepository.save(object);
    }

    @DeleteMapping("/location")
    public void deleteLocation(@RequestBody LocationEntity object) {
        locationJpaRepository.delete(object);
    }

    @PutMapping("/location/{id}")
    public void updateLocation(@RequestBody LocationEntity newLocationEntity, @PathVariable Long id) {

        locationJpaRepository.findById(id)
                .map(locationEntity -> {
                    locationEntity.setDescription(newLocationEntity.getDescription());
                    locationEntity.setName(newLocationEntity.getName());
                    locationEntity.setState(newLocationEntity.getState());
                    return locationJpaRepository.save(locationEntity);
                })
                .orElseGet(() -> {
                    newLocationEntity.setIdLocation(id);
                    return locationJpaRepository.save(newLocationEntity);
                });
    }
}
