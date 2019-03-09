package ru.homework.javacourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.homework.javacourse.data.GameJpaRepository;
import ru.homework.javacourse.models.Game;

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
    public List<Game> getGame() {
        List<Game> list = new ArrayList<>();
        gameRepository.findAll().forEach(testEntity -> list.add(testEntity));
        return list;
    }

    @PostMapping("/game")
    public void addGame(@RequestBody Game object) {
        gameRepository.save(object);
    }

    @DeleteMapping("/game")
    public void deleteGame(@RequestBody Game object) {
        gameRepository.delete(object);
    }

    @PutMapping("/game/{id}")
    public void updateGame(@RequestBody Game newGame, @PathVariable Long id) {

        gameRepository.findById(id)
                .map(gameEntity -> {
                    gameEntity.setScore(newGame.getScore());
                    gameEntity.setDatetime(newGame.getDatetime());
                    gameEntity.setLocation(newGame.getLocation());
                    gameEntity.setState(newGame.getState());
                    return gameRepository.save(gameEntity);
                })
                .orElseGet(() -> {
                    newGame.setId(id);
                    return gameRepository.save(newGame);
                });
    }
}