package accidents.controller;

import accidents.model.User;
import accidents.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class RegControl {

    private final UserService userService;

    @GetMapping("/registration")
    public String regPage(@RequestParam(value = "login", required = false) String login, Model model) {
        String ifEmptyName = "Field name must be not empty";
        if (login != null) {
            model.addAttribute("errorMessage", ifEmptyName);
        }
        return "/users/reg";
    }

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        String ifUserExist = "User already exists";
        if (userService.findByName(user.getName())) {
            model.addAttribute("errorMessage", ifUserExist);
        } else {
            userService.saveSData(user);
        }
        return "redirect:users/login";
    }
}
