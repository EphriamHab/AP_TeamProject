package com.example.studentinformationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class student_page_controller {


    FXMLLoader loader;
    Parent root;
    Stage stage;
    Scene scene;
    private String loggedInUsername;
    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
    }
    @FXML
    public void initialize() {

    }
    @FXML
    void handleViewGrade(ActionEvent event) throws IOException {
        loader = new FXMLLoader(getClass().getResource("ViewGrade.fxml"));
        root = loader.load();
        ViewGradeController viewGradeController = loader.getController();
        viewGradeController.setLoggedInUsername(loggedInUsername);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void changePassword(ActionEvent event) throws IOException {
        loader = new FXMLLoader(getClass().getResource("ChangePassword.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void handleBack(ActionEvent event) throws IOException {
        loader = new FXMLLoader(getClass().getResource("Home-view.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
