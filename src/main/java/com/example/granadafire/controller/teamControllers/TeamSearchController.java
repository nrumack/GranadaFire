package com.example.granadafire.controller.teamControllers;

import com.example.granadafire.DAO.TeamDAO;
import com.example.granadafire.entities.Team;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import java.util.List;
import java.util.ResourceBundle;

public class TeamSearchController implements Initializable {

    TeamDAO teamDAO = new TeamDAO();

    @FXML
    private ListView<Team> teamListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Team> teams = teamDAO.getAllTeams();
        teamListView.setItems(FXCollections.observableArrayList(teams));
    }

    public void deleteTeam() {
        try {
            Team selectedTeamName = teamListView.getSelectionModel().getSelectedItem();
            if (selectedTeamName != null) {
                Team team = teamDAO.getTeamByName(selectedTeamName.getTeamName());
                teamDAO.deleteTeam(team.getTeamId());
                teamListView.getSelectionModel().clearSelection();
                teamListView.getItems().remove(selectedTeamName);
            }
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    /*public void editTeam() {
        try {
            Team selectedTeam = teamListView.getSelectionModel().getSelectedItem();
            if (selectedTeam != null) {
                openTeamInfo(selectedTeam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void editTeam() {
        try {
            ObservableValue<Team> selectedTeamObservable = teamListView.getSelectionModel().selectedItemProperty();
            Team selectedTeam = selectedTeamObservable.getValue();

            if (selectedTeam != null) {
                openTeamInfo(selectedTeam);
            } else {
                System.out.println("edit Team method is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void openTeamInfo(Team team) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/teamViews/teamInfoView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            TeamInfoController controller = loader.getController();
            controller.setSelectedTeam(team);
            stage.setTitle("Team Info");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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