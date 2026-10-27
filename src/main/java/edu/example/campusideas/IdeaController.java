package edu.example.campusideas;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.CrossOrigin;       //added for CORS
import org.springframework.web.bind.annotation.RequestMethod;     //addes for CORS

@CrossOrigin(
    origins = "http://localhost:5173",
    methods = {RequestMethod.GET, RequestMethod.POST},
    allowedHeaders = "Content-Type"
)
@RestController
@RequestMapping("/api/ideas")
public class IdeaController {
    private final IdeaStore store;
    public IdeaController(IdeaStore store) {
        this.store = store;
    }
    // TODO 2: Add the HTTP mapping for reading ideas.
    //Done
    @GetMapping 
    public List<Idea> getIdeas() {
        // TODO 3: Replace the placeholder with the store's read method.
        //Done
        return store.findAll();
    }
    // TODO 4: Add the HTTP mapping for creating an idea.
    //Done
    @PostMapping 
    public ResponseEntity<Idea> createIdea(/* TODO 5: Add @Valid and @RequestBody here : Done*/ @Valid @RequestBody  IdeaRequest request) {
        // TODO 6: Use store.add(request) and return 201 Created with that Idea as the body.
        //Done


        Idea savedIdea = store.add(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIdea);
    }
}
