package de.unistuttgart.iste.ese.api.assignees;

import org.springframework.data.repository.CrudRepository;

/**
 * Repository for the Assignee entity.
 * 
 * @author Marie Kufner
 * @version 4.5
 */
public interface AssigneeRepository extends CrudRepository<Assignee, Long>{
    Assignee findById(long id);
}
