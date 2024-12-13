package com.example.granadafire.controller.playerControllers;

import com.example.granadafire.DAO.GameDAO;
import com.example.granadafire.DAO.PlayerDAO;
import com.example.granadafire.entities.Game;
import com.example.granadafire.entities.Player;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx. scene. input. MouseEvent;
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
            if (event.getClickCount() == 2) {
                Player selectedPlayer = playerListView.getSelectionModel().getSelectedItem();
                if (selectedPlayer != null) {
                    openPlayerDetailView(selectedPlayer, event);
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

    private void openPlayerDetailView(Player player, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playerViews/playerView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            PlayerController controller = loader.getController();
            controller.setPlayer(player);

            stage.setTitle("Player Details");
            stage.show();

            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void backButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/generalViews/mainMenuView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Main Menu");
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void createNewButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playerViews/playerView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Create Player");
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

