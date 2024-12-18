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
    private Team selectedTeam;

    @FXML
    public void initialize() {
        setSelectedTeam(selectedTeam);
        if (selectedTeam != null) {
            System.out.println(selectedTeam + "it works!!!!!!!!!!!!!!!!!");
            List<Player> players = playerDAO.getPlayersByTeam(selectedTeam);
            playersInTeam.getItems().addAll(players);
        } else {
            System.out.println("initialize method is null");
        }
    }

    @FXML
    public void goBackToTeamSearch(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/teamViews/teamSearchView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Main Menu");
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSelectedTeam(Team team){
        this.selectedTeam = team;
        System.out.println(selectedTeam + " - team set");
    }

    public PlayerDAO getPlayerDAO() {
        return playerDAO;
    }

    public void setPlayerDAO(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}