package com.example.studentinformationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.*;


public class FeedBackController {

    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private TextArea taFB;

    @FXML
    private TextField txfEAU;

    @FXML
    void handleSendButton(ActionEvent event)  {
        try{
            connect = Database.connectDb();
            String insertQuery = "INSERT INTO feedback (user_email,feed_back_description) VALUES (?,?)";
            prepare = connect.prepareStatement(insertQuery);
            prepare.setString(1, txfEAU.getText());
            prepare.setString(2,taFB.getText());
            prepare.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Your Feedback is successfully send Thank you!!!");
            alert.showAndWait();
    }catch (SQLException ex){
            ex.printStackTrace();
        }

}
}