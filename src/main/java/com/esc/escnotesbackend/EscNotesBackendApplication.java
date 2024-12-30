package com.esc.escnotesbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class EscNotesBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EscNotesBackendApplication.class, args);
    }

}
