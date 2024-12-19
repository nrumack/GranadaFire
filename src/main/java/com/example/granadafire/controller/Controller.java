package com.example.granadafire.controller;

import com.example.granadafire.controller.gameControllers.GameSearchController;
import com.example.granadafire.controller.generalControllers.loginController;
import com.example.granadafire.controller.matchControllers.MatchController;
import com.example.granadafire.controller.playerControllers.PlayerSearchController;
import com.example.granadafire.controller.staffControllers.StaffSeachController;
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
        SceneSwitcher.switchScenes(event, "/views/teamViews/mainMenuView.fxml", "Pied Pipers", null, Controller.class);
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