package com.example.granadafire.controller.matchControllers;

import com.example.granadafire.DAO.MatchDAO;
import com.example.granadafire.DAO.PlayerDAO;
import com.example.granadafire.DAO.TeamDAO;
import com.example.granadafire.controller.SceneSwitcher;
import com.example.granadafire.entities.Match;
import com.example.granadafire.util.DataReciever;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MatchEditController implements DataReciever {

    private Match selectedMatch;
    TeamDAO teamDAO = new TeamDAO();
    PlayerDAO playerDAO = new PlayerDAO();
    MatchDAO matchDAO = new MatchDAO();


    @FXML
    private ComboBox<String> participant1ComboBox;

    @FXML
    private ComboBox<String> participant2ComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeField;



    @FXML
    public void initialize() {
        participant1ComboBox.setItems(FXCollections.observableArrayList(loadParticipants()));
        participant2ComboBox.setItems(FXCollections.observableArrayList(loadParticipants()));
    }

    private List<String> loadParticipants() {
        List<String> participants = new ArrayList<>();
        teamDAO.getAllTeams().forEach(team -> participants.add(team.getTeamName()));
        playerDAO.getAllPlayers().forEach(player -> participants.add(player.getFirstName() + " " + player.getLastName()));
        return participants;
    }

    @FXML
    public void saveMatch(ActionEvent event) {
        try {
            LocalDate date = datePicker.getValue();
            LocalTime time = LocalTime.parse(timeField.getText());
            LocalDateTime matchDateTime = LocalDateTime.of(date, time);

            String participant1 = participant1ComboBox.getValue();
            String participant2 = participant2ComboBox.getValue();

            if (participant1 == null || participant2 == null) {
                return;
            }
            selectedMatch.setPlayer1(null);
            selectedMatch.setPlayer2(null);
            selectedMatch.setTeam1(null);
            selectedMatch.setTeam2(null);

            selectedMatch.setMatchDate(matchDateTime);

            playerDAO.getAllPlayers().stream()
                    .filter(player -> (player.getFirstName() + " " + player.getLastName()).equals(participant1))
                    .findFirst()
                    .ifPresent(selectedMatch::setPlayer1);

            teamDAO.getAllTeams().stream()
                    .filter(team -> team.getTeamName().equals(participant1))
                    .findFirst()
                    .ifPresent(selectedMatch::setTeam1);

            playerDAO.getAllPlayers().stream()
                    .filter(player -> (player.getFirstName() + " " + player.getLastName()).equals(participant2))
                    .findFirst()
                    .ifPresent(selectedMatch::setPlayer2);

            teamDAO.getAllTeams().stream()
                    .filter(team -> team.getTeamName().equals(participant2))
                    .findFirst()
                    .ifPresent(selectedMatch::setTeam2);

            matchDAO.updateMatch(selectedMatch);
        } finally {
            goBack(event);
        }
    }

    @FXML
    public void goBack(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/matchViews/matchView.fxml", "Matches", null, MatchController.class);
    }

    @Override
    public void recieveData(Object data) {
        if (data instanceof Match) {
            this.selectedMatch = (Match) data;

            datePicker.setValue(selectedMatch.getMatchDateRaw().toLocalDate());
            timeField.setText(selectedMatch.getMatchDateRaw().toLocalTime().toString());

            if (selectedMatch.getPlayer1() != null) {
                participant1ComboBox.setValue(selectedMatch.getPlayer1().getFirstName() + " " + selectedMatch.getPlayer1().getLastName());
            } else if (selectedMatch.getTeam1() != null) {
                participant1ComboBox.setValue(selectedMatch.getTeam1().getTeamName());
            }

            if (selectedMatch.getPlayer2() != null) {
                participant2ComboBox.setValue(selectedMatch.getPlayer2().getFirstName() + " " + selectedMatch.getPlayer2().getLastName());
            } else if (selectedMatch.getTeam2() != null) {
                participant2ComboBox.setValue(selectedMatch.getTeam2().getTeamName());
            }
        }
    }
}
