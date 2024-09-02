package de.unistuttgart.iste.ese.api.assignees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

/**
 * Controller for the assignees.
 *
 * @author Marie Kufner
 * @version 4.5
 */
@RestController
public class AssigneeController {
    @Autowired
    private AssigneeRepository assigneeRepository;

    @Autowired
    private AssigneeService assigneeService;

    /**
     * Initializes the database with sample data.
     */
    @PostConstruct
    public void init() {
        // check if DB is empty
        long numberOfAssignees = assigneeRepository.count();
        if (numberOfAssignees == 0) {
            // adding sample data for demonstration purposes
            Assignee jonasMueller = new Assignee("Jonas", "Müller",
                "jonas.mueller@iste.uni-stuttgart.de");
            assigneeRepository.save(jonasMueller);
            Assignee marie = new Assignee("Marie", "Kufner", "marie.kufner@uni-stuttgart.de");
            assigneeRepository.save(marie);
        }
    }

    /**
     * Gets all assignees.
     * @return a list of all assignees
     */
    @GetMapping("/assignees")
    public List<Assignee> getAssignees() {
        return assigneeService.getAssignees();
    }

    /**
     * Gets an assignee by its ID.
     * @param id the ID of the assignee
     * @return the assignee with the given ID
     * @throws ResponseStatusException if the assignee with the given ID does not exist
     */
    @GetMapping("/assignees/{id}")
    public Assignee getAssignee(@PathVariable("id") long id) {
        Assignee searchedAssignee = assigneeRepository.findById(id);
        if (searchedAssignee != null) {
            return searchedAssignee;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Assignee with ID %s not found!", id));
        }
    }

    /**
     * Creates a new assignee.
     * @param requestBody the assignee to be created
     * @return the created assignee
     * @throws ResponseStatusException if the assignee with the given ID already exists
     */
    @PostMapping("/assignees")
    @ResponseStatus(HttpStatus.CREATED)
    public Assignee createAssignee(@Valid @RequestBody Assignee requestBody) {
        Assignee assignee = new Assignee(requestBody.getPrename(), requestBody.getName(), requestBody.getEmail());
        return assigneeRepository.save(assignee);
    }

    /**
     * Updates an assignee.
     * @param id the ID of the assignee to be updated
     * @param requestBody the updated assignee
     * @return the updated assignee
     * @throws ResponseStatusException if the assignee with the given ID does not exist
     */
    @PutMapping("/assignees/{id}")
    public Assignee updateAssignee(@PathVariable("id") long id, @Valid @RequestBody Assignee requestBody) {
        requestBody.setId(id);
        Assignee assigneeToUpdate = assigneeRepository.findById(id);
        if (assigneeToUpdate != null) {
            return assigneeRepository.save(requestBody);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Assignee with ID %s not found!", id));
    }

    /**
     * Deletes an assignee.
     * @param id the ID of the assignee to be deleted
     * @return the deleted assignee
     * @throws ResponseStatusException if the assignee with the given ID does not exist
     */
    @DeleteMapping("/assignees/{id}")
    public Assignee deleteAssignee(@PathVariable("id") long id) {
        Assignee assigneeToDelete = assigneeRepository.findById(id);
        if (assigneeToDelete != null) {
            return assigneeService.deleteAssignee(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Assignee with ID %s not found!", id));
        }
    }
}
