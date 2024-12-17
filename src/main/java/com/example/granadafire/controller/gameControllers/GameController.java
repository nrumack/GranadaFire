package com.example.granadafire.controller.gameControllers;

import com.example.granadafire.DAO.GameDAO;
import com.example.granadafire.entities.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GameController {
    GameDAO gameDAO = new GameDAO();

    public TextField gameNameField;

    @FXML
    private Button deleteButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button updateGame;
    @FXML
    private Button saveGame;

    private Game game;

    @FXML
    public void initialize() {
        toggleButtonsVisibility();
    }

    public void setGame(Game game) {
        this.game = game;
        updateView();
        toggleButtonsVisibility();
    }

    private void toggleButtonsVisibility() {
        if (game != null && game.getGameId() > 0) {
            deleteButton.setVisible(true);
            deleteButton.setManaged(true);
            deleteButton.setDisable(false);
            updateGame.setVisible(true);
            updateGame.setManaged(true);
            updateGame.setDisable(false);
        } else {
            deleteButton.setVisible(false);
            deleteButton.setManaged(false);
            deleteButton.setDisable(true);
            updateGame.setVisible(false);
            updateGame.setManaged(false);
            updateGame.setDisable(true);
            updateGame.setStyle("-fx-background-color: C0BCBC");
        }
        if (game != null) {
            clearButton.setVisible(false);
            clearButton.setManaged(false);
            clearButton.setDisable(true);
            saveGame.setVisible(false);
            saveGame.setManaged(false);
            saveGame.setDisable(true);
        } else {
            clearButton.setVisible(true);
            clearButton.setManaged(true);
            clearButton.setDisable(false);
            clearButton.setStyle("-fx-background-color: C0BCBC");
            saveGame.setVisible(true);
            saveGame.setManaged(true);
            saveGame.setDisable(false);
            saveGame.setStyle("-fx-background-color: C0BCBC");
        }
    }

    @FXML
    private void clearButton(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        gameNameField.clear();
    }

    private void updateView() {
        if (game != null) {
            gameNameField.setText(game.getGameName());
        }
    }

    @FXML
    public void backButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gameViews/gameSearchView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Pied Pipers");
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteButton(ActionEvent event) {
        try {
            if (game != null) {
                gameDAO.deleteGame(game);
                clearFields();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gameViews/gameSearchView.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Pied Pipers");
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    public void saveGame() {
        try {
            String gameNameInput = gameNameField.getText();
            resetErrorsStyles();

            if (gameNameInput.isEmpty()){
                showError(gameNameField, "Game Name is required");
            }

            if (gameNameInput.isEmpty()) {
                return;
            }

            Game newGame = new Game(gameNameInput);
            gameDAO.createGame(newGame);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void updateGame(ActionEvent event) {
        try {
            String gameNameInput = gameNameField.getText();
            resetErrorsStyles();

            if (gameNameInput.isEmpty()){
                showError(gameNameField, "Name of game is required*");
            }

            if (gameNameInput.isEmpty()) {
                return;
            }

            Game existingGame = gameDAO.getGameByName(game.getGameName());

            if (existingGame != null) {
                existingGame.setGameName(gameNameInput);

                gameDAO.updateGame(existingGame);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gameViews/gameSearchView.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Pied Pipers");
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } else {
                System.out.println("game not valid");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    private void showError(TextField field, String message) {
        field.setStyle("-fx-border-color: red");
        field.setPromptText(message);
        field.setStyle("-fx-prompt-atext-fill: red;");
    }

    private void resetErrorsStyles() {
        gameNameField.setStyle("");
        gameNameField.setPromptText("First Name");
    }

}