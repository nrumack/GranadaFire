package com.example.granadafire.controller.teamControllers;

import com.example.granadafire.DAO.GameDAO;
import com.example.granadafire.DAO.PlayerDAO;
import com.example.granadafire.DAO.TeamDAO;
import com.example.granadafire.controller.SceneSwitcher;
import com.example.granadafire.controller.generalControllers.loginController;
import com.example.granadafire.entities.Game;
import com.example.granadafire.entities.Player;
import com.example.granadafire.entities.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;

//Created by Marc
public class TeamInfoController {

    PlayerDAO playerDAO = new PlayerDAO();
    TeamDAO teamDAO = new TeamDAO();
    GameDAO gameDAO = new GameDAO();

    @FXML
    List <Player> players;

    @FXML
    TextField teamName;

    @FXML
    TextField gameName;

    @FXML
    ListView<Player> playersInTeam;

    @FXML
    ListView<Player> availablePlayers;

    @FXML
    private Team selectedTeam;

    @FXML
    ListView<Game> gamesList;

    @FXML
    public void initialize() {
    }

    @FXML
    public void setGameName(Team team) {
        String gameName = team.getGame().getGameName();
        this.gameName.setText(gameName);
    }

    @FXML
    public void setTeamName(Team team) {
        String teamName = team.getTeamName();
        this.teamName.setText(teamName);
        teamDAO.updateTeam(team);
    }

    @FXML
    public void updateButton() {
        String newName = teamName.getText();
        teamName.setText(newName);
        selectedTeam.setTeamName(newName);
        teamDAO.updateTeam(selectedTeam);

        Game selectedGame = gamesList.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            selectedTeam.setGame(selectedGame);
            gameDAO.updateGame(selectedGame);
            setGameName(selectedTeam);
            teamDAO.updateTeam(selectedTeam);
        }
    }

    @FXML
    public void setPlayersInTeam() {
        if (selectedTeam != null) {
            List<Player> players = playerDAO.getPlayersByTeam(selectedTeam);
            playersInTeam.getItems().addAll(players);
        }
    }

    @FXML
    public void setGamesList() {
        List<Game> games = gameDAO.getAllGames();
        gamesList.getItems().addAll(games);
    }

    @FXML
    public void setAvailablePlayers() {
        List<Player> players = playerDAO.getAllPlayers();
        for (Player player: players)
            if (player.getTeam() == null)
                availablePlayers.getItems().add(player);
    }

    public void addAPlayer() {
        Player player = availablePlayers.getSelectionModel().getSelectedItem();
            if (player != null) {
                playersInTeam.getItems().add(player);
                player.setTeam(selectedTeam);
                playerDAO.updatePlayer(player);
                availablePlayers.getItems().remove(player);
                availablePlayers.refresh();
                playersInTeam.refresh();
            }
    }

    public void removePlayer() {
        Player player = playersInTeam.getSelectionModel().getSelectedItem();
        if (player != null) {
            playersInTeam.getItems().remove(player);
            availablePlayers.getItems().add(player);
            player.setTeam(null);
            playerDAO.updatePlayer(player);
            playersInTeam.getItems().remove(player);
            availablePlayers.refresh();
            playersInTeam.refresh();
        }
    }

    @FXML
    public void goBackToTeamSearch(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/teamViews/teamSearchView.fxml"));
            Scene newScene = new Scene(loader.load());
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.setTitle("Pied Pipers");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void logout(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/loginView.fxml", "Pied Pipers", null, loginController.class);
    }

    public void setSelectedTeam(Team team) {
        this.selectedTeam = team;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}