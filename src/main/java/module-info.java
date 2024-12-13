module com.example.granadafire {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;

    opens com.example.granadafire.entities to org.hibernate.orm.core;
    opens com.example.granadafire to javafx.fxml;
    exports com.example.granadafire;
    exports com.example.granadafire.controller;
    opens com.example.granadafire.controller to javafx.fxml;
    exports com.example.granadafire.controller.playerControllers;
    opens com.example.granadafire.controller.playerControllers to javafx.fxml;
    exports com.example.granadafire.controller.staffControllers;
    opens com.example.granadafire.controller.staffControllers to javafx.fxml;
    exports com.example.granadafire.controller.generalControllers;
    opens com.example.granadafire.controller.generalControllers to javafx.fxml;
    exports com.example.granadafire.controller.matchControllers;
    opens com.example.granadafire.controller.matchControllers to javafx.fxml;
    exports com.example.granadafire.controller.teamControllers;
    opens com.example.granadafire.controller.teamControllers to javafx.fxml;
    exports com.example.granadafire.controller.gameControllers;
    opens com.example.granadafire.controller.gameControllers to javafx.fxml;
}