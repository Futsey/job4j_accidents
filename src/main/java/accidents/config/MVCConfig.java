package accidents.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MVCConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/assets/bootstrap/css/**")
                .addResourceLocations("classpath:/static/assets/bootstrap/css/");
        registry.addResourceHandler("/static/assets/bootstrap/js/**")
                .addResourceLocations("classpath:/static/assets/bootstrap/js/");
        registry.addResourceHandler("/static/assets/fonts/**")
                .addResourceLocations("classpath:/templates/assets/bootstrap/fonts/");
        registry.addResourceHandler("/static/assets/img/**")
                .addResourceLocations("classpath:/static/assets/img/");
    }
}
