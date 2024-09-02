package de.unistuttgart.iste.ese.api.mail;

import de.unistuttgart.iste.ese.api.assignees.Assignee;
import de.unistuttgart.iste.ese.api.toDos.ToDo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This class is used to create and send mails.
 * 
 * @author Marie Kufner
 * @version 4.5
 */
@Component
public class Mail {
    @Value("${mail.senderAddress}")
    private String senderAddress;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    
    private String password;
    private final Session session;
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("YYYY-MM-dd");
    private static final String DELIMITER = ",";
    
    /**
     * Constructs a mail session with the SMTP properties.
     */
    public Mail() {
        // instantiate SMTP properties (taken from
        // https://www.tik.uni-stuttgart.de/en/support/service-manuals/e-mail/)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.uni-stuttgart.de");
        props.put("mail.smtp.port", "587");

        // get a Session object with the properties
        session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
    
    /**
     * Sends a mail to the recipient address with the given ToDo and assignees.
     * @param recipientAssignee the recipient
     * @param todo the ToDo
     * @param assignees the assignees
     * @throws RuntimeException if the mail could not be sent
     */
    public void sendMail(final Assignee recipientAssignee, final ToDo todo, final Set<Assignee> assignees) {
        
        try {
            // create a default MimeMessage object and fill its attributes
            Message message = new MimeMessage(this.session);
            final String recipientAddress = recipientAssignee.getEmail();
            message.setFrom(new InternetAddress(senderAddress));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipientAddress));
            message.setSubject("New ToDo with ID " + todo.getId() +" is assigned to you");
            message.setText(createMailText(todo, recipientAssignee, assignees));
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Creates the mail text for the given ToDo and assignees.
     * @param todo the ToDo
     * @param recipientAssignee the recipient
     * @param assignees the assignees
     * @return the mail text
     */
    private static String createMailText(ToDo todo, Assignee recipientAssignee, Set<Assignee> assignees) {
            String createdDate = getFormattedDateString(todo.getCreatedDate());
            String dueDate = getFormattedDateString(todo.getDueDate());
            String mailText = "";
            String assigneeString = getFormattedAssigneeString(assignees);
            if (todo.isFinished()) {
                String finishedDate = getFormattedDateString(todo.getFinishedDate());
                mailText = "Hi " + recipientAssignee.getPrename() + " " + recipientAssignee.getName() 
                    + ", a new ToDo is assigned to you." + "\n" + "\n" + "Title: " + todo.getTitle() 
                    + "\n" + "Description: " + todo.getDescription() + "\n" + "Due Date: " + dueDate + "\n" 
                    + "Created Date: " + createdDate + "\n" + "Assignees: " + assigneeString + "\n" 
                    + "The ToDo is finished on date: " + finishedDate  + "\n" + "\n" + "\n" 
                    + "Thanks for using our ToDo-App!";
            } else {
                mailText = "Hi " + recipientAssignee.getPrename() + " " + recipientAssignee.getName() 
                    + ", a new ToDo is assigned to you." + "\n" + "\n" + "Title: " + todo.getTitle() 
                    + "\n" + "Description: " + todo.getDescription() + "\n" + "Due Date: " + dueDate + "\n" 
                    + "Created Date: " + createdDate + "\n" + "Assignees: " + assigneeString + "\n" 
                    + "ToDo is not finished yet" + "\n" + "\n" + "\n" + "Thanks for using our ToDo-App!";
            }
            return mailText;
    }
    
    /**
     * Formats the given date.
     * @param date the date
     * @return the formatted date
     */
    private static String getFormattedDateString(Date date) {
        if(date == null || date.equals(new Date(0))){
            return "not set";
        }
        return DATE_FORMATTER.format(date);
    }
    
    /**
     * Formats the given assignees as a String .
     * @param assignees the assignees
     * @return the formatted assignees for the mail text
     */
    private static String getFormattedAssigneeString(Set<Assignee> assignees) {
        String assigneeString = "";
        if (!assignees.isEmpty()) {
            return assignees.stream()
                .map(assignee -> assignee.getPrename() + " " + assignee.getName())
                .collect(Collectors.joining(DELIMITER));
        }
        return assigneeString;
    }
}
