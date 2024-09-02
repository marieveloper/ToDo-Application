package de.unistuttgart.iste.ese.api.csv;

import de.unistuttgart.iste.ese.api.assignees.Assignee;
import de.unistuttgart.iste.ese.api.toDos.ToDo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Helper class for the CSV export.
 * 
 * @author Marie Kufner
 * @version 4.5
 */
public class CsvHelper {

    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("YYYY-MM-dd");
    private static final String DELIMITER = "+";
    private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT
        .withHeader("id", "title", "description", "finished", "assignees", "createdDate", "dueDate", "finishedDate");

    /**
     * Formats a List of Todos to export them as a CSV file.
     * 
     * @param toDos
     * @return the CSV file as a ByteArrayInputStream
     * @throws RuntimeException
     */
    public static ByteArrayInputStream toDosToCSV(List<ToDo> toDos) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8)), CSV_FORMAT)) {
            for (ToDo toDo : toDos) {
                List<String> data = Arrays.asList(
                    String.valueOf(toDo.getId()),
                    toDo.getTitle(),
                    toDo.getDescription(),
                    Boolean.toString(toDo.isFinished()),
                    formatAssignees(toDo.getAssigneeList()),
                    formatDate(toDo.getCreatedDate()),
                    formatDate(toDo.getDueDate()),
                    formatDate(toDo.getFinishedDate())
                );
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to import data to CSV file: " + e.getMessage());
        }
    }

    /**
     * Private method for formatting a Date to a String.
     * @param date
     * @return the formatted date as a String
     */
    private static String formatDate(Date date) {
        return date == null ? "" : DATE_FORMATTER.format(date);
    }
    
    /**
     * Private method for formatting a Set of Assignees to a String.
     * @param assignees
     * @return the formatted assignees as a String
     */
    private static String formatAssignees(Set<Assignee> assignees) {
        if (assignees.isEmpty()) {
            return "";
        } else {
            return assignees.stream()
                .map(assignee -> assignee.getPrename() + " " + assignee.getName())
                .collect(Collectors.joining(DELIMITER));
        }
    }
}