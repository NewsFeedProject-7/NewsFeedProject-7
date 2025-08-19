package org.example.newsfeedproejct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NewsFeedProejctApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsFeedProejctApplication.class, args);
    }

}
