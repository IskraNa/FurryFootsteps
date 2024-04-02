package org.example.furryfootstepsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class FurryFootstepsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(FurryFootstepsApiApplication.class, args);
    }
}
