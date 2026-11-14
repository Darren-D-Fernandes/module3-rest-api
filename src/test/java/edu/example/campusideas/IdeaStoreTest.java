package edu.example.campusideas;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;
// True unit tests: no Spring context, HTTP client, or server is involved.
class IdeaStoreTest {

    @TempDir 
    Path tempDir;


    @Test
    void freshRepositoryStartsEmpty() throws Exception {
        Path db = tempDir.resolve("empty-ideas.db");
        IdeaRepository repo = new IdeaRepository(db);
        assertEquals(0, repo.findAll().size());
    }

    //As we are using db now, and it will start empty - removing this test
    /*@Test void freshStoreContainsTwoSeedIdeas() {
        IdeaStore store = new IdeaStore();
        assertEquals(2, store.findAll().size());
    }*/

    @Test 
    void addingIdeaStoresItsTitleAndCategory() throws Exception{
        // Arrange: construct a fresh store and an IdeaRequest.
        Path db = tempDir.resolve("add-ideas.db");

        IdeaRepository repo = new IdeaRepository(db);
        Idea savedIdea = repo.create("Start a computer science club", "clubs");


       

        // Assert: verify returned title/category and that findAll contains the idea.
        assertNotNull(savedIdea);
        assertEquals("Start a computer science club", savedIdea.title());
        assertEquals("clubs", savedIdea.category());
        assertTrue(repo.findAll().contains(savedIdea));
        
    }

    @Test 
    void twoNewIdeasReceiveDifferentIds() throws Exception {
        // Arrange: use a fresh store.
        Path db = tempDir.resolve("ids-idea.db");
        IdeaRepository repo = new IdeaRepository(db);
        Idea idea1 = repo.create("Career Fare", "events");
        Idea idea2 = repo.create("Sports Club", "clubs");
        
        // Act: add two requests.
        

        // Assert: ids differ; neither reuses a seed id; list size grows to four.
        assertNotEquals(idea1.id(), idea2.id());
        assertEquals(2, repo.findAll().size());
    }

    @Test
    void savesAcrossRepositoryRecreation() throws Exception {
        Path db = tempDir.resolve("test-ideas.db");
        IdeaRepository first = new IdeaRepository(db);
        Idea created = first.create("Test club", "clubs");

        IdeaRepository reopened = new IdeaRepository(db);
        assertTrue(reopened.findAll().stream()
            .anyMatch(x -> x.id() == created.id()
                && x.title().equals("Test club")
                && x.category().equals("clubs")));
    }
}
