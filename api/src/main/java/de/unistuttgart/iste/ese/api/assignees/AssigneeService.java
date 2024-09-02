package de.unistuttgart.iste.ese.api.assignees;

import de.unistuttgart.iste.ese.api.toDos.ToDo;
import de.unistuttgart.iste.ese.api.toDos.ToDoRepository;
import de.unistuttgart.iste.ese.api.toDos.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Service for the Assignee entity.
 * 
 * @author Marie Kufner
 * @version 4.5
 */
@Service
public class AssigneeService {
    @Autowired
    private AssigneeRepository assigneeRepository;
    
    @Autowired
    private ToDoRepository toDoRepository;
    
    @Autowired
    private ToDoService toDoService;
    
    /**
     * Returns all Assignees from the database.
     * @return a list of all Assignees
     */
    public List<Assignee> getAssignees(){
        return (List<Assignee>) assigneeRepository.findAll();
    }

    /**
     * Deletes an Assignee with the given id from the database.
     * @param id
     * @return the deleted Assignee
     * @throws ResponseStatusException if the Assignee with the given id does not exist
     */
    public Assignee deleteAssignee(long id){
        Assignee assigneeToDelete = assigneeRepository.findById(id);
        if(assigneeToDelete != null) {
            List<ToDo> toDos = toDoService.getToDos();
            for (ToDo toDo : toDos) {
                if (toDo.getAssigneeList().contains(assigneeToDelete)) {
                    toDo.getAssigneeList().remove(assigneeToDelete);
                    toDoRepository.save(toDo);
                }
            }
            assigneeRepository.deleteById(id);
            return assigneeToDelete;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignee not found");
        }
    }
    
}
