package ru.homework.javacourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.homework.javacourse.model.GameEntity;
import ru.homework.javacourse.repository.GameJpaRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController {

    private GameJpaRepository gameRepository;

    @Autowired
    public GameController(GameJpaRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/game")
    @Transactional
    public List<GameEntity> getGame() {
        List<GameEntity> list = new ArrayList<>();
        gameRepository.findAll().forEach(testEntity -> list.add(testEntity));
        return list;
    }

    @PostMapping("/game")
    public void addGame(@RequestBody GameEntity object) {
        gameRepository.save(object);
    }

    @DeleteMapping("/game")
    public void deleteGame(@RequestBody GameEntity object) {
        gameRepository.delete(object);
    }

    @PutMapping("/game/{id}")
    public void updateGame(@RequestBody GameEntity newGameEntity, @PathVariable Long id) {

        gameRepository.findById(id)
                .map(gameEntity -> {
                    gameEntity.setScore(newGameEntity.getScore());
                    gameEntity.setDatetime(newGameEntity.getDatetime());
                    gameEntity.setLocation(newGameEntity.getLocation());
                    gameEntity.setState(newGameEntity.getState());
                    return gameRepository.save(gameEntity);
                })
                .orElseGet(() -> {
                    newGameEntity.setId(id);
                    return gameRepository.save(newGameEntity);
                });
    }
}