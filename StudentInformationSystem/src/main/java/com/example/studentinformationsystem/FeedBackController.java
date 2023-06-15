package com.example.studentinformationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class FeedBackController {

    @FXML
    private TextArea taFB;

    @FXML
    private TextField txfEAU;

    @FXML
    void handleSendButton(ActionEvent event)  {
        String senderEmail = txfEAU.getText();
        String feedback = taFB.getText();
        String developerEmail = "ephremhabtamu2015@gmail.com";
        String host = "smtp.gmail.com";
        String port = "587";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props);
        try {
        // Create a new email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(developerEmail));
        message.setSubject("Feedback Received");
        message.setText("Sender Email: " + senderEmail + "\n\nFeedback: \n" + feedback);

        // Send the email
        Transport.send(message);

            // Show success popup
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Email Sent");
            alert.setHeaderText(null);
            alert.setContentText("Your email has been sent successfully.");
            alert.showAndWait();
        } catch (MessagingException e) {
            // Show error popup
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email Failed");
            alert.setHeaderText(null);
            alert.setContentText("Failed to send email. Please check your email settings and try again.");
            alert.showAndWait();
        }

    }

}