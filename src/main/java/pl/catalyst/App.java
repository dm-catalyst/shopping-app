package pl.catalyst;
/**
 * @Author Dawid Miłoń
 * @Email dawid.milon@protonmail.com
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.regex.Pattern;

@SpringBootApplication
@EnableAsync
@Slf4j
public class App {
    public static void main(String[] args) {
        try {
            SpringApplication.run(App.class, args);
            log.info("============================================SHOPPING APP================================================");
        } catch (Exception e) {
            throw new RuntimeException("Shopping App exception: " + e.getMessage());
        }
    }
}
