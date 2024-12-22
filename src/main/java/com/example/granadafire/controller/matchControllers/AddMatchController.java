package com.example.granadafire.controller.matchControllers;

import com.example.granadafire.DAO.MatchDAO;
import com.example.granadafire.DAO.PlayerDAO;
import com.example.granadafire.DAO.TeamDAO;
import com.example.granadafire.controller.SceneSwitcher;
import com.example.granadafire.controller.generalControllers.loginController;
import com.example.granadafire.entities.Match;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
//Created by Martin
public class AddMatchController {

    @FXML
    private DatePicker matchDatePicker;

    @FXML
    private TextField matchTimeField;

    @FXML
    private ComboBox<String> player1ComboBox;

    @FXML
    private ComboBox<String> player2ComboBox;


    private final MatchDAO matchDAO = new MatchDAO();
    private final PlayerDAO playerDAO = new PlayerDAO();
    private final TeamDAO teamDAO = new TeamDAO();

    @FXML
    public void initialize() {
        List<String> players = FXCollections.observableArrayList();
        playerDAO.getAllPlayers().forEach(player -> players.add(player.getFirstName() + " " + player.getLastName()));
        teamDAO.getAllTeams().forEach(team -> players.add(team.getTeamName()));

        player1ComboBox.setItems(FXCollections.observableArrayList(players));
        player2ComboBox.setItems(FXCollections.observableArrayList(players));
    }

    @FXML
    public void saveMatch() {
        try {
            LocalDate date = matchDatePicker.getValue();
            LocalTime time = LocalTime.parse(matchTimeField.getText());
            LocalDateTime matchDateTime = LocalDateTime.of(date, time);

            String player1 = player1ComboBox.getValue();
            String player2 = player2ComboBox.getValue();

            if (player1 == null || player2 == null) {
                return;
            }

            Match match = new Match();
            match.setMatchDate(matchDateTime);
            match.setScore1(null);
            match.setScore2(null);
            match.setResult(null);

            playerDAO.getAllPlayers().stream()
                    .filter(player -> (player.getFirstName() + " " + player.getLastName()).equals(player1))
                    .findFirst()
                    .ifPresent(match::setPlayer1);

            teamDAO.getAllTeams().stream()
                    .filter(team -> team.getTeamName().equals(player1))
                    .findFirst()
                    .ifPresent(match::setTeam1);

            playerDAO.getAllPlayers().stream()
                    .filter(player -> (player.getFirstName() + " " + player.getLastName()).equals(player2))
                    .findFirst()
                    .ifPresent(match::setPlayer2);

            teamDAO.getAllTeams().stream()
                    .filter(team -> team.getTeamName().equals(player2))
                    .findFirst()
                    .ifPresent(match::setTeam2);

            matchDAO.addMatch(match);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            goBack();
        }
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/matchViews/matchView.fxml"));
            Stage stage = (Stage) matchDatePicker.getScene().getWindow();
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Pied Pipers");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void logout(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/loginView.fxml", "Pied Pipers", null, loginController.class);
    }
}

