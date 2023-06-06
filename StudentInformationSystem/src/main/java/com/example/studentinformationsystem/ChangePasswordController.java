package com.example.studentinformationsystem;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Alert;
        import javafx.scene.control.TextField;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.sql.*;

public class ChangePasswordController {

    @FXML
    private TextField txtConfirm;
    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    public void handleOK(ActionEvent actionEvent){
        Connection connection = Database.connectDb();
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirm.getText();

        if (!password.equals(confirmPassword)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match please re-enter the password correctly.");
            alert.showAndWait();
            return;
        }

        String query = "UPDATE user SET password = ? WHERE user_name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, username);

            int rowsAffected = preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            if (rowsAffected > 0) {
                alert.setContentText("Your password changed successfully");
            } else {
                alert.setContentText("No user found with the given username please enter a valid username.");
            }
            alert.showAndWait();

            connection.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Error accessing the database please try again later");
            alert.showAndWait();
            e.printStackTrace();

        }
    }
    @FXML
    void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("student_page.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

