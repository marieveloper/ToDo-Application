package de.unistuttgart.iste.ese.api.toDos;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.unistuttgart.iste.ese.api.assignees.Assignee;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Class to represent the entity of a todo.
 *
 * @author Marie Kufner
 * @version 4.5
 * @since 1.0
 */
@Entity
@Table(name = "todos")
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @NotNull
    @Size(min = 1)
    private String title;
    @NotNull
    private String description;
    private boolean finished;
    
    @ManyToMany(targetEntity = Assignee.class, fetch = FetchType.EAGER)
    private Set<Assignee> assigneeList;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Date createdDate;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Date dueDate;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Date finishedDate;
    
    
    // empty default constructor is necessary for JPA
    public ToDo() {}
    /**
     * Constructs a ToDo instance.
     *
     * @ensures this.title == title && this.description == description && this.createdDate == createdDate && this.dueDate == dueDate
     */
    public ToDo(String title, String description, Date createdDate, Date dueDate){
        this.title = title;
        this.description = description;
        this.assigneeList = new HashSet<>();
        this.createdDate = createdDate;
        this.dueDate = dueDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }
    
    public Set<Assignee> getAssigneeList() {
        return assigneeList;
    }

    public void setAssigneeList(Set<Assignee> assigneeList) {
        this.assigneeList = assigneeList;
    }
    
   
}
