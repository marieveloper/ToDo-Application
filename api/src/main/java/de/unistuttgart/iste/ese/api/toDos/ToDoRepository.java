package de.unistuttgart.iste.ese.api.toDos;


import org.springframework.data.repository.CrudRepository;

/**
 * Repository for the ToDo entity.
 * 
 * @author Marie Kufner
 * @version 4.5
 */
public interface ToDoRepository extends CrudRepository<ToDo, Long> {
    ToDo findById(long id);
    
}
