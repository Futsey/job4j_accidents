package accidents.service;

import accidents.model.User;
import accidents.repository.security.AuthorityRepository;
import accidents.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder encoder;
    private final UserRepository users;
    private final AuthorityRepository authorities;

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class.getName());

    public List<User> findAllSData() {
        return users.findAll();
    }

    public Optional<User> findByIdSData(int accidentId) {
        Optional<User> nonNullUser = users.findById(accidentId);
        if (nonNullUser.isPresent()) {
            LOG.info("User was found successfully");
        } else {
            LOG.error("User wasn`t found. Empty user was returned");
        }
        return nonNullUser;
    }

    public Optional<User> saveSData(User user) {
        Optional<User> rsl = Optional.empty();
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        try {
            users.save(user);
            rsl = Optional.of(user);
            LOG.info("User was saved successfully");
        } catch (Exception e) {
            LOG.error("User wasn`t saved. Exception: " + e, e);
        }
        return rsl;
    }


    public boolean findByName(String name) {
        return users.findByName(name);
    }
}
