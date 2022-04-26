package com.follydev.gestiondestock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GestiondestockApiApplication {

    public static void main(String[] args) {
        
        SpringApplication.run(GestiondestockApiApplication.class, args);
    }

}
