package ru.homework.javacourse.services;

import ru.homework.javacourse.models.Player;

import java.util.List;

public interface PlayerService {
    Player findById(Long id);
    List<Player> getAll();
    void save(Player player);
    void delete(Long id);
}
