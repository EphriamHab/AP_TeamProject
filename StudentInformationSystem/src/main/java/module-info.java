module com.example.studentinformationsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.studentinformationsystem to javafx.fxml;
    exports com.example.studentinformationsystem;
}