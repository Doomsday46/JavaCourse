package ru.homework.javacourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.homework.javacourse.models.Player;
import ru.homework.javacourse.models.User;
import ru.homework.javacourse.services.PlayerService;
import ru.homework.javacourse.services.TemplateHelper;
import ru.homework.javacourse.services.TournamentService;
import ru.homework.javacourse.services.UserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class PlayerController {

    private final PlayerService playerService;
    private final TournamentService tournamentService;
    private final UserService userService;
    private final TemplateHelper templateHelper;

    @Autowired
    public PlayerController(TemplateHelper templateHelper, PlayerService playerService, UserService userService, TournamentService tournamentService) {
        this.playerService = playerService;
        this.userService = userService;
        this.tournamentService = tournamentService;
        this.templateHelper = templateHelper;

    }

    @RequestMapping(value = {"/players"}, method = RequestMethod.GET)
    public String listOfPlayers(Model model) {
        List<Player> players = playerService.getAll();

        User user = userService.findByUsername(templateHelper.getUsername());

        Set<Player> _players = new HashSet<>();
        for (Player player:players
        ) {
            if(player != null && player.getAssignedTo() != null && player.getAssignedTo().equals(user)) _players.add(player);
        }

        model.addAttribute("players", _players);
        return "players/player_list";
    }

    @RequestMapping(value = {"/players/{id}/edit"}, method = RequestMethod.GET)
    public String getEditPlayerForm(Model model, @PathVariable("id") Long id) {
        Player player = playerService.findById(id);
        model.addAttribute("create", false);
        model.addAttribute("player", player);
        return "players/player_edit";
    }

    @RequestMapping(value = {"/players/{id}/edit"}, method = RequestMethod.POST)
    public String savePlayer(@ModelAttribute("player") Player player, BindingResult bindingResult, Model model, @PathVariable("id") Long id) {
        player.setIdPlayer(id);
        Player existingPlayer = playerService.findById(id);
        playerService.save(player);
        return "redirect:/players";
    }


    @RequestMapping(value = {"/players/new"}, method = RequestMethod.GET)
    public String getNewTaskForm(Model model) {
        model.addAttribute("create", true);
        model.addAttribute("player", new Player());
        return "players/player_edit";
    }

    @RequestMapping(value = {"/players/new"}, method = RequestMethod.POST)
    public String saveTask(@ModelAttribute("player") Player player, BindingResult bindingResult, Model model) {
        User user = userService.findByUsername(templateHelper.getUsername());
        player.setAssignedTo(user);
        playerService.save(player);
        return "redirect:/players";
    }

    @RequestMapping(value = {"/players/{id}/delete"}, method = RequestMethod.GET)
    public String deletePlayer(@PathVariable("id") Long id) {
        playerService.delete(id);
        return "redirect:/players";
    }

}
