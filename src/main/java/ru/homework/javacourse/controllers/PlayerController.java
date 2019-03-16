package ru.homework.javacourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.homework.javacourse.models.Player;
import ru.homework.javacourse.models.User;
import ru.homework.javacourse.services.PlayerService;
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

    @Autowired
    public PlayerController(PlayerService playerService, UserService userService, TournamentService tournamentService) {
        this.playerService = playerService;
        this.userService = userService;
        this.tournamentService = tournamentService;

        Set<Player> players = new HashSet<>();

        for (int i = 1; i < 7; i++) {
            Player player = new Player();
            player.setFirstName("FirsNameTest" + i);
            player.setSecondName("SecondNameTest" + i);
            player.setAge(new Date(1997,1,1));
            players.add(player);
        }
        for (Player player:
        players) {
            playerService.save(player);
        }

    }

    @RequestMapping(value = {"/players"}, method = RequestMethod.GET)
    public String listOfPlayers(Model model) {
        List<Player> players = playerService.getAll();
        model.addAttribute("players", players);
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
        playerService.save(player);
        return "redirect:/players";
    }

    @RequestMapping(value = {"/players/{id}/editAssignedTo"}, method = RequestMethod.GET)
    public String getEditAssignedToForm(Model model, @PathVariable("id") Long id) {
        Player player = playerService.findById(id);

        List<User> users = userService.getAll();
        Map<Long, User> values = users.stream().collect(Collectors.toMap(
                User::getId,
                Function.identity(),
                (e1, e2) -> e2,
                LinkedHashMap::new
        ));
        Long selectedId = player.getAssignedTo() != null ? player.getAssignedTo().getId() : null;
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
        model.addAttribute("player", player);
        model.addAttribute("values", values);
        model.addAttribute("checkedIndex", selectedIndex);
        return "players/player_edit_assignedTo";
    }

    @RequestMapping(value = {"/players/{id}/editAssignedTo"}, method = RequestMethod.POST)
    public String saveTask(Model model, @PathVariable("id") Long id, @RequestParam("radioAssignedTo") Long assignedToId) {
        Player player = playerService.findById(id);

        if (assignedToId != null) {
            User user = userService.findById(assignedToId);
            player.setAssignedTo(user);
        } else {
            player.setAssignedTo(null);
        }
        playerService.save(player);

        return "redirect:/players";
    }
}
