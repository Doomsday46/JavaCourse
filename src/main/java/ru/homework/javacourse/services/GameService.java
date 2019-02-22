package ru.homework.javacourse.services;

import ru.homework.javacourse.models.Game;

import java.util.List;

public interface GameService {
    Game findById(Long id);
    List<Game> getAll();
    void save(Game game);
}
