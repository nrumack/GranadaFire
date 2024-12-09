module com.example.granadafire {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires rt;


    opens com.example.granadafire to javafx.fxml;
    exports com.example.granadafire;
}