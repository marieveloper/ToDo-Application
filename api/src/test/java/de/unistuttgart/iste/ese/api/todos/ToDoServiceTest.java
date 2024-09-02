package de.unistuttgart.iste.ese.api.todos;

import de.unistuttgart.iste.ese.api.assignees.AssigneeRepository;
import de.unistuttgart.iste.ese.api.mail.Mail;
import de.unistuttgart.iste.ese.api.toDos.ToDo;
import de.unistuttgart.iste.ese.api.toDos.ToDoRepository;
import de.unistuttgart.iste.ese.api.toDos.ToDoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class ToDoServiceTest {

    @Mock
    private ToDoRepository toDoRepository;

    @Mock
    private AssigneeRepository assigneeRepository;

    @Mock
    private Mail mail;

    @InjectMocks
    private ToDoService toDoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetToDos_ReturnsEmptyList() {
        when(toDoRepository.findAll()).thenReturn(Collections.emptyList());

        List<ToDo> result = toDoService.getToDos();

        assertEquals(0, result.size());
    }

    @Test
    public void testGetToDos_ReturnsSingleToDo() {
        ToDo toDo = new ToDo("Title1", "Description1", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        when(toDoRepository.findAll()).thenReturn(Collections.singletonList(toDo));

        List<ToDo> result = toDoService.getToDos();

        assertEquals(1, result.size());
        assertEquals("Title1", result.get(0).getTitle());
    }

    @Test
    public void testGetToDos_ReturnsMultipleToDos() {
        ToDo toDo1 = new ToDo("Title1", "Description1", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        ToDo toDo2 = new ToDo("Title2", "Description2", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        when(toDoRepository.findAll()).thenReturn(Arrays.asList(toDo1, toDo2));

        List<ToDo> result = toDoService.getToDos();

        assertEquals(2, result.size());
        assertEquals("Title1", result.get(0).getTitle());
        assertEquals("Title2", result.get(1).getTitle());
    }

    @Test
    public void testGetToDos_RepositoryThrowsException() {
        when(toDoRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        try {
            toDoService.getToDos();
        } catch (RuntimeException e) {
            assertEquals("Database error", e.getMessage());
        }
    }

    @Test
    public void testGetToDos_VerifyRepositoryInteraction() {
        toDoService.getToDos();
        verify(toDoRepository, times(1)).findAll();
    }
}