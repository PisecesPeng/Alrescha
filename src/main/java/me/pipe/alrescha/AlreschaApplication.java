package me.pipe.alrescha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AlreschaApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlreschaApplication.class, args);
    }
}
