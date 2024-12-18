package com.example.granadafire.controller.teamControllers;

import com.example.granadafire.DAO.PlayerDAO;
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

public class TeamInfoController {

    PlayerDAO playerDAO = new PlayerDAO();

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
    }

    @FXML
    public void setPlayersInTeam() {
        if (selectedTeam != null) {
            List<Player> players = playerDAO.getPlayersByTeam(selectedTeam);
            playersInTeam.getItems().addAll(players);
        } else {
            System.out.println("selectedTeam is null @ setPlayersInTeam");
        }
    }

    @FXML
    public void setAvailablePlayers() {
        List<Player> players = playerDAO.getAllPlayers();
        for (Player player: players)
            if (player.getTeam() == null)
                availablePlayers.getItems().add(player);
    }

    public void addAPlayer(ActionEvent event) {

    }

    public void removePlayer(ActionEvent event) {

    }

    @FXML
    public void goBackToTeamSearch(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/teamViews/teamSearchView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Teams");
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSelectedTeam(Team team){
        this.selectedTeam = team;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}