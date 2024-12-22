package com.example.granadafire.controller.teamControllers;

import com.example.granadafire.DAO.TeamDAO;
import com.example.granadafire.controller.SceneSwitcher;
import com.example.granadafire.controller.generalControllers.loginController;
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
//Created by Marc
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

    @FXML
    public void editTeam(ActionEvent event) {
        try {
            Team selectedTeam = teamListView.getSelectionModel().getSelectedItem();

            if (selectedTeam != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/teamViews/teamInfoView.fxml"));
                Scene newScene = new Scene(loader.load());
                TeamInfoController controller = loader.getController();
                controller.setSelectedTeam(selectedTeam);
                controller.setGameName(selectedTeam);
                controller.setTeamName(selectedTeam);
                controller.setPlayersInTeam();
                controller.setAvailablePlayers();
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(newScene);
                currentStage.setTitle("Pied Pipers");
            } else {
                System.out.println("No team selected.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void clickBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/generalViews/mainMenuView.fxml"));
            Scene newScene = new Scene(loader.load());
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.setTitle("Pied Pipers");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void createNewTeam(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/teamViews/teamView.fxml"));
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
}