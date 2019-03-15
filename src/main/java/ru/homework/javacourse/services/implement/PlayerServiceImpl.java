package ru.homework.javacourse.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.homework.javacourse.data.PlayerJpaRepository;
import ru.homework.javacourse.models.Player;
import ru.homework.javacourse.services.PlayerService;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerJpaRepository playerJpaRepository;

    @Autowired
    public PlayerServiceImpl(PlayerJpaRepository playerJpaRepository) {
        this.playerJpaRepository = playerJpaRepository;
    }

    @Override
    public Player findById(Long id) {
        Optional<Player> task = playerJpaRepository.findById(id);
        return task.orElse(null);
    }

    @Override
    public List<Player> getAll() {
        return playerJpaRepository.findAll();
    }

    @Override
    public void save(Player player) {
        playerJpaRepository.saveAndFlush(player);
    }

    @Override
    public void delete(Long id) {
        playerJpaRepository.delete(playerJpaRepository.findById(id).get());
    }
}
