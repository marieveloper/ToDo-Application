package de.unistuttgart.iste.ese.api.assignees;

import de.unistuttgart.iste.ese.api.toDos.ToDo;
import de.unistuttgart.iste.ese.api.toDos.ToDoRepository;
import de.unistuttgart.iste.ese.api.toDos.ToDoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssigneeServiceTest {

    @Mock
    private AssigneeRepository assigneeRepository;

    @Mock
    private ToDoRepository toDoRepository;

    @Mock
    private ToDoService toDoService;

    @InjectMocks
    private AssigneeService assigneeService;

    private Assignee testAssignee;

    //presets habe ich gemacht
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testAssignee = new Assignee("John", "Doe", "john.doe@uni-stuttgart.de");
        testAssignee.setId(1L);
    }

   @Test
   void testDeleteAssigneeNoRelatedToDos() {
       Assignee assigneeToDelete = new Assignee("John", "Doe", "john.doe@uni-stuttgart.de");
       assigneeToDelete.setId(1L);
   
       when(assigneeRepository.findById(1L)).thenReturn(assigneeToDelete);
       when(toDoService.getToDos()).thenReturn(Collections.emptyList());
   
       Assignee result = assigneeService.deleteAssignee(1L);
   
       assertEquals(assigneeToDelete, result);
       verify(assigneeRepository).findById(1L);
       verify(toDoService).getToDos();
       verify(assigneeRepository).deleteById(1L);
       verify(toDoRepository, never()).save(any(ToDo.class));
   }
   
   @Test
   void testDeleteAssigneeWithRelatedToDos() {
       Assignee assigneeToDelete = new Assignee("John", "Doe", "john.doe@uni-stuttgart.de");
       assigneeToDelete.setId(1L);
   
       ToDo toDo = new ToDo("Task 1", "Description 1", new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()));
       toDo.getAssigneeList().add(assigneeToDelete);
   
       when(assigneeRepository.findById(1L)).thenReturn(assigneeToDelete);
       when(toDoService.getToDos()).thenReturn(Arrays.asList(toDo));
   
       Assignee result = assigneeService.deleteAssignee(1L);
   
       assertEquals(assigneeToDelete, result);
       assertTrue(toDo.getAssigneeList().isEmpty());
       verify(assigneeRepository).findById(1L);
       verify(toDoService).getToDos();
       verify(toDoRepository).save(toDo);
       verify(assigneeRepository).deleteById(1L);
   }
   
   @Test
   void testDeleteAssigneeNotFound() {
       when(assigneeRepository.findById(1L)).thenReturn(null);
   
       assertThrows(ResponseStatusException.class, () -> assigneeService.deleteAssignee(1L));
       verify(assigneeRepository).findById(1L);
       verify(toDoService, never()).getToDos();
       verify(toDoRepository, never()).save(any(ToDo.class));
       verify(assigneeRepository, never()).deleteById(anyLong());
   }
   
   @Test
   void testDeleteAssigneeWithRelatedToDosAndOtherAssignees() {
       Assignee assigneeToDelete = new Assignee("John", "Doe", "john.doe@uni-stuttgart.de");
       assigneeToDelete.setId(1L);
   
       Assignee otherAssignee = new Assignee("Jane", "Doe", "jane.doe@uni-stuttgart.de");
       otherAssignee.setId(2L);
   
       ToDo toDo = new ToDo("Task 1", "Description 1", new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()));
       toDo.getAssigneeList().add(assigneeToDelete);
       toDo.getAssigneeList().add(otherAssignee);
   
       when(assigneeRepository.findById(1L)).thenReturn(assigneeToDelete);
       when(toDoService.getToDos()).thenReturn(Arrays.asList(toDo));
   
       Assignee result = assigneeService.deleteAssignee(1L);
   
       assertEquals(assigneeToDelete, result);
       assertTrue(toDo.getAssigneeList().contains(otherAssignee));
       assertFalse(toDo.getAssigneeList().contains(assigneeToDelete));
       verify(assigneeRepository).findById(1L);
       verify(toDoService).getToDos();
       verify(toDoRepository).save(toDo);
       verify(assigneeRepository).deleteById(1L);
   }

    @Test
    void testDeleteAssigneeWithRelatedToDosNoOtherAssignees() {
        Assignee assigneeToDelete = new Assignee("John", "Doe", "john.doe@uni-stuttgart.de");
        assigneeToDelete.setId(1L);
    
        ToDo toDo = new ToDo("Task 1", "Description 1", new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()));
        toDo.getAssigneeList().add(assigneeToDelete);
    
        when(assigneeRepository.findById(1L)).thenReturn(assigneeToDelete);
        when(toDoService.getToDos()).thenReturn(Arrays.asList(toDo));
    
        Assignee result = assigneeService.deleteAssignee(1L);
    
        assertEquals(assigneeToDelete, result);
        assertTrue(toDo.getAssigneeList().isEmpty());
        verify(assigneeRepository).findById(1L);
        verify(toDoService).getToDos();
        verify(toDoRepository).save(toDo);
        verify(assigneeRepository).deleteById(1L);
    }
}