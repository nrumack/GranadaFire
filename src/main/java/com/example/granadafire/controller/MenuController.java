package com.example.granadafire.controller;

import com.example.granadafire.controller.gameControllers.GameSearchController;
import com.example.granadafire.controller.generalControllers.loginController;
import com.example.granadafire.controller.matchControllers.MatchController;
import com.example.granadafire.controller.playerControllers.PlayerSearchController;
import com.example.granadafire.controller.staffControllers.StaffSeachController;
import com.example.granadafire.controller.teamControllers.TeamSearchController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    protected void clickPlayers(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/playerViews/playerSearchView.fxml", "Pied Pipers", null, PlayerSearchController.class);
    }

    @FXML
    protected void clickTeams(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/teamViews/teamSearchView.fxml", "Pied Pipers", null, TeamSearchController.class);
    }

    @FXML
    protected void clickGames(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/gameViews/gameSearchView.fxml", "Pied Pipers", null, GameSearchController.class);
    }

    @FXML
    protected void clickMatches(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/matchViews/matchView.fxml", "Pied Pipers", null, MatchController.class);
    }

    @FXML
    protected void clickStaff(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/staffViews/staffSearchView.fxml", "Pied Pipers", null, StaffSeachController.class);
    }
    @FXML
    protected void logout(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/loginView.fxml", "Pied Pipers", null, loginController.class);
    }
}