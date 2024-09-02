package de.unistuttgart.iste.ese.api.toDos;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Class to represent the entity of a toDoDto.
 *
 * @author Marie Kufner
 * @version 4.5
 * @since 1.0
 */
public class ToDoDto {
    @Id
    @NotNull
    private long id;
    @NotNull
    @Size(min = 1)
    private String title;
    @NotNull
    private String description;
    private boolean finished;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Date dueDate;
    private List<Long> assigneeIdList;

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


    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public List<Long> getAssigneeIdList() {
        return assigneeIdList;
    }

    public void setAssigneeIdList(List<Long> assigneeIdList) {
        this.assigneeIdList = assigneeIdList;
    }

}
