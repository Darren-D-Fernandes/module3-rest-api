package edu.example.campusideas;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.fail;
// API/component test: exercises routing and JSON through MockMvc, without a live network server.
@SpringBootTest
@AutoConfigureMockMvc
class IdeaApiTest {
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
}
