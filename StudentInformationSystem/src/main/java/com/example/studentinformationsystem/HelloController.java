
package com.example.studentinformationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button btnAdmin;



    @FXML
    private Button btnStudent;

    @FXML
    private Button btnTeacher;

    @FXML
    void handleAdmin(ActionEvent event) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_login.fxml"));
                root = loader.load();
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch ( IOException ex) {
                ex.printStackTrace();
            }

    }

    @FXML
    void handleStudent(ActionEvent event) {

    }

    @FXML
    void handleTeacher(ActionEvent event) {

    }

}
