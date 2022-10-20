module com.example.software_ii_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens controller to javafx.fxml;
    exports controller;
    exports main;
    opens main to javafx.fxml;
    exports abstractions;
    opens abstractions to javafx.fxml;
    exports firstleveldivisions;
    opens firstleveldivisions to javafx.fxml;
    exports countries;
    opens countries to javafx.fxml;
}