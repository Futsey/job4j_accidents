package accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "index";
    }
}
