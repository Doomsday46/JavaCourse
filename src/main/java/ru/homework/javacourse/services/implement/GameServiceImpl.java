package ru.homework.javacourse.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.homework.javacourse.data.GameJpaRepository;
import ru.homework.javacourse.models.Game;
import ru.homework.javacourse.services.GameService;

import java.util.List;
import java.util.Optional;
@Service
public class GameServiceImpl implements GameService {


    private GameJpaRepository gameJpaRepository;

    @Autowired
    public GameServiceImpl(GameJpaRepository gameJpaRepository) {
        this.gameJpaRepository = gameJpaRepository;
    }

    @Override
    public Game findById(Long id) {
        Optional<Game> game = gameJpaRepository.findById(id);
        return game.orElse(null);
    }

    @Override
    public List<Game> getAll() {
        return gameJpaRepository.findAll();
    }

    @Override
    public void save(Game game) {
        gameJpaRepository.save(game);
    }
}
