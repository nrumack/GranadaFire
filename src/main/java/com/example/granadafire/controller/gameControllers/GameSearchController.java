package com.example.granadafire.controller.gameControllers;

import com.example.granadafire.DAO.GameDAO;
import com.example.granadafire.controller.MenuController;
import com.example.granadafire.controller.SceneSwitcher;
import com.example.granadafire.controller.generalControllers.loginController;
import com.example.granadafire.entities.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.List;
//Created by Nicho/Martin
public class GameSearchController {
    private GameDAO gameDAO = new GameDAO();

    @FXML
    private TextField searchField;
    @FXML
    private ListView<Game> gameResultList;

    @FXML
    public void initialize() {
        List<Game> game = gameDAO.getAllGames();
        ObservableList<Game> observableStaff = FXCollections.observableArrayList(game);

        gameResultList.setItems(observableStaff);
        gameResultList.setOnMouseClicked(this::handleClick);
    }

    private void handleClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Game selectedItem = gameResultList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                SceneSwitcher.switchScenes(event, "/views/gameViews/gameView.fxml", "Game View", selectedItem, getClass());
            }
        }
    }

    @FXML
    public void searchGames() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            List<Game> allGames = gameDAO.getAllGames();
            gameResultList.setItems(FXCollections.observableArrayList(allGames));
        } else {
            List<Game> filteredGame = gameDAO.getGamesByName(keyword);
            gameResultList.setItems(FXCollections.observableArrayList(filteredGame));
        }
    }

    public void backButton(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/mainMenuView.fxml", "Pied Pipers", null, MenuController.class);
    }

    public void createNewButton(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/gameViews/gameView.fxml", "Pied Pipers", null, GameController.class);
    }

    @FXML
    protected void logout(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/loginView.fxml", "Pied Pipers", null, loginController.class);
    }
}
