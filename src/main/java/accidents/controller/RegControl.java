package accidents.controller;

import accidents.model.User;
import accidents.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class RegControl {

    private final UserService userService;

    @GetMapping("/registration")

    public String regPage(Model model, @RequestParam(name = "errorMessage", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "users/reg";
    }

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        Optional<User> nonNullUser = userService.saveSData(user);
        if (nonNullUser.isEmpty() || user.getName().equals("")) {
            return "redirect:/registration?error=true";
        }
        return "redirect:users/login";
    }
}
