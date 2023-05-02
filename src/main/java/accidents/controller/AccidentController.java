package accidents.controller;

import accidents.model.Accident;
import accidents.service.AccidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {

    private final AccidentService accidentService;

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        accidentService.findAll();
        return "/accidents/accidents";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("accident", new Accident());
        return "/accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        String rsl = "redirect:/accidents";
        if (!accidentService.create(accident)) {
            rsl = "/createFail";
        }
        return rsl;
    }

    @GetMapping("/edit/{id}")
    public String formUpdateTask(Model model, @PathVariable("id") int id) {
        Optional<Accident> accidentInDB = accidentService.findById(id);
        accidentInDB.ifPresent(accident -> model.addAttribute("accident", accident));
        return "/accidents/editAccident";
    }

    @PostMapping("/update")
    public String editTask(@ModelAttribute Accident accident) {
        String rsl = "redirect:/accidents";
        if (!accidentService.update(accident)) {
            rsl = "/editFail";
        }
        return rsl;
    }
}
