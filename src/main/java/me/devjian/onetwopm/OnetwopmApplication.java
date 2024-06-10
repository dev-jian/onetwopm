package me.devjian.onetwopm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OnetwopmApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnetwopmApplication.class, args);
    }
}
