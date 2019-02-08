package ru.homework.javacourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.homework.javacourse.model.PlayerEntity;
import ru.homework.javacourse.repository.PlayerJpaRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PlayerController {

    private PlayerJpaRepository playerJpaRepository;

    @Autowired
    public PlayerController(PlayerJpaRepository playerJpaRepository) {
        this.playerJpaRepository = playerJpaRepository;
    }

    @GetMapping("/player")
    @Transactional
    public List<PlayerEntity> getPlayer() {
        List<PlayerEntity> list = new ArrayList<>();
        playerJpaRepository.findAll().forEach(testEntity -> list.add(testEntity));
        return list;
    }

    @PostMapping("/player")
    public void addPlayer(@RequestBody PlayerEntity object) {
        playerJpaRepository.save(object);
    }

    @DeleteMapping("/player")
    public void deletePlayer(@RequestBody PlayerEntity object) {
        playerJpaRepository.delete(object);
    }

    @PutMapping("/player/{id}")
    public void updatePlayer(@RequestBody PlayerEntity newPlayerEntity, @PathVariable Long id) {

        playerJpaRepository.findById(id)
                .map(playerEntity -> {
                    playerEntity.setFirstName(newPlayerEntity.getFirstName());
                    playerEntity.setSecondName(newPlayerEntity.getSecondName());
                    playerEntity.setAge(newPlayerEntity.getAge());
                    playerEntity.setPrizePlaceEntities(newPlayerEntity.getPrizePlaceEntities());
                    return playerJpaRepository.save(playerEntity);
                })
                .orElseGet(() -> {
                    newPlayerEntity.setIdPlayer(id);
                    return playerJpaRepository.save(newPlayerEntity);
                });
    }
}
