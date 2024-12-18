package com.example.granadafire.controller.gameControllers;

import com.example.granadafire.DAO.GameDAO;
import com.example.granadafire.controller.SceneSwitcher;
import com.example.granadafire.entities.Game;
import com.example.granadafire.util.DataReciever;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GameController implements DataReciever {
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

    private Game selectedGame;

    @FXML
    public void initialize() {
        toggleButtonsVisibility();
    }

    @Override
    public void recieveData(Object data) {
        if (data instanceof Game) {
            this.selectedGame = (Game) data;
            setGame(this.selectedGame);
            updateView();
        }
    }

    public void setGame(Game selectedGame) {
        this.selectedGame = selectedGame;
        updateView();
        toggleButtonsVisibility();
    }

    private void toggleButtonsVisibility() {
        if (selectedGame != null && selectedGame.getGameId() > 0) {
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
        if (selectedGame != null) {
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
        if (selectedGame != null) {
            gameNameField.setText(selectedGame.getGameName());
        }
    }

    @FXML
    public void backButton(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/gameViews/gameSearchView.fxml", "Pied Pipers", null, GameSearchController.class);
    }

    @FXML
    public void deleteButton(ActionEvent event) {
        try {
            if (selectedGame != null) {
                gameDAO.deleteGame(selectedGame);
                clearFields();
                SceneSwitcher.switchScenes(event, "/views/gameViews/gameSearchView.fxml", "Pied Pipers", null, GameSearchController.class);
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

            if (gameNameInput.isEmpty()) {
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

            if (gameNameInput.isEmpty()) {
                showError(gameNameField, "Name of game is required*");
            }

            if (gameNameInput.isEmpty()) {
                return;
            }

            Game existingGame = gameDAO.getGameByName(selectedGame.getGameName());

            if (existingGame != null) {
                existingGame.setGameName(gameNameInput);

                gameDAO.updateGame(existingGame);

                SceneSwitcher.switchScenes(event, "/views/gameViews/gameSearchView.fxml", "Pied Pipers", null, GameSearchController.class);
            } else {
                System.out.println("game not valid");
            }
        } catch (Exception e) {
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