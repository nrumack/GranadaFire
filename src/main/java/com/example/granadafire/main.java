package com.example.granadafire;

import com.example.granadafire.DAO.GameDAO;
import com.example.granadafire.entities.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        /*GameDAO gameDAO = new GameDAO();
        Game testGame = new Game();
        gameDAO.createGame(testGame);
        if(gameDAO.createGame(testGame)) {
            System.out.println("Saved game");
        } else {
            System.out.println("Failed to save game");
        }*/
        //FXMLLoader fxmlLoader1 = new FXMLLoader(main.class.getResource("mainMenuView.fxml"));
        //Scene mainMenuScene = new Scene(fxmlLoader1.load(), 500, 400);


        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("/views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}