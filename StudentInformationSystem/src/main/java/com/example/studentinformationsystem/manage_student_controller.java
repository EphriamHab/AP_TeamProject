package com.example.studentinformationsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class manage_student_controller {
        private Stage stage;
        private Scene scene;
        private Parent root;
        private Connection connect;
        private Statement statement;
        private PreparedStatement prepare;
        private ResultSet result;
        @FXML
        private Button btnSearch;
        @FXML
        private Button back;
        @FXML
        private Button backHome;
        @FXML
        private Button btnDelete;

        @FXML
        private Button btnRegister;

        @FXML
        private Button btnUpdate;

        @FXML
        private Button btnView;

        @FXML
        private TextField txfAdress;
        @FXML
        private TextField txfSearch;

        @FXML
        private DatePicker txfDoB;

        @FXML
        private DatePicker txfDoR;

        @FXML
        private TextField txfEmail;

        @FXML
        private TextField txfFN;

    @FXML
    private ComboBox cmbSex;

        @FXML
        private TextField txfID;

        @FXML
        private TextField txfLN;

        public void initialize(){
            ObservableList<String> list = FXCollections.observableArrayList("Male","Female","Other");
            cmbSex.setItems(list);
        }

        @FXML
        void handleDelete(ActionEvent event) {
                try{
                       connect = Database.connectDb();
                       prepare=connect.prepareStatement("delete from student where student_id='"+txfSearch.getText()+"' ");
                       prepare.executeUpdate();
                       connect.close();
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText(null);
                        alert.setContentText("Student deleted successfully!");
                        alert.showAndWait();
                }catch (SQLException ex){
                        ex.printStackTrace();
                }


        }

        @FXML
        void handleRegistration(ActionEvent event) {

                try{
                        connect = Database.connectDb();
                        prepare = connect.prepareStatement("insert into student values(?,?,?,?,?,?,?,?)");
                        prepare.setString(1,txfID.getText());
                        prepare.setString(2,txfFN.getText());
                        prepare.setString(3,txfLN.getText());
                        prepare.setString(4,txfEmail.getText());
                        prepare.setString(5,txfAdress.getText());
                        prepare.setDate(6, Date.valueOf(txfDoB.getValue()));
                        prepare.setString(7,cmbSex.getValue().toString());
                        prepare.setDate(8,Date.valueOf(txfDoR.getValue()));
                        prepare.executeUpdate();
                        connect.close();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Student added successfully!");
                        alert.showAndWait();

                }catch (SQLException ex){
                        ex.printStackTrace();
                }


        }
        @FXML
        void handleSearch(ActionEvent event) {
         try {
                 connect = Database.connectDb();
                 statement=connect.createStatement();
                 result=statement.executeQuery("select * from student where student_id='"+txfSearch.getText()+"'");
                 while (result.next()){
                 txfID.setText(result.getString(1));
                 txfFN.setText(result.getString(2));
                 txfLN.setText(result.getString(3));
                 txfEmail.setText(result.getString(4));
                 txfAdress.setText(result.getString(5));
                 txfDoB.setValue(result.getDate(6).toLocalDate());
                 cmbSex.setValue(result.getString(7));
                 txfDoR.setValue(result.getDate(8).toLocalDate());
                 }
             connect.close();
         }catch(SQLException ex){
                 ex.printStackTrace();
            }
        }
        @FXML
        void handleUpdate(ActionEvent event) {
                try{
                        connect = Database.connectDb();
                        prepare= connect.prepareStatement("update student set  student_id=?,first_name=?,last_name=?,email=?,address=?, date_of_birth=?, gender=?, date_enrolled=? where student_id='"+txfSearch.getText()+"'" );
                        prepare.setString(1,txfID.getText());
                        prepare.setString(2,txfFN.getText());
                        prepare.setString(3,txfLN.getText());
                        prepare.setString(4,txfEmail.getText());
                        prepare.setString(5,txfAdress.getText());
                        prepare.setDate(6, Date.valueOf(txfDoB.getValue()));
                        prepare.setString(7,cmbSex.getValue().toString());
                        prepare.setDate(8,Date.valueOf(txfDoR.getValue()));
                        prepare.executeUpdate();
                        connect.close();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Student Updated successfully!");
                        alert.showAndWait();

                }catch (SQLException ex){
                        ex.printStackTrace();
                }

        }

        @FXML
        void handleView(ActionEvent event) {
                try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("view_student_info.fxml"));
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
    void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_page.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    }


