package com.example.granadafire;

import com.example.granadafire.DAO.GameDAO;
import com.example.granadafire.entities.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        GameDAO gameDAO = new GameDAO();
        Game testGame = new Game();
        if(gameDAO.saveGame(testGame)) {
            System.out.println("Saved game");
        } else {
            System.out.println("Failed to save game");
        }


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}