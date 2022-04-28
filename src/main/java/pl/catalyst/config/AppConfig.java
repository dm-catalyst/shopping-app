package pl.catalyst.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan("pl.catalyst")
public class AppConfig {
    @Bean
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }
}
