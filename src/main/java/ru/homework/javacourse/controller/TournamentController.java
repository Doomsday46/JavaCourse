package ru.homework.javacourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.homework.javacourse.model.GameEntity;
import ru.homework.javacourse.model.TournamentEntity;
import ru.homework.javacourse.repository.TournamentJpaRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TournamentController {

    private TournamentJpaRepository tournamentJpaRepository;

    @Autowired
    public TournamentController(TournamentJpaRepository tournamentJpaRepository) {
        this.tournamentJpaRepository = tournamentJpaRepository;
    }

    @GetMapping("/tournament")
    @Transactional
    public List<TournamentEntity> getTournament() {
        List<TournamentEntity> list = new ArrayList<>();
        tournamentJpaRepository.findAll().forEach(testEntity -> list.add(testEntity));
        return list;
    }

    @PostMapping("/tournament")
    public void addTournament(@RequestBody TournamentEntity object) {
        tournamentJpaRepository.save(object);
    }

    @DeleteMapping("/tournament")
    public void deleteTournament(@RequestBody TournamentEntity object) {
        tournamentJpaRepository.delete(object);
    }

    @PutMapping("/tournament/{id}")
    public void updateTournament(@RequestBody TournamentEntity newTournamentEntity, @PathVariable Long id) {

        tournamentJpaRepository.findById(id)
                .map(tournamentEntity -> {
                    tournamentEntity.setBeginDate(newTournamentEntity.getBeginDate());
                    tournamentEntity.setName(newTournamentEntity.getName());
                    return tournamentJpaRepository.save(tournamentEntity);
                })
                .orElseGet(() -> {
                    newTournamentEntity.setIdTournament(id);
                    return tournamentJpaRepository.save(newTournamentEntity);
                });
    }
}
