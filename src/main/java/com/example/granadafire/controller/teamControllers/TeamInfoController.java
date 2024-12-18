package com.example.granadafire.controller.teamControllers;

import com.example.granadafire.DAO.GameDAO;
import com.example.granadafire.DAO.PlayerDAO;
import com.example.granadafire.DAO.TeamDAO;
import com.example.granadafire.entities.Game;
import com.example.granadafire.entities.Player;
import com.example.granadafire.entities.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TeamInfoController implements Initializable {
    PlayerDAO playerDAO = new PlayerDAO();
    TeamDAO teamDAO = new TeamDAO();
    GameDAO gameDAO = new GameDAO();

    @FXML
    List <Player> players;

    @FXML
    Team team;

    @FXML
    Game game;

    @FXML
    TextField teamName;

    @FXML
    TextField gameName;

    @FXML
    ListView<Player> playersInTeam;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        players = playerDAO.getAllPlayers();

        playersInTeam.getItems().addAll(players);
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

    public void setTeamName(String name){
        this.teamName.setText(name);
    }

    public void setGameName(String name){
        this.gameName.setText(name);
    }

    public void setTeam(Team team){
        this.team = team;
    }

    public void setPlayersInTeam(List<Player> playersInTeam){
    this.players = playersInTeam;
    }


}