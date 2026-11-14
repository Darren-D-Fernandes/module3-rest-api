package edu.example.campusideas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Disabled;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

// API/component test: exercises routing and JSON through MockMvc, without a live network server.
@SpringBootTest
@AutoConfigureMockMvc
class IdeaApiTest {

    @TempDir 
    static Path tempDir;

    @DynamicPropertySource 
    static void registerDbPath(DynamicPropertyRegistry registry){
        registry.add("app.db-path", () -> tempDir.resolve("api-test.db").toString());
    }

    @Autowired private MockMvc mvc;

    @Test
    void validPostReturnsCreatedIdea() throws Exception {
        // Use mvc.perform(post("/api/ideas").contentType(MediaType.APPLICATION_JSON)
        //     .content(...)) and check status().isCreated(), $.id, $.title, $.category.
        // Do not assert an exact id; another test may have created an idea.
        mvc.perform(post("/api/ideas").contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"Start a robotics club\",\"category\":\"clubs\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.title").value("Start a robotics club"))
            .andExpect(jsonPath("$.category").value("clubs"));

        
    }


    @Test
    void spacesOnlyTitleReturnsBadRequest() throws Exception {
        mvc.perform(post("/api/ideas").contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"   \",\"category\":\"clubs\"}"))
            .andExpect(status().isBadRequest());
    }
}
