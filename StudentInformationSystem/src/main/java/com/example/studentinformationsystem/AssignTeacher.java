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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AssignTeacher {
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    private int myIndex;
    private String assigned_id;

    @FXML
    private TableColumn<TeacherAssigned, Integer> ColCID;

    @FXML
    private TableColumn<TeacherAssigned, String> ColDI;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRegister;
    @FXML
    private Button btnBack;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TeacherAssigned , String> colATID;

    @FXML
    private TableColumn<TeacherAssigned, String> colTID;

    @FXML
    private TableView<TeacherAssigned> tableView;

    @FXML
    private TextField txfAID;

    @FXML
    private TextField txfCID;

    @FXML
    private TextField txfDID;

    @FXML
    private TextField txfTID;
    public void initialize() {
        getViews();
    }
    public void refresh() {
        assignTeacherList.clear();
        getViews();
    }
    ObservableList<TeacherAssigned>assignTeacherList = FXCollections.observableArrayList();


    public void loadTableViews(){
        colATID.setCellValueFactory(new PropertyValueFactory<>("assign_id"));
        colTID.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
        ColCID.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        ColDI.setCellValueFactory(new PropertyValueFactory<>("department_id"));
        tableView.setItems(assignTeacherList);
        tableView.setRowFactory( tv -> {
            TableRow<TeacherAssigned> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  tableView.getSelectionModel().getSelectedIndex();

                    assigned_id = String.valueOf(tableView.getItems().get(myIndex).getAssign_id());
                    txfAID.setText(tableView.getItems().get(myIndex).getAssign_id());
                    txfTID.setText(tableView.getItems().get(myIndex).getTeacher_id());
                    txfCID.setText(Integer.toString(tableView.getItems().get(myIndex).getCourse_id()));
                    txfDID.setText(String.valueOf(tableView.getItems().get(myIndex).getDepartment_id()));




                }
            });
            return myRow;
        });


    }
    public void getViews(){
        try{
            connect=Database.connectDb();


            prepare=connect.prepareStatement("select * from assign_teacher");
            result=prepare.executeQuery();
            while (result.next()){
                assignTeacherList.add(new TeacherAssigned(
                        result.getString(1),
                        result.getString(2),
                        result.getInt(3),
                        result.getString(4)));
            }

            loadTableViews();
            connect.close();



        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void handleDelete(ActionEvent event) {
        myIndex = tableView.getSelectionModel().getSelectedIndex();
        assigned_id = String.valueOf(tableView.getItems().get(myIndex).getAssign_id());
        try{
            connect = Database.connectDb();
            prepare=connect.prepareStatement("delete from assign_teacher where assign_teacher_id=?");
            prepare.setString(1,assigned_id);
            prepare.executeUpdate();
            connect.close();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Teacher is not assigned any more deleted successfully!");
            alert.showAndWait();
            refresh();


        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    @FXML
    void handleRegister(ActionEvent event) {

        try{
            connect = Database.connectDb();
            prepare = connect.prepareStatement("insert into assign_teacher values(?,?,?,?)");
            prepare.setString(1,txfAID.getText());
            prepare.setString(2,txfTID.getText());
            prepare.setInt(3,Integer.parseInt(txfCID.getText()));
            prepare.setString(4,txfDID.getText());
            prepare.executeUpdate();
            connect.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Teacher assigned for Course successfully!");
            alert.showAndWait();
            refresh();



        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }


    @FXML
    void handleUpdate(ActionEvent event) {
        try {
            connect = Database.connectDb();
            prepare = connect.prepareStatement("update assign_teacher set teacher_id=?, course_id=?, department_id=? where assign_teacher_id=?");
            prepare.setString(1, txfTID.getText());
            prepare.setInt(2, Integer.parseInt(txfCID.getText()));
            prepare.setString(3, txfDID.getText());
            prepare.setString(4, assigned_id);
            prepare.executeUpdate();
            connect.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Teacher assignment updated successfully!");
            alert.showAndWait();
            refresh();
        } catch (SQLException ex) {
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

    @FXML
    void handleClear(ActionEvent event) {
       txfAID.clear();
       txfTID.clear();
       txfCID.clear();
       txfDID.clear();
    }


}

