package accidents.controller;

import accidents.service.AccidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;

    @GetMapping("/accidents")
    public String index(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        System.out.println(accidentService.findAll());
        return "index";
    }
}
