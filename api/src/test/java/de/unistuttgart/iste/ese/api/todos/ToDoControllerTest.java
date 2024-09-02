package de.unistuttgart.iste.ese.api.todos;

import de.unistuttgart.iste.ese.api.toDos.ToDo;
import de.unistuttgart.iste.ese.api.toDos.ToDoController;
import de.unistuttgart.iste.ese.api.toDos.ToDoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ToDoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ToDoService toDoService;

    @InjectMocks
    private ToDoController toDoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(toDoController).build();
    }

    @Test
    public void testGetToDosReturnsListOfToDos() throws Exception {
        ToDo toDo1 = new ToDo("Title1", "Description1", new Date(), new Date());
        ToDo toDo2 = new ToDo("Title2", "Description2", new Date(), new Date());
        List<ToDo> toDoList = Arrays.asList(toDo1, toDo2);

        when(toDoService.getToDos()).thenReturn(toDoList);

        mockMvc.perform(get("/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Title1")))
                .andExpect(jsonPath("$[0].description", is("Description1")))
                .andExpect(jsonPath("$[1].title", is("Title2")))
                .andExpect(jsonPath("$[1].description", is("Description2")));
    }

    @Test
    public void testGetToDosReturnsEmptyList() throws Exception {
        when(toDoService.getToDos()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetToDosReturnsCorrectStructure() throws Exception {
        ToDo toDo = new ToDo("Title", "Description", new Date(), new Date());
        List<ToDo> toDoList = Arrays.asList(toDo);

        when(toDoService.getToDos()).thenReturn(toDoList);

        mockMvc.perform(get("/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Title")))
                .andExpect(jsonPath("$[0].description", is("Description")))
                .andExpect(jsonPath("$[0].createdDate").exists())
                .andExpect(jsonPath("$[0].dueDate").exists());
    }

    @Test
    public void testGetToDosHandlesNullFields() throws Exception {
        ToDo toDo = new ToDo("Title", "Description", null, null);
        List<ToDo> toDoList = Arrays.asList(toDo);

        when(toDoService.getToDos()).thenReturn(toDoList);

        mockMvc.perform(get("/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Title")))
                .andExpect(jsonPath("$[0].description", is("Description")))
                .andExpect(jsonPath("$[0].createdDate").doesNotExist())
                .andExpect(jsonPath("$[0].dueDate").doesNotExist());
    }

    @Test
    public void testGetToDosHandlesEmptyTitleAndDescription() throws Exception {
        ToDo toDo = new ToDo("", "", new Date(), new Date());
        List<ToDo> toDoList = Arrays.asList(toDo);

        when(toDoService.getToDos()).thenReturn(toDoList);

        mockMvc.perform(get("/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("")))
                .andExpect(jsonPath("$[0].description", is("")));
    }
}