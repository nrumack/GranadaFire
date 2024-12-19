package com.example.granadafire.controller.playerControllers;

import com.example.granadafire.DAO.GameDAO;
import com.example.granadafire.DAO.PlayerDAO;
import com.example.granadafire.controller.SceneSwitcher;
import com.example.granadafire.controller.generalControllers.loginController;
import com.example.granadafire.entities.Game;
import com.example.granadafire.entities.Player;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlayerSearchController {

    private final GameDAO gameDAO = new GameDAO();
    private final PlayerDAO playerDAO = new PlayerDAO();

    @FXML
    private ListView<Player> playerListView;

    @FXML
    private ListView<String> gameFilterListView;

    @FXML
    private TextField searchField;

    @FXML
    public void initialize() {
        List<Game> games = gameDAO.getAllGames();
        List<String> gameNames = new ArrayList<>();
        for (Game game : games) {
            gameNames.add(game.getGameName());
        }
        gameFilterListView.setItems(FXCollections.observableArrayList(gameNames));
        gameFilterListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        loadPlayers(null);

        playerListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Player selectedPlayer = playerListView.getSelectionModel().getSelectedItem();
                if (selectedPlayer != null) {
                    SceneSwitcher.switchScenes(event, "/views/playerViews/playerView.fxml", "Pied Pipers", selectedPlayer, PlayerController.class);
                }
            }
        });
    }

    @FXML
    private void filterPlayersByGames() {
        List<String> selectedGames = gameFilterListView.getSelectionModel().getSelectedItems();

        if (selectedGames.isEmpty()) {
            loadPlayers(null);
        } else {
            List<Game> selectedGameEntities = selectedGames.stream()
                    .map(gameDAO::getGameByName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            loadPlayersByGames(selectedGameEntities);
        }
    }

    private void loadPlayers(Game gameFilter) {
        List<Player> players;

        if (gameFilter == null) {
            players = playerDAO.getAllPlayers();
        } else {
            List<Game> singleGameList = List.of(gameFilter);
            players = playerDAO.getPlayersByGames(singleGameList);
        }

        playerListView.setItems(FXCollections.observableArrayList(players));
    }

    private void loadPlayersByGames(List<Game> games) {
        List<Player> players = playerDAO.getPlayersByGames(games);
        playerListView.setItems(FXCollections.observableArrayList(players));
    }

    @FXML
    private void searchPlayers() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadPlayers(null);
        } else {
            List<Player> filteredPlayers = playerDAO.getPlayersByName(searchTerm);
            playerListView.setItems(FXCollections.observableArrayList(filteredPlayers));
        }
    }

    @FXML
    public void backButton(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/mainMenuView.fxml", "Pied Pipers", null, PlayerController.class);
    }

    @FXML
    public void createNewButton(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/playerViews/playerView.fxml", "Pied Pipers", null, PlayerController.class);
    }
    @FXML
    protected void logout(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/loginView.fxml", "Pied Pipers", null, loginController.class);
    }
}

