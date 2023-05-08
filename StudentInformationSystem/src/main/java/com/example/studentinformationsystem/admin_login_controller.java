package com.example.studentinformationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class admin_login_controller {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;
    @FXML
    private Button btnAdmin_login;

    @FXML
    void handleAdminLogin(ActionEvent event) {
        String sql = "SELECT * FROM admin WHERE username = ? and passwords = ?";
        connect = Database.connectDb();
        try{
            Alert alert;

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());

            result = prepare.executeQuery();
//            CHECK IF FIELDS ARE EMPTTY
            if(username.getText().isEmpty() || password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                if(result.next()){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_page.fxml"));
                    root = loader.load();
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                }else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Wrong Username/Password");
                        alert.showAndWait();

                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

}



