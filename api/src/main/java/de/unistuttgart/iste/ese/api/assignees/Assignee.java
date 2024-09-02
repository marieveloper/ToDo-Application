package de.unistuttgart.iste.ese.api.assignees;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Class to represent the entity of an assignee.
 *
 * @author Marie Kufner
 * @version 4.5
 * @since 1.0
 */
@Entity
@Table(name = "assignees")
public class Assignee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @NotNull
    @NotBlank
    @Size(min = 1)
    private String prename;

    @NotNull
    @NotBlank
    @Size(min = 1)
    private String name;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-.]*uni-stuttgart.de$")
    private String email;
    
    public Assignee() {}
    
    /**
     * Constructs an Assignee instance.
     *
     * @ensures this.prename == prename && this.name == name && this.email == email
     */
    public Assignee(String prename, String name, String email) {
        this.prename = prename;
        this.name = name;
        this.email = email;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
