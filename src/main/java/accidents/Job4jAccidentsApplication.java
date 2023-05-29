package accidents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Job4jAccidentsApplication {

    private static void encoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("secret");
        System.out.println(pwd);
    }

    public static void main(String[] args) {
        encoder();
        SpringApplication.run(Job4jAccidentsApplication.class, args);
        System.out.println("http://localhost:8080/index");
    }
}
