package de.unistuttgart.iste.ese.api.csv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the CSV export.
 * 
 * @author Marie Kufner
 * @version 4.5
 * @see de.unistuttgart.iste.ese.api.csv.CsvExportService
 */
@RestController
public class CsvController {

    @Autowired
    private CsvExportService csvExportService;

    /**
     * Returns a CSV file with all ToDos.
     * @return the CSV file as a ResponseEntity
     */
    @GetMapping("/csv-downloads/todos")
    public ResponseEntity<Resource> getAllTodosAsCsv() {
        String filename = "todos.csv";
        InputStreamResource file = new InputStreamResource(csvExportService.load());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
            .contentType(MediaType.parseMediaType("application/csv; charset=UTF-8"))
            .body(file);
    }
}
