package com.example.granadafire.controller.teamControllers;

import com.example.granadafire.DAO.GameDAO;
import com.example.granadafire.DAO.TeamDAO;
import com.example.granadafire.controller.SceneSwitcher;
import com.example.granadafire.controller.generalControllers.loginController;
import com.example.granadafire.entities.Game;
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

public class CreateTeamController implements Initializable {

    TeamDAO teamDAO = new TeamDAO();
    GameDAO gameDAO = new GameDAO();

    @FXML
    private TextField teamNameBox;

    @FXML
    private ListView<String> gameListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Game> games = gameDAO.getAllGames();
        List<String> gameNames = new ArrayList<>();
        for (Game game : games) {
            gameNames.add(game.getGameName());
        }
        gameListView.getItems().addAll(gameNames);
    }

    @FXML
    protected void clickBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/teamViews/teamSearchView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Pied Pipers");
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void clickAddTeamButton(ActionEvent event) {
        try {
            String selectedGameName = gameListView.getSelectionModel().getSelectedItem();
                if (selectedGameName != null) {
                    Game game = gameDAO.getGameByName(selectedGameName);
                    String teamName = teamNameBox.getText();
                    Team team = new Team(teamName, game);
                    teamDAO.saveTeam(team);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/teamViews/teamSearchView.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(loader.load()));
                    stage.setTitle("Pied Pipers");
                    stage.show();
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                    }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void logout(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/loginView.fxml", "Pied Pipers", null, loginController.class);
    }
}