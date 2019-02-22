package ru.homework.javacourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.homework.javacourse.models.Tournament;
import ru.homework.javacourse.models.User;
import ru.homework.javacourse.services.TournamentService;
import ru.homework.javacourse.services.UserService;
import ru.homework.javacourse.services.implement.TournamentServiceImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
class TournamentController {

    private final TournamentService tournamentService;
    private final UserService userService;

    @Autowired
    public TournamentController(TournamentService tournamentService, UserService userService) {
        this.tournamentService = tournamentService;
        this.userService = userService;
    }

    @RequestMapping(value = {"/tournaments"}, method = RequestMethod.GET)
    public String listOfTournaments(Model model) {
        List<Tournament> tasks = tournamentService.getAll();
        model.addAttribute("tournaments", tasks);
        return "tournaments/tournaments_list";
    }

    @RequestMapping(value = {"/tournaments/{id}/edit"}, method = RequestMethod.GET)
    public String getEditTournamentForm(Model model, @PathVariable("id") Long id) {
        Tournament task = tournamentService.findById(id);
        model.addAttribute("create", false);
        model.addAttribute("tournament", task);
        return "tournaments/tournament_edit";
    }

    @RequestMapping(value = {"/tournaments/{id}/edit"}, method = RequestMethod.POST)
    public String saveTournament(@ModelAttribute("task") Tournament tournament, BindingResult bindingResult, Model model, @PathVariable("id") Long id) {
        tournament.setIdTournament(id);
        Tournament existingTournament = tournamentService.findById(id);
        tournament.setBeginDate(existingTournament.getBeginDate());
        tournament.setName(existingTournament.getName());
        tournamentService.save(tournament);
        return "redirect:/tournaments";
    }

    @RequestMapping(value = {"/tournaments/new"}, method = RequestMethod.GET)
    public String getNewTournamentForm(Model model) {
        model.addAttribute("create", true);
        model.addAttribute("tournament", new Tournament());
        return "tournaments/tournament_edit";
    }

    @RequestMapping(value = {"/tournaments/new"}, method = RequestMethod.POST)
    public String saveTask(@ModelAttribute("task") Tournament tournament, BindingResult bindingResult, Model model) {
        tournamentService.save(tournament);
        return "redirect:/tournaments";
    }

    @RequestMapping(value = {"/tournaments/{id}/editAssignedTo"}, method = RequestMethod.GET)
    public String getEditAssignedToForm(Model model, @PathVariable("id") Long id) {
        Tournament tournament = tournamentService.findById(id);
        List<User> users = userService.getAll();
        Map<Long, User> values = users.stream().collect(Collectors.toMap(
                User::getId,
                Function.identity(),
                (e1, e2) -> e2,
                LinkedHashMap::new
        ));
        Long selectedId = tournament.getAssignedTo() != null ? tournament.getAssignedTo().getId() : null;
        int selectedIndex = -1;
        int i = 0;
        for (User user : users) {
            if (Objects.equals(user.getId(), selectedId)) {
                selectedIndex = i;
            }
            i++;
        }
        model.addAttribute("create", false);
        model.addAttribute("nullable", true);
        model.addAttribute("tournament", tournament);
        model.addAttribute("values", values);
        model.addAttribute("checkedIndex", selectedIndex);
        return "tournaments/tournament_edit_assignedTo";
    }

    @RequestMapping(value = {"/tournaments/{id}/editAssignedTo"}, method = RequestMethod.POST)
    public String saveTournament(Model model, @PathVariable("id") Long id, @RequestParam("radioAssignedTo") Long assignedToId) {
        Tournament tournament = tournamentService.findById(id);

        if (assignedToId != null) {
            User user = userService.findById(assignedToId);
            tournament.setAssignedTo(user);
        } else {
            tournament.setAssignedTo(null);
        }
        tournamentService.save(tournament);

        return "redirect:/tournaments";
    }

}