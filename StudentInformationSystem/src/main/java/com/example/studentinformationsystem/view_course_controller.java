package com.example.studentinformationsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Comparator;

public class view_course_controller {
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private Button btnBack;

    @FXML
    private Button btn_search;
    @FXML
    private TableView<Course> tableView;
    @FXML
    private TableColumn<Course, String> colCC;

    @FXML
    private TableColumn<Course, String> colCN;

    @FXML
    private TableColumn<Course, Integer> colCrHr;
    @FXML
    private TableColumn<Course, String> colDepId;

    @FXML
    private TableColumn<Course, Integer> colID;

    public void initialize() {
        getViews();
    }

    ObservableList<Course> courseList = FXCollections.observableArrayList();

    @FXML
    private TextField txf_search;

    @FXML
    void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("manageCourse.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void loadTableViews() {
        colID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        colCN.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colCC.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colCrHr.setCellValueFactory(new PropertyValueFactory<>("CreditHours"));
        colDepId.setCellValueFactory(new PropertyValueFactory<>("DepartmentId"));
        tableView.setItems(courseList);

    }

    public void getViews() {
        try {
            connect = Database.connectDb();


            prepare = connect.prepareStatement("select * from course");
            result = prepare.executeQuery();
            while (result.next()) {
                courseList.add(new Course(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getString(5)));
            }

            loadTableViews();
            connect.close();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String searchText = txf_search.getText().toLowerCase();

        // Clear the previous search results
        courseList.clear();

        try {
            connect = Database.connectDb();
            prepare = connect.prepareStatement("SELECT * FROM course WHERE LOWER(course_name) LIKE ?");
            prepare.setString(1, searchText + "%");
            result = prepare.executeQuery();

            while (result.next()) {
                courseList.add(new Course(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getString(5)));
            }

            connect.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Sort the courseList alphabetically by course name
        courseList.sort(Comparator.comparing(Course::getCourseName));

        tableView.setItems(courseList);
    }
}
