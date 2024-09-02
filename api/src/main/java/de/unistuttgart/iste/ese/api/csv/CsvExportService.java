package de.unistuttgart.iste.ese.api.csv;

import de.unistuttgart.iste.ese.api.toDos.ToDo;
import de.unistuttgart.iste.ese.api.toDos.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Service for the CSV export.
 * 
 * @author Marie Kufner
 * @version 4.5
 */
@Service
public class  CsvExportService {

    @Autowired
    private ToDoRepository toDoRepository;

    /**
     * Loads all ToDos from the database and converts them to a CSV file.
     * @return the CSV file as a ByteArrayInputStream
     */
    public ByteArrayInputStream load() {
        List<ToDo> toDos = (List<ToDo>) toDoRepository.findAll();
        return CsvHelper.toDosToCSV(toDos);
    }
}
