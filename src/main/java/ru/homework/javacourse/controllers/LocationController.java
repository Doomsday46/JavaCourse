package ru.homework.javacourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.homework.javacourse.data.LocationJpaRepository;
import ru.homework.javacourse.data.UserRepository;
import ru.homework.javacourse.models.Location;
import ru.homework.javacourse.models.Player;
import ru.homework.javacourse.models.User;
import ru.homework.javacourse.services.LocationService;
import ru.homework.javacourse.services.TemplateHelper;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class LocationController {

    private final LocationService locationService;
    private final UserRepository userService;
    private final TemplateHelper templateHelper;

    @Autowired
    public LocationController(LocationService locationService, UserRepository userService, TemplateHelper templateHelper) {
        this.locationService = locationService;
        this.userService = userService;
        this.templateHelper = templateHelper;
    }

    @RequestMapping(value = {"/locations"}, method = RequestMethod.GET)
    public String listOfLocations(Model model) {
        List<Location> locations = locationService.getAll();

        User user = userService.findByUsername(templateHelper.getUsername());

        Set<Location> _locations = new HashSet<>();
        for (Location location:locations
        ) {
            if(location != null && location.getAssignedTo() != null && location.getAssignedTo().equals(user)) _locations.add(location);
        }

        model.addAttribute("locations", locations);
        return "locations/locations_list";
    }

    @RequestMapping(value = {"/locations/{id}/edit"}, method = RequestMethod.GET)
    public String getEditLocationForm(Model model, @PathVariable("id") Long id) {
        Location location = locationService.findById(id);
        model.addAttribute("create", false);
        model.addAttribute("location", location);
        model.addAttribute("readonly",false);
        return "locations/locations_edit";
    }

    @RequestMapping(value = {"/locations/{id}/edit"}, method = RequestMethod.POST)
    public String saveLocation(@ModelAttribute("Location") Location location, BindingResult bindingResult, Model model, @PathVariable("id") Long id) {
        location.setIdLocation(id);
        Location existingPlayer = locationService.findById(id);
        locationService.save(location);
        return "redirect:/locations";
    }


    @RequestMapping(value = {"/locations/new"}, method = RequestMethod.GET)
    public String getNewLocationForm(Model model) {
        model.addAttribute("create", true);
        model.addAttribute("location", new Location());

        return "locations/locations_edit";
    }

    @RequestMapping(value = {"/locations/new"}, method = RequestMethod.POST)
    public String saveLocation(@ModelAttribute("Location") Location location, BindingResult bindingResult, Model model) {
        User user = userService.findByUsername(templateHelper.getUsername());
        location.setAssignedTo(user);
        locationService.save(location);
        return "redirect:/locations";
    }

    @RequestMapping(value = {"/locations/{id}/delete"}, method = RequestMethod.GET)
    public String deleteLocation(@PathVariable("id") Long id) {
        locationService.delete(id);
        return "redirect:/locations";
    }
}
