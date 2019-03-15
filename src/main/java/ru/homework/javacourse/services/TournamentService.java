package ru.homework.javacourse.services;

import ru.homework.javacourse.models.Tournament;

import java.util.List;

public interface TournamentService {
        Tournament findById(Long id);
        List<Tournament> getAll();
        void save(Tournament tournament);
        void delete(Long id);
}
