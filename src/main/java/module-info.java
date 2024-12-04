module com.example.granadafire {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;


    opens com.example.granadafire to javafx.fxml;
    exports com.example.granadafire;
}