package de.unistuttgart.iste.ese.api.toDos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Controller for the ToDo entity.
 *
 * @author Marie Kufner
 * @version 4.5
 * @since 1.0
 */
@RestController
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    ToDoService toDoService;
    
    @PostConstruct
    /**
     * Initializes the database with sample data.
     */
    public void init() {
        // check if DB is empty
        long numberOfToDos = toDoRepository.count();
        if (numberOfToDos == 0) {
            // adding sample data for demonstration purposes
            ToDo finishExercise = new ToDo("A shorter title",
                "A potentially longer description", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 1000));
            toDoRepository.save(finishExercise);
            ToDo grumpyToDo = new ToDo("Grumpy ToDo", "No motivation to do this",
                new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 1000));
            toDoRepository.save(grumpyToDo);
        }
    }

    /**
     * Returns all ToDos.
     *
     * @return all ToDos
     */
    @GetMapping("/todos")
    public List<ToDo> getToDos() {
        return toDoService.getToDos();
    }

    /**
     * Returns a ToDo with the given ID.
     *
     * @param id the ID of the ToDo
     * @return the ToDo with the given ID
     * @throws ResponseStatusException if the ToDo with the given ID does not exist
     */
    @GetMapping("/todos/{id}")
    public ToDo getToDo(@PathVariable("id") long id) {
        ToDo searchedToDo = toDoRepository.findById(id);
        if (searchedToDo != null) {
            return searchedToDo;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("ToDo with ID %s not found!", id));
    }

    /**
     * Creates a new ToDo.
     *
     * @param requestBody the ToDo to create
     * @return the created ToDo
     */
    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public ToDo createTodo(@Valid @RequestBody ToDoDto requestBody) {
        return toDoService.createToDo(requestBody);
    }

    /**
     * Updates a ToDo.
     *
     * @param id the ID of the ToDo to update
     * @param requestBody the new ToDo
     * @return the updated ToDo
     * @throws ResponseStatusException if the ToDo with the given ID does not exist
     */
    @PutMapping("/todos/{id}")
    public ToDo updateToDo(@PathVariable("id") long id, @Valid @RequestBody ToDoDto requestBody) {
        ToDo toDoToUpdate = toDoService.updateToDo(requestBody, id);
        if (toDoToUpdate != null) {
            return toDoToUpdate;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("ToDo with ID %s not found!", id));
    }

    /**
     * Deletes a ToDo.
     *
     * @param id the ID of the ToDo to delete
     * @return the deleted ToDo
     * @throws ResponseStatusException if the ToDo with the given ID does not exist
     */
    @DeleteMapping("/todos/{id}")
    public ToDo deleteToDo(@PathVariable("id") long id) {
        ToDo toDoToDelete = toDoRepository.findById(id);
        if (toDoToDelete != null) {
            toDoRepository.deleteById(id);
            return toDoToDelete;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("ToDo with ID %s not found!", id));
    }


}
