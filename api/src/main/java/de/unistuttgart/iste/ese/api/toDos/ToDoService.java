package de.unistuttgart.iste.ese.api.toDos;

import de.unistuttgart.iste.ese.api.assignees.Assignee;
import de.unistuttgart.iste.ese.api.assignees.AssigneeRepository;
import de.unistuttgart.iste.ese.api.mail.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/**
 * Service for the ToDo entity.
 * 
 * @author Marie Kufner
 * @version 4.5
 * @since 1.0
 */
@Service
public class ToDoService {
    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private AssigneeRepository assigneeRepository;
    
    @Autowired
    private Mail mail;

    /**
     * Returns all ToDos.
     * @return all ToDos
     */
    public List<ToDo> getToDos() {
        return (List<ToDo>) toDoRepository.findAll();
    }

    /**
     * Creates a new ToDo and saves this to the database.
     * @param toDo the ToDoDto to save
     * @return the saved ToDo
     */
    public ToDo createToDo(ToDoDto toDo) {
        ToDo newToDo = new ToDo();
        newToDo.setTitle(toDo.getTitle());
        newToDo.setDescription(toDo.getDescription());
        newToDo.setCreatedDate(new Date(System.currentTimeMillis()));
        newToDo.setDueDate(toDo.getDueDate());
        setAssigneeList(toDo, newToDo);
        return toDoRepository.save(newToDo);
    }

    /**
     * Updates a ToDo with the given ID.
     * @param toDoDto
     * @param id
     * @return the updated and saved ToDo
     * @throws ResponseStatusException if the ToDo with the given ID does not exist
     */
    public ToDo updateToDo(ToDoDto toDoDto, long id) {
        toDoDto.setId(id);
        ToDo toDoToUpdate = toDoRepository.findById(id);
        if (toDoToUpdate != null) {
            toDoToUpdate.setTitle(toDoDto.getTitle());
            toDoToUpdate.setDescription(toDoDto.getDescription());
            toDoToUpdate.setFinished(toDoDto.isFinished());
            if (toDoDto.isFinished()) {
                toDoToUpdate.setFinishedDate(new Date(System.currentTimeMillis()));
            }else{
                toDoToUpdate.setFinishedDate(null);
            }
            toDoToUpdate.setDueDate(toDoDto.getDueDate());
            setAssigneeList(toDoDto, toDoToUpdate);
            return toDoRepository.save(toDoToUpdate);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("ToDo with ID %s not found!", id));
        }
    }

    /**
     * Sets the assigneeList of the ToDo.
     * @param toDoDto
     * @param toDoToUpdate
     * @throws ResponseStatusException if an assignee with the given ID does not exist
     */
    private void setAssigneeList(ToDoDto toDoDto, ToDo toDoToUpdate){
        Set<Assignee> assigneeList = new HashSet<>();
        Set<Assignee> oldAssignees = toDoToUpdate.getAssigneeList();
        List<Long> assigneeIds = toDoDto.getAssigneeIdList();
        if (assigneeIds != null) {
            //adds all assignees to the assigneeList
            for (long assigneeId : assigneeIds) {
                Assignee assignee = assigneeRepository.findById(assigneeId);
                if (assignee != null) {
                    assigneeList.add(assignee);
                    if (oldAssignees == null || !oldAssignees.contains(assignee)) {
                        mail.sendMail(assignee, toDoToUpdate, assigneeList);
                    }
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Assignee with ID %s not found!", assigneeId));
                }
            }
            toDoToUpdate.setAssigneeList(assigneeList);
        }
    }
}
