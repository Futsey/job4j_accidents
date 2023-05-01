package accidents.controller;

import accidents.service.AccidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final AccidentService accidentService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("user", "Andrew Petrushin");
        model.addAttribute("accidents", accidentService.findAllInTestList());
        System.out.println(accidentService.findAllInTestList());
        return "index";
    }
}
