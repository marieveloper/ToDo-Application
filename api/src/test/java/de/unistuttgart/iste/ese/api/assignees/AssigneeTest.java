package de.unistuttgart.iste.ese.api.assignees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.*;

class AssigneeTest {

    private Validator validator;
    private Assignee assignee;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        assignee = new Assignee("John", "Doe", "john.doe@uni-stuttgart.de");
    }

    @Test
    void testValidAssignee() {
        assertTrue(validator.validate(assignee).isEmpty());
    }

    @Test
    void testInvalidPrename() {
        assignee.setPrename("");
        assertFalse(validator.validate(assignee).isEmpty());
    }

    @Test
    void testInvalidName() {
        assignee.setName(null);
        assertFalse(validator.validate(assignee).isEmpty());
    }

    @Test
    void testValidEmail() {
        assignee.setEmail("jane.doe@iste.uni-stuttgart.de");
        assertTrue(validator.validate(assignee).isEmpty());
    }

    @Test
    void testInvalidEmail() {
        assignee.setEmail("invalid.email@gmail.com");
        assertFalse(validator.validate(assignee).isEmpty());
    }

    @Test
    void testGettersAndSetters() {
        assignee.setId(1L);
        assertEquals(1L, assignee.getId());
        
        assignee.setPrename("Jane");
        assertEquals("Jane", assignee.getPrename());
        
        assignee.setName("Smith");
        assertEquals("Smith", assignee.getName());
        
        assignee.setEmail("jane.smith@uni-stuttgart.de");
        assertEquals("jane.smith@uni-stuttgart.de", assignee.getEmail());
    }

    @Test
    void testConstructor() {
        Assignee newAssignee = new Assignee("Alice", "Johnson", "alice.johnson@uni-stuttgart.de");
        assertEquals("Alice", newAssignee.getPrename());
        assertEquals("Johnson", newAssignee.getName());
        assertEquals("alice.johnson@uni-stuttgart.de", newAssignee.getEmail());
    }
}
