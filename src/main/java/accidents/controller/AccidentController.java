package accidents.controller;

import accidents.model.Accident;
import accidents.model.AccidentType;
import accidents.model.Rule;
import accidents.service.AccidentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        return "/accidents/accidents";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> types = new ArrayList<>();
        types.add(new AccidentType(1, "Две машины"));
        types.add(new AccidentType(2, "Машина и человек"));
        types.add(new AccidentType(3, "Машина и велосипед"));
        model.addAttribute("types", types);
        List<Rule> rules = List.of(
                new Rule(1, "Статья. 1"),
                new Rule(2, "Статья. 2"),
                new Rule(3, "Статья. 3")
        );
        model.addAttribute("rules", rules);
        model.addAttribute("accident", new Accident());
        return "/accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String rsl = "redirect:/accidents";
        if (!accidentService.save(accident)) {
            String[] ids = req.getParameterValues("rIds");
            rsl = "/createFail";
        }
        return rsl;
    }

    @GetMapping("/edit/{id}")
    public String formUpdateTask(Model model, @PathVariable("id") int id) {
        String rsl = "/accidents/editAccident";
        Optional<Accident> accidentInDB = accidentService.findById(id);
        if (!accidentInDB.isEmpty()) {
            model.addAttribute("accident", accidentInDB.get());
        } else {
            rsl = "/editFail";
        }
        return rsl;
    }

    @PostMapping("/update")
    public String editTask(@ModelAttribute Accident accident) {
        String rsl = "redirect:/accidents";
        if (!accidentService.update(accident)) {
            rsl = "/editFail";
        }
        return rsl;
    }

    @GetMapping("/formUpdateAccident")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidentService.findById(id).get());
        return "accident/update";
    }
}
