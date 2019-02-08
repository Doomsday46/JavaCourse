package ru.homework.javacourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.homework.javacourse.model.LocationEntity;
import ru.homework.javacourse.model.PrizePlaceEntity;
import ru.homework.javacourse.repository.PrizePlaceJpaRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PrizePlaceController {

    private PrizePlaceJpaRepository prizePlaceJpaRepository;

    @Autowired
    public PrizePlaceController(PrizePlaceJpaRepository prizePlaceJpaRepository) {
        this.prizePlaceJpaRepository = prizePlaceJpaRepository;
    }

    @GetMapping("/prizePlace")
    @Transactional
    public List<PrizePlaceEntity> getPrizePlace() {
        List<PrizePlaceEntity> list = new ArrayList<>();
        prizePlaceJpaRepository.findAll().forEach(testEntity -> list.add(testEntity));
        return list;
    }

    @PostMapping("/prizePlace")
    public void addPrizePlace(@RequestBody PrizePlaceEntity object) {
        prizePlaceJpaRepository.save(object);
    }

    @DeleteMapping("/prizePlace")
    public void deletePrizePlace(@RequestBody PrizePlaceEntity object) {
        prizePlaceJpaRepository.delete(object);
    }

    @PutMapping("/prizePlace/{id}")
    public void updatePrizePlace(@RequestBody PrizePlaceEntity newPrizePlaceEntity, @PathVariable Long id) {

        prizePlaceJpaRepository.findById(id)
                .map(prizePlaceEntity -> {
                    prizePlaceEntity.setPrizePlace(newPrizePlaceEntity.getPrizePlace());
                    prizePlaceEntity.setPlayer(newPrizePlaceEntity.getPlayer());
                    prizePlaceEntity.setTournament(newPrizePlaceEntity.getTournament());
                    return prizePlaceJpaRepository.save(prizePlaceEntity);
                })
                .orElseGet(() -> {
                    newPrizePlaceEntity.setIdPrizePlace(id);
                    return prizePlaceJpaRepository.save(newPrizePlaceEntity);
                });
    }
}
