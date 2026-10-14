package edu.example.campusideas;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;
// True unit tests: no Spring context, HTTP client, or server is involved.
class IdeaStoreTest {
    @Test void freshStoreContainsTwoSeedIdeas() {
        IdeaStore store = new IdeaStore();
        assertEquals(2, store.findAll().size());
    }
    @Test 
    void addingIdeaStoresItsTitleAndCategory() {
        // Arrange: construct a fresh store and an IdeaRequest.
        IdeaStore store = new IdeaStore();
        IdeaRequest request = new IdeaRequest("Start a computer science club", "clubs");

        // Act: call add(request).
        Idea savedIdea = store.add(request);

        // Assert: verify returned title/category and that findAll contains the idea.
        assertNotNull(savedIdea);
        assertEquals("Start a computer science club", savedIdea.title());
        assertEquals("clubs", savedIdea.category());
        assertTrue(store.findAll().contains(savedIdea));
        
    }
    @Test 
    void twoNewIdeasReceiveDifferentIds() {
        // Arrange: use a fresh store.
        IdeaStore store = new IdeaStore();
        IdeaRequest request1 = new IdeaRequest("Career Fare", "events");
        IdeaRequest request2 = new IdeaRequest("Sports Club", "clubs");
        
        // Act: add two requests.
        Idea idea1 = store.add(request1);
        Idea idea2 = store.add(request2);

        // Assert: ids differ; neither reuses a seed id; list size grows to four.
        assertNotEquals(idea1.id(), idea2.id());
        assertEquals(4, store.findAll().size());
    }
}
