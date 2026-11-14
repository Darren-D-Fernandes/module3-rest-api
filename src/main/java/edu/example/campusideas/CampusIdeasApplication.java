package edu.example.campusideas;


import java.nio.file.Path;
import java.io.IOException;
import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CampusIdeasApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusIdeasApplication.class, args);
    }

    @Bean
    IdeaRepository ideaRepository(@Value("${app.db-path}")String path)
            throws IOException, SQLException{
                return new IdeaRepository(Path.of(path));
            }
}

