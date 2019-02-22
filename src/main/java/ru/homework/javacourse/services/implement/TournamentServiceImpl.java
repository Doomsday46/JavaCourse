package ru.homework.javacourse.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.homework.javacourse.data.TournamentJpaRepository;
import ru.homework.javacourse.models.Tournament;
import ru.homework.javacourse.services.TournamentService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentServiceImpl implements TournamentService {

    private TournamentJpaRepository tournamentJpaRepository;

    @Autowired
    public TournamentServiceImpl(TournamentJpaRepository tournamentJpaRepository) {
        this.tournamentJpaRepository = tournamentJpaRepository;
    }

    @Override
    public Tournament findById(Long id) {
        Optional<Tournament> task = tournamentJpaRepository.findById(id);
        return task.orElse(null);
    }

    @Override
    public List<Tournament> getAll() {
        return tournamentJpaRepository.findAll();
    }

    @Override
    public void save(Tournament tournament) {
        tournamentJpaRepository.saveAndFlush(tournament);
    }
}
