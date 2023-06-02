package com.example.studentinformationsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.*;

public class ViewGradeController {
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





    public void initialize() {
        // Set up the table
        loggedInStudentId = fetchStudentId();
        if (loggedInStudentId != null) {


            Col_CID.setCellValueFactory(cellData -> cellData.getValue().courseIdProperty());
            Col_CN.setCellValueFactory(cellData -> cellData.getValue().courseNameProperty());
            Col_CrHr.setCellValueFactory(cellData -> cellData.getValue().creditHourProperty().asObject());
            Col_Grade.setCellValueFactory(cellData -> cellData.getValue().letterGradeProperty());
            resultTable.setItems(getStudentResults());

            // Calculate and display the GPA
            String[] studentInfo = getStudentInfo();
            String firstName = studentInfo[0];
            String lastName = studentInfo[1];
            labelName.setText(firstName + " " + lastName);
            double gpa = calculateGPA();
            txfGpa.setText(String.format("%.2f", gpa));
            System.out.println("loggedInStudentId: " + loggedInStudentId);
        }
    }
  private String fetchStudentId(){
       try {
           connect = Database.connectDb();
           prepare = connect.prepareStatement("SELECT student_id FROM user");
           result=prepare.executeQuery();
           if (result.next()) {
               return result.getString("student_id");
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
        return null;
  }
    private String[] getStudentInfo() {
        String[] studentInfo = new String[2];

        try {
            connect = Database.connectDb();
            prepare = connect.prepareStatement("SELECT first_name, last_name FROM student WHERE student_id = ?");
            prepare.setString(1, loggedInStudentId);
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
            prepare.setString(1, loggedInStudentId);
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
}