package com.example.granadafire.controller;

import com.example.granadafire.controller.gameControllers.GameController;
import com.example.granadafire.controller.gameControllers.GameSearchController;
import com.example.granadafire.controller.matchControllers.MatchController;
import com.example.granadafire.controller.playerControllers.PlayerController;
import com.example.granadafire.controller.playerControllers.PlayerSearchController;
import com.example.granadafire.controller.staffControllers.StaffSeachController;
import com.example.granadafire.controller.teamControllers.TeamController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {

    @FXML
    protected void clickPlayers(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/playerViews/playerSearchView.fxml", "Pied Pipers", null, PlayerSearchController.class);
    }

    @FXML
    protected void clickTeams(ActionEvent event) {

        SceneSwitcher.switchScenes(event, "/views/gameViews/gameView.fxml", "Pied Pipers", null, TeamController.class);
    }

    @FXML
    protected void clickGames(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/gameViews/gameSearchView.fxml", "Pied Pipers", null, GameSearchController.class);
    }

    @FXML
    protected void clickMatches(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/matchesView/matchView.fxml", "Pied Pipers", null, MatchController.class);
    }

    @FXML
    protected void clickStaff(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/staffViews/staffSearchView.fxml", "Pied Pipers", null, StaffSeachController.class);
    }
}