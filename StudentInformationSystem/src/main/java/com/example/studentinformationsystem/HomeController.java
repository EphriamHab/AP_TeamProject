
package com.example.studentinformationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Menu about;


    @FXML
    void handleFeedback(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FeedBack.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void handleAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AboutSIMS");
        alert.setHeaderText("SIMS");
        alert.setContentText("This sims desktop application is done by team for class project.\n" +
                "Those Who contributed to this project are\n" +
                "1 Ephrem Habtamu\n2 Fentahun Mengie\n3 Solomon Muhye \n4 Dawit Zewdu \n5 Jemal Workie");
        alert.showAndWait();
    }

    @FXML
    void handleLogin(ActionEvent event) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                root = loader.load();
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch ( IOException ex) {
                ex.printStackTrace();
            }

    }



}
