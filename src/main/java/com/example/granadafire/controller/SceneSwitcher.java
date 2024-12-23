package com.example.granadafire.controller;

import javafx.scene.Parent;
import com.example.granadafire.util.DataReciever;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
// created by Nicholas
public class SceneSwitcher {
    public static void switchScenes(Event event, String fxmlFile, String title, Object data, Class<?> callingClass) {
        try {
            FXMLLoader loader = new FXMLLoader(callingClass.getResource(fxmlFile));

            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            Parent root = loader.load();
            Object controller = loader.getController();

            if (controller instanceof DataReciever) {
                ((DataReciever) controller).recieveData(data);
            }

            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
