package de.unistuttgart.iste.ese.api.assignees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class AssigneeControllerTest {

    @Mock
    private AssigneeRepository assigneeRepository;

    @InjectMocks
    private AssigneeController assigneeController;

    private Assignee testAssignee;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testAssignee = new Assignee("Test", "User", "test.user@example.com");
        testAssignee.setId(1L);
    }

    @Test
    public void testGetAssignee_AssigneeExists_ReturnsAssignee() {
        when(assigneeRepository.findById(1L)).thenReturn(testAssignee);

        Assignee result = assigneeController.getAssignee(1L);

        assertEquals(testAssignee, result);
    }


    @Test
    public void testGetAssignee_NegativeId_ThrowsResponseStatusException() {
        when(assigneeRepository.findById(1L)).thenReturn(testAssignee);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> assigneeController.getAssignee(-1L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Assignee with ID -1 not found!", exception.getReason());
    }

    @Test
    public void testGetAssignee_ZeroId_ThrowsResponseStatusException() {
        when(assigneeRepository.findById(1L)).thenReturn(testAssignee);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> assigneeController.getAssignee(0L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Assignee with ID 0 not found!", exception.getReason());
    }

    @Test
    public void testGetAssignee_NonExistentId_ThrowsResponseStatusException() {
        when(assigneeRepository.findById(1L)).thenReturn(testAssignee);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> assigneeController.getAssignee(100L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Assignee with ID 100 not found!", exception.getReason());
    }
}