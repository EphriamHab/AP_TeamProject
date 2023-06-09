package com.example.studentinformationsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ViewGradeController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private TableColumn<StudentResult , String> Col_CID;

    @FXML
    private TableColumn<StudentResult , String> Col_CN;
    @FXML
    private TableColumn<StudentResult , Integer> Col_CrHr;

    @FXML
    private TableColumn<StudentResult, String> Col_Grade;

    @FXML
    private TextField txfGpa;
    @FXML
    private Label labelName;


    @FXML
    private TableView<StudentResult> resultTable;

    private String loggedInStudentId;
    private String loggedInUsername;

    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
        tableView();


    }
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (loggedInUsername != null) {
            tableView();
        }
    }

    public void tableView() {
        loggedInStudentId = fetchStudentId(loggedInUsername);


        if (loggedInStudentId != null) {

            Col_CID.setCellValueFactory(cellData -> cellData.getValue().courseIdProperty());
            Col_CN.setCellValueFactory(cellData -> cellData.getValue().courseNameProperty());
            Col_CrHr.setCellValueFactory(cellData -> cellData.getValue().creditHourProperty().asObject());
            Col_Grade.setCellValueFactory(cellData -> cellData.getValue().letterGradeProperty());
            resultTable.setItems(getStudentResults());

            // Calculate and display the GPA

            String[] studentInfo = getStudentInfo(loggedInStudentId);
            String firstName = studentInfo[0];
            String lastName = studentInfo[1];
            labelName.setText(firstName + " " + lastName);
            double gpa = calculateGPA();
            txfGpa.setText(String.format("%.2f", gpa));

        }
    }
    private String fetchStudentId(String username) {
        try {
            connect = Database.connectDb();
            prepare = connect.prepareStatement("SELECT student_id FROM user WHERE user_name = ?");
            prepare.setString(1, username);
            // Replace getCurrentUsername() with the actual method to retrieve the current username
            result = prepare.executeQuery();
            
            if (result.next()) {

                return result.getString("student_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

     private String[] getStudentInfo(String loggedInStudentId) {
        String[] studentInfo = new String[2];

        try {
            connect = Database.connectDb();
            prepare = connect.prepareStatement("SELECT first_name, last_name FROM student WHERE student_id = ?");
            prepare.setString(1, this.loggedInStudentId);
            result = prepare.executeQuery();

            if (result.next()) {
                studentInfo[0] = result.getString("first_name");
                studentInfo[1] = result.getString("last_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentInfo;
    }


    private ObservableList<StudentResult> getStudentResults() {
        ObservableList<StudentResult> studentResults = FXCollections.observableArrayList();

        try {
            connect = Database.connectDb();
            prepare = connect.prepareStatement("SELECT cmv.course_id, cmv.course_name, cmv.credit_hours, cmv.mark_obtained FROM course_marks_view cmv JOIN user sit ON cmv.student_id = sit.student_id WHERE cmv.student_id = ?");
            System.out.println("prepare");
            prepare.setString(1, this.loggedInStudentId);
            result = prepare.executeQuery();

            while (result.next()) {
                String courseId = result.getString("course_id");
                String courseName = result.getString("course_name");

                int creditHour = result.getInt("credit_hours");
                double numberGrade = result.getDouble("mark_obtained");
                String grade = calculateLetterGrade(String.valueOf(numberGrade));

                StudentResult studentResult = new StudentResult(courseId, courseName, numberGrade, creditHour, grade);
                studentResults.add(studentResult);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentResults;
    }

    private String calculateLetterGrade(String grade) {
        double numericGrade = Double.parseDouble(grade);

        if (numericGrade >= 90) {
            return "A";
        } else if (numericGrade >= 80) {
            return "B";
        } else if (numericGrade >= 70) {
            return "C";
        } else if (numericGrade >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    private double calculateGPA() {
        ObservableList<StudentResult> studentResults = resultTable.getItems();
        int totalCredits = 0;
        int totalPoints = 0;

        for (StudentResult result : studentResults) {
            int creditHour = result.getCreditHour();
            totalCredits += creditHour;

            String grade = result.getLetterGrade();
            int points;

            switch (grade) {
                case "A":
                    points = 4 * creditHour;
                    break;
                case "B":
                    points = 3 * creditHour;
                    break;
                case "C":
                    points = 2 * creditHour;
                    break;
                case "D":
                    points = 1 * creditHour;
                    break;
                default:
                    points = 0;
                    break;
            }

            totalPoints += points;
        }

        return (double) totalPoints / totalCredits;
    }
    @FXML
    void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("student_page.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}