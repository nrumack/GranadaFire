package com.example.granadafire.controller.teamControllers;

import com.example.granadafire.DAO.TeamDAO;
import com.example.granadafire.entities.Game;
import com.example.granadafire.entities.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TeamSearchController implements Initializable {

    TeamDAO teamDAO = new TeamDAO();

    @FXML
    private ListView<String> listView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Team> teams = teamDAO.getAllTeams();
        List<String> teamNames = new ArrayList<>();
        for (Team team : teams) {
            teamNames.add(team.getTeamName());
        }
                   listView.getItems().addAll(teamNames);

    }

    public void deleteTeam() {
        try {
            String selectedTeamName = listView.getSelectionModel().getSelectedItem();
            if (selectedTeamName != null) {
                Team team = teamDAO.getTeamByName(selectedTeamName);
                teamDAO.deleteTeam(team.getTeamId());
                listView.getSelectionModel().clearSelection();
                listView.getItems().remove(selectedTeamName);
            }
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    public void editTeam() {
            String selectedTeamName = listView.getSelectionModel().getSelectedItem();
            if (selectedTeamName != null) {
                Team theTeam = teamDAO.getTeamByName(selectedTeamName);

                Game game = theTeam.getGame();
                String theGameName = game.getGameName();
                openTeamInfo(selectedTeamName, theGameName);
            }
    }

    public void openTeamInfo(String selectedTeamName, String theGameName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/teamViews/teamInfoView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            TeamInfoController controller = loader.getController();
            controller.setTeamName(selectedTeamName);
            controller.setGameName(theGameName);
            stage.setTitle("Team Info");
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    /*@FXML
    ObservableList<String> names = FXCollections.observableArrayList(
            "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");


    @FXML
    private ListView<Team> teamsList;

    @FXML
    public void listTeams(){
        List<Team> teams = teamDAO.getAllTeams();
    }*/

    //String[] names = names.get().toArray(new String[0]);
    //String[] items = {"java", "python"};




    /*When staff manage teams, they must be able to add and remove existing teams
    player profiles in the team. A team is always tied to a certain game and a player only gets
    be part of a team.*/

    //När personalen listar lag ska det synas vilket spel laget tävlar inom.


    @FXML
    protected void clickBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/generalViews/mainMenuView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Piper Games");
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void createNewTeam(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/teamViews/teamView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Piper Games");
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}