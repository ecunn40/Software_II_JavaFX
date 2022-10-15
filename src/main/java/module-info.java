module com.example.software_ii_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens controller to javafx.fxml;
    exports controller;
    exports main;
    opens main to javafx.fxml;
}