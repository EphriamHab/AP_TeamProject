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



public class ManageEnrollementController {
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    private int myIndex;
    private String enrollment_id;
    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEnroll;

    @FXML
    private Button btnUpdate;
    @FXML
    private TableView<Enrollement> enrollTable;
    @FXML
    private TableColumn<Enrollement, Integer> colCId;

    @FXML
    private TableColumn<Enrollement, Date> colEd;

    @FXML
    private TableColumn<Enrollement,String> colEnId;

    @FXML
    private TableColumn<Enrollement, String> colSID;

    @FXML
    private TextField txfCId;

    @FXML
    private TextField txfEnID;

    @FXML
    private DatePicker txfEndate;

    @FXML
    private TextField txfStudId;

    ObservableList<Enrollement> enrollList = FXCollections.observableArrayList();

    public void initialize() {
        getViews();
    }
    public void refresh() {
        enrollList.clear();
        getViews();
    }



    public void loadTableViews(){
        colEnId.setCellValueFactory(new PropertyValueFactory<>("Enroll_id"));
        colSID.setCellValueFactory(new PropertyValueFactory<>("Student_id"));
        colCId.setCellValueFactory(new PropertyValueFactory<>("Course_id"));
        colEd.setCellValueFactory(new PropertyValueFactory<>("Enroll_date"));

        enrollTable.setItems(enrollList);
        enrollTable.setRowFactory( tv -> {
            TableRow<Enrollement> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  enrollTable.getSelectionModel().getSelectedIndex();

                    enrollment_id = String.valueOf(enrollTable.getItems().get(myIndex).getEnroll_id());
                    txfEnID.setText(enrollTable.getItems().get(myIndex).getEnroll_id());
                    txfStudId.setText(enrollTable.getItems().get(myIndex).getStudent_id());
                    txfCId.setText(String.valueOf(enrollTable.getItems().get(myIndex).getCourse_id()));
                    txfEndate.setValue(enrollTable.getItems().get(myIndex).getEnroll_date().toLocalDate());



                }
            });
            return myRow;
        });

    }
    public void getViews(){
        try{
            connect=Database.connectDb();


            prepare=connect.prepareStatement("select * from enrollment");
            result=prepare.executeQuery();
            while (result.next()){
                enrollList.add(new Enrollement(
                        result.getString(1),
                        result.getString(2),
                        result.getInt(3),
                        result.getDate(4)));
            }

            loadTableViews();
            connect.close();



        }catch (SQLException ex){
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
    void handleDelete(ActionEvent event) {
        myIndex = enrollTable.getSelectionModel().getSelectedIndex();
        enrollment_id = String.valueOf(enrollTable.getItems().get(myIndex).getEnroll_id());
        try{
            connect = Database.connectDb();
            prepare=connect.prepareStatement("delete from enrollment where enrollment_id=?");
            prepare.setString(1,enrollment_id);
            prepare.executeUpdate();
            connect.close();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Student deleted successfully!");
            alert.showAndWait();
            refresh();


        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    @FXML
    void handleEnrollement(ActionEvent event) {
        try{
            connect = Database.connectDb();
            prepare = connect.prepareStatement("insert into enrollment values(?,?,?,?)");
            prepare.setString(1,txfEnID.getText());
            prepare.setString(2,txfStudId.getText());
            prepare.setInt(3,Integer.parseInt(txfCId.getText()));
            prepare.setDate(4,Date.valueOf(txfEndate.getValue()));
            prepare.executeUpdate();
            connect.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Student Enrolled for Course successfully!");
            alert.showAndWait();
            refresh();



        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }
    @FXML
    void handleClear(ActionEvent event){
        txfEnID.clear();
        txfCId.clear();
        txfStudId.clear();
    }




}



