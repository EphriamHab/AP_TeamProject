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


public class view_student_controller {
        private Connection connect;
        private Statement statement;
        private PreparedStatement prepare;
        private ResultSet result;
        @FXML
        private Button btn_back;

        @FXML
        private Button btn_search;

        @FXML
        private TextField txfSearch;
        @FXML
        private TableView<Student> tableView;
        @FXML
        private TableColumn<Student,String > colGender;

        @FXML
        private TableColumn<Student, String> col_Address;

        @FXML
        private TableColumn<Student, Date> col_DoB;


        @FXML
        private TableColumn<Student, Date> col_DoE;

        @FXML
        private TableColumn<Student, String> col_Email;

        @FXML
        private TableColumn<Student, String> col_FN;

        @FXML
        private TableColumn<Student, String> col_ID;
        @FXML
        public void initialize() {
                getView();
        }

        @FXML
        private TableColumn<Student, String > col_LN;
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        @FXML
        void handleBackbutton(ActionEvent event) throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("manageStudent.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
        }

        @FXML
        void handleSearch(ActionEvent event) {
                String searchText = txfSearch.getText().toLowerCase();

                // Clear the previous search results
                studentList.clear();

                try {
                        connect = Database.connectDb();
                        prepare = connect.prepareStatement("SELECT * FROM student WHERE LOWER(first_name) LIKE ?");
                        prepare.setString(1, searchText + "%");
                        result = prepare.executeQuery();

                        while (result.next()) {
                                studentList.add(new Student(
                                        result.getString(1),
                                        result.getString(2),
                                        result.getString(3),
                                        result.getString(4),
                                        result.getString(5),
                                        result.getDate(6),
                                        result.getString(7),
                                        result.getDate(8)));
                        }

                        connect.close();
                } catch (SQLException ex) {
                        ex.printStackTrace();
                }

                // Sort the studentList alphabetically by last name
                studentList.sort(Comparator.comparing(Student::getLastName));

                tableView.setItems(studentList);
        }


        public void loadTableView(){
                col_ID.setCellValueFactory(new PropertyValueFactory<>("Student_ID"));
                col_FN.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
                col_LN.setCellValueFactory(new PropertyValueFactory<>("LastName"));
                col_Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
                col_Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
                col_DoB.setCellValueFactory(new PropertyValueFactory<>("Date_of_birth"));
                colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
                col_DoE.setCellValueFactory(new PropertyValueFactory<>("Date_of_Enrolled"));

                tableView.setItems(studentList);

        }
        public void getView(){
                try{
                   connect=Database.connectDb();


                        prepare=connect.prepareStatement("select * from student");
                   result=prepare.executeQuery();
                   while (result.next()){
                           studentList.add(new Student(
                                   result.getString(1),
                                   result.getString(2),
                                   result.getString(3),
                                   result.getString(4),
                                   result.getString(5),
                                   result.getDate(6),
                                   result.getString(7),
                                   result.getDate(8)));


                   }
//                        tableView.setItems(studentList)
                       loadTableView();
                        connect.close();



                }catch (SQLException ex){
                        ex.printStackTrace();
                }
        }

    }


