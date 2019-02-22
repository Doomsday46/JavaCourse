package ru.homework.javacourse.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.homework.javacourse.data.LocationJpaRepository;
import ru.homework.javacourse.models.Location;
import ru.homework.javacourse.models.Player;
import ru.homework.javacourse.services.LocationService;

import java.util.List;
import java.util.Optional;
@Service
public class LocationServiceImpl implements LocationService {

    private LocationJpaRepository locationJpaRepository;

    @Autowired
    public LocationServiceImpl(LocationJpaRepository locationJpaRepository) {
        this.locationJpaRepository = locationJpaRepository;
    }

    @Override
    public Location findById(Long id) {
        Optional<Location> location = locationJpaRepository.findById(id);
        return location.orElse(null);
    }

    @Override
    public List<Location> getAll() {
        return locationJpaRepository.findAll();
    }

    @Override
    public void save(Location location) {
        locationJpaRepository.save(location);
    }
}
