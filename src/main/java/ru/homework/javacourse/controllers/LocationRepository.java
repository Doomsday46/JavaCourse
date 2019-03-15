package ru.homework.javacourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.homework.javacourse.data.LocationJpaRepository;
import ru.homework.javacourse.models.Location;
import ru.homework.javacourse.models.Player;
import ru.homework.javacourse.models.User;
import ru.homework.javacourse.services.LocationService;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class LocationRepository {

    private final LocationService locationService;

    @Autowired
    public LocationRepository(LocationService locationService) {
        this.locationService = locationService;

        Location location = new Location();

        location.setState(false);
        location.setName( "tabel");
        location.setDescription("this is table");

        locationService.save(location);
    }

    @RequestMapping(value = {"/locations"}, method = RequestMethod.GET)
    public String listOfPlayers(Model model) {
        List<Location> locations = locationService.getAll();
        model.addAttribute("locations", locations);
        return "locations/locations_list";
    }

    @RequestMapping(value = {"/locations/{id}/edit"}, method = RequestMethod.GET)
    public String getEditPlayerForm(Model model, @PathVariable("id") Long id) {
        Location location = locationService.findById(id);
        model.addAttribute("create", false);
        model.addAttribute("location", location);
        return "locations/locations_edit";
    }

    @RequestMapping(value = {"/locations/{id}/edit"}, method = RequestMethod.POST)
    public String savePlayer(@ModelAttribute("Location") Location location, BindingResult bindingResult, Model model, @PathVariable("id") Long id) {
        location.setIdLocation(id);
        Location existingPlayer = locationService.findById(id);
        locationService.save(location);
        return "redirect:/locations";
    }


    @RequestMapping(value = {"/locations/new"}, method = RequestMethod.GET)
    public String getNewTaskForm(Model model) {
        model.addAttribute("create", true);
        model.addAttribute("player", new Player());
        return "locations/locations_edit";
    }

    @RequestMapping(value = {"/locations/new"}, method = RequestMethod.POST)
    public String saveTask(@ModelAttribute("Location") Location location, BindingResult bindingResult, Model model) {
        locationService.save(location);
        return "redirect:/locations";
    }

}
