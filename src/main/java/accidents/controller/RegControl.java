package accidents.controller;

import accidents.model.User;
import accidents.repository.security.AuthorityRepository;
import accidents.repository.security.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class RegControl {

    private final PasswordEncoder encoder;
    private final UserRepository users;
    private final AuthorityRepository authorities;

    public RegControl(PasswordEncoder encoder, UserRepository users, AuthorityRepository authorities) {
        this.encoder = encoder;
        this.users = users;
        this.authorities = authorities;
    }

    @GetMapping("/registration")
    public String regPage() {
        return "users/reg";
    }

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        users.save(user);
        System.out.println(users.findAll());
        return "redirect:users/login";
    }
}
