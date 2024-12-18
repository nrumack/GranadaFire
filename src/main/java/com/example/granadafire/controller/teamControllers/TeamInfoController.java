package com.example.granadafire.controller.teamControllers;

import com.example.granadafire.DAO.PlayerDAO;
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
    public Team selectedTeam;

    @FXML
    public void initialize() {
        setSelectedTeam(selectedTeam);
        if (selectedTeam != null) {
            System.out.println(selectedTeam + "it works!!!!!!!!!!!!!!!!!");
            players = playerDAO.getPlayersByTeam(selectedTeam);
            playersInTeam.getItems().addAll(players);
        } else {
            System.out.println("no team selected");
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

    public Team getSelectedTeam() {
        return selectedTeam;
    }
}