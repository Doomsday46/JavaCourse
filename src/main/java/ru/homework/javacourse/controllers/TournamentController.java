package ru.homework.javacourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.homework.javacourse.models.Location;
import ru.homework.javacourse.models.Player;
import ru.homework.javacourse.models.Tournament;
import ru.homework.javacourse.models.User;
import ru.homework.javacourse.services.LocationService;
import ru.homework.javacourse.services.PlayerService;
import ru.homework.javacourse.services.TournamentService;
import ru.homework.javacourse.services.UserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
class TournamentController {

    private final TournamentService tournamentService;
    private final PlayerService playerService;
    private final LocationService locationService;
    private final UserService userService;

    @Autowired
    public TournamentController(TournamentService tournamentService, UserService userService,PlayerService playerService, LocationService locationService) {
        this.tournamentService = tournamentService;
        this.userService = userService;
        this.playerService = playerService;
        this.locationService = locationService;

        Tournament tournament = new Tournament();
        tournament.setName("TennisTest");
        tournament.setStartTournament(new Date(2019,01,01));
        tournament.setEndTournament(new Date(2019,01,9));

        tournamentService.save(tournament);
    }

    @RequestMapping(value = {"/tournaments"}, method = RequestMethod.GET)
    public String listOfTournaments(Model model) {
        List<Tournament> tournaments = tournamentService.getAll();
        model.addAttribute("tournaments", tournaments);
        return "tournaments/tournaments_list";
    }

    @RequestMapping(value = {"/tournaments/{id}/edit"}, method = RequestMethod.GET)
    public String getEditTournamentForm(Model model, @PathVariable("id") Long id) {
        Tournament tournament = tournamentService.findById(id);
        model.addAttribute("create", false);
        model.addAttribute("tournament", tournament);
        model.addAttribute("locations",tournament.getLocations());
        model.addAttribute("players",tournament.getPlayers());
        return "tournaments/tournaments_edit";
    }

    @RequestMapping(value = {"/tournaments/{id}/edit"}, method = RequestMethod.POST)
    public String saveTournament(@ModelAttribute("tournament") Tournament tournament, BindingResult bindingResult, Model model, @PathVariable("id") Long id) {
        tournament.setIdTournament(id);
        Tournament existingTournament = tournamentService.findById(id);
        tournament.setName(existingTournament.getName());
        tournament.setLocations(existingTournament.getLocations());
        tournament.setPlayers(existingTournament.getPlayers());
        tournament.setStartTournament(existingTournament.getStartTournament());
        tournament.setEndTournament(existingTournament.getEndTournament());
        tournament.setAssignedTo(existingTournament.getAssignedTo());
        tournamentService.save(tournament);
        return "redirect:/tournaments";
    }

    @RequestMapping(value = {"/tournaments/new"}, method = RequestMethod.GET)
    public String getNewTournamentForm(Model model) {
        model.addAttribute("create", true);
        model.addAttribute("tournament", new Tournament());
        return "tournaments/tournaments_edit";
    }

    @RequestMapping(value = {"/tournaments/new"}, method = RequestMethod.POST)
    public String saveTask(@ModelAttribute("tournament") Tournament tournament, BindingResult bindingResult, Model model) {
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
        return "tournaments_edit_assignedTo";
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

    @RequestMapping(value = {"/tournaments/{id}/delete"}, method = RequestMethod.GET)
    public String deleteTournament(@PathVariable("id") Long id) {
        tournamentService.delete(id);
        return "redirect:/tournaments";
    }

    @RequestMapping(value = {"/tournaments/{id}/players"}, method = RequestMethod.GET)
    public String listOfPlayerForTournament(Model model, @PathVariable("id") Long id) {
        Set<Player> players = tournamentService.findById(id).getPlayers();
        model.addAttribute("players", players);
        return "players/player_list";
    }

    @RequestMapping(value = {"/tournaments/{id}/locations"}, method = RequestMethod.GET)
    public String listOfLocationsForTournament(Model model, @PathVariable("id") Long id) {
        Set<Location> locations = tournamentService.findById(id).getLocations();
        model.addAttribute("locations", locations);
        return "locations/location_list";
    }

    @RequestMapping(value = {"/tournaments/{id}/editPlayers"}, method = RequestMethod.GET)
    public String editPlayerForTournament(Model model, @PathVariable("id") Long id) {
        Tournament tournament = tournamentService.findById(id);
        model.addAttribute("tournament", tournament);
        model.addAttribute("players", playerService.getAll());
        return "tournaments/tournaments_edit_players";
    }

    @RequestMapping(value = {"/tournaments/{id}/{idTournament}/editPlayers/add"}, method = RequestMethod.GET)
    public String savePlayerForTournament(Model model, @PathVariable("id") Long id,@PathVariable("idTournament") Long idTournament) {
        Tournament _tournament = tournamentService.findById(idTournament);



        Set<Player> players = _tournament.getPlayers();
        Player player = playerService.findById(id);
        player.setTournament(_tournament);

        players.add(player);
        _tournament.setPlayers(players);
        tournamentService.save(_tournament);

        model.addAttribute("tournament", _tournament);
        model.addAttribute("players", playerService.getAll());

        return "tournaments/tournaments_edit_players";
    }

    @RequestMapping(value = {"/tournaments/{id}/{idTournament}/editPlayers/delete"}, method = RequestMethod.GET)
    public String deletePlayerForTournament(Model model, @PathVariable("id") Long id,@PathVariable("idTournament") Long idTournament) {
        Tournament _tournament = tournamentService.findById(idTournament);

        Set<Player> players = _tournament.getPlayers();
        Player player = playerService.findById(id);

        players.remove(player);

        _tournament.setPlayers(players);
        tournamentService.save(_tournament);

        player.setTournament(null);

        playerService.save(player);

        model.addAttribute("tournament", _tournament);
        model.addAttribute("players", playerService.getAll());

        return "tournaments/tournaments_edit_players";
    }

    @RequestMapping(value = {"/tournaments/{id}/editLocations"}, method = RequestMethod.GET)
    public String editLocationForTournament(Model model, @PathVariable("id") Long id) {
        Tournament tournament = tournamentService.findById(id);
        model.addAttribute("tournament", tournament);
        model.addAttribute("locations", locationService.getAll());
        return "tournaments/tournaments_edit_locations";
    }

    @RequestMapping(value = {"/tournaments/{id}/{idTournament}/editLocations/add"}, method = RequestMethod.GET)
    public String saveLocationForTournament(Model model, @PathVariable("id") Long id,@PathVariable("idTournament") Long idTournament) {
        Tournament _tournament = tournamentService.findById(idTournament);

        Set<Location> locations = _tournament.getLocations();
        Location location = locationService.findById(id);
        location.setTournament(_tournament);

        locations.add(location);
        _tournament.setLocations(locations);
        tournamentService.save(_tournament);

        model.addAttribute("tournament", _tournament);
        model.addAttribute("locations", locationService.getAll());

        return "tournaments/tournaments_edit_locations";
    }

    @RequestMapping(value = {"/tournaments/{id}/{idTournament}/editLocations/delete"}, method = RequestMethod.GET)
    public String deleteLocationForTournament(Model model, @PathVariable("id") Long id,@PathVariable("idTournament") Long idTournament) {
        Tournament _tournament = tournamentService.findById(idTournament);

        Set<Location> locations = _tournament.getLocations();
        Location location = locationService.findById(id);

        locations.remove(location);

        _tournament.setLocations(locations);
        tournamentService.save(_tournament);

        location.setTournament(null);

        locationService.save(location);

        model.addAttribute("tournament", _tournament);
        model.addAttribute("locations", locationService.getAll());

        return "tournaments/tournaments_edit_locations";
    }

}