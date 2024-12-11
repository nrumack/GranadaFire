module com.example.granadafire {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens com.example.granadafire.entities to org.hibernate.orm.core;
    opens com.example.granadafire to javafx.fxml;
    exports com.example.granadafire;
    exports com.example.granadafire.controller;
    opens com.example.granadafire.controller to javafx.fxml;
}