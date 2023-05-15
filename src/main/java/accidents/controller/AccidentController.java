package accidents.controller;

import accidents.model.Accident;
import accidents.model.AccidentType;
import accidents.model.Rule;
import accidents.service.AccidentService;
import accidents.service.AccidentTypeService;
import accidents.service.AccidentRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {

    private final AccidentService accidentService;
    private final AccidentRuleService accidentRuleService;
    private final AccidentTypeService accidentTypeService;

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("accidents", accidentService.findAllJDBC());
        return "/accidents/accidents";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> accidentTypes = accidentTypeService.findAllWithJDBC();
        model.addAttribute("accidentTypes", accidentTypes);
        List<Rule> rules = accidentRuleService.findAllRulesJDBC();
        model.addAttribute("rules", rules);
        return "/accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, Model model, @RequestParam("rIds") int[] ids) {
        String rsl = "redirect:/accidents";
        if (!accidentService.saveJDBC(accident, ids)) {
            model.addAttribute("message", "Sorry, can`t create accident. Something went wrong");
            rsl = "/accidents/fail";
        }
        return rsl;
    }

    /**TODO INFO PAGE
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public String formAccidentInfo(Model model, @PathVariable("id") int id) {
        Optional<Accident> accidentInMem = accidentService.findByIdWithJDBC(id);
        accidentInMem.ifPresent(accident -> model.addAttribute("accident", accident));
        return "/accidents/TODO_INFOPAGE";
    }

    /**
    @GetMapping("/edit")
    public String formUpdateAccident(Model model, @RequestParam("id") int id) {
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
    public String editAccident(@ModelAttribute Accident accident) {
        String rsl = "redirect:/accidents";
        if (!accidentService.update(accident)) {
            rsl = "/editFail";
        }
        return rsl;
    }

     */


    /** IN MEMORY CONTROLLER

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("accidents", accidentService.findAll());;
        return "/accidents/accidents";
    }

    @GetMapping("/info/{id}")
    public String formTaskInfo(Model model, @PathVariable("id") int id) {
        Optional<Accident> accidentInMem = accidentService.findById(id);
        accidentInMem.ifPresent(accident -> model.addAttribute("accident", accident));
        return "/accidents/info";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> accidentTypes = accidentTypeService.findAll();
        model.addAttribute("accidentTypes", accidentTypes);
        List<Rule> rules = ruleService.findAllRules();
        model.addAttribute("rules", rules);
        model.addAttribute("accident", new Accident());
        return "/accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req, Model model) {
        String rsl = "redirect:/accidents";
        String[] ids = req.getParameterValues("rIds");
        Optional<Accident> optAcc = accidentService.save(accident, ids);
        if (optAcc.isEmpty()) {
            model.addAttribute("message", "Sorry, can`t create accident. Something went wrong");
            rsl = "/accidents/fail";
        }
        return rsl;
    }

    @GetMapping("/edit")
    public String formUpdateAccident(Model model, @RequestParam("id") int id) {
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
    public String editAccident(@ModelAttribute Accident accident) {
        String rsl = "redirect:/accidents";
        if (!accidentService.update(accident)) {
            rsl = "/editFail";
        }
        return rsl;
    }
    */
}
