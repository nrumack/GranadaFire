package com.example.granadafire.controller.matchControllers;

import com.example.granadafire.DAO.MatchDAO;
import com.example.granadafire.controller.MenuController;
import com.example.granadafire.controller.SceneSwitcher;
import com.example.granadafire.controller.generalControllers.loginController;
import com.example.granadafire.entities.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
//Created by Martin
public class MatchController {

    private final MatchDAO matchDAO = new MatchDAO();
    private List<Match> matches = new ArrayList<>();

    @FXML
    private ListView<Match> matchListView;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private TextField score1Field;

    @FXML
    private TextField score2Field;

    @FXML
    public void initialize() {
        filterComboBox.setItems(FXCollections.observableArrayList("All Matches", "Completed", "Upcoming"));
        filterComboBox.setValue("All Matches");

        matches = matchDAO.getAllMatches();
        if (matches == null) {
            matches = new ArrayList<>();
        }

        loadMatches(matches);

        filterComboBox.setOnAction(event -> applyFilter());
    }

    private void loadMatches(List<Match> matches) {
        ObservableList<Match> matchObservableList = FXCollections.observableArrayList(matches);
        matchObservableList.sort(Comparator.comparing(Match::getMatchDateRaw));

        matchListView.setItems(matchObservableList);
        matchListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Match match, boolean empty) {
                super.updateItem(match, empty);
                if (empty || match == null) {
                    setText(null);
                } else {
                    String status = match.getResult() == null ? "(Upcoming)" : "(Completed)";
                    String score = (match.getScore1() == null || match.getScore2() == null) ? "N/A - N/A"
                            : match.getScore1() + " - " + match.getScore2();
                    setText(String.format("[%s] %s %s %s - Winner: %s %s",
                            match.getMatchDateRaw(),
                            match.getParticipant1Name(),
                            score,
                            match.getParticipant2Name(),
                            match.getWinnerName(),
                            status));
                }
            }
        });
    }

    @FXML
    private void applyFilter() {
        String filter = filterComboBox.getValue();

        if ("Completed".equals(filter)) {
            loadMatches(matches.stream()
                    .filter(match -> match.getScore1() != null && match.getScore2() != null)
                    .collect(Collectors.toList()));
        } else if ("Upcoming".equals(filter)) {
            loadMatches(matches.stream()
                    .filter(match -> match.getScore1() == null || match.getScore2() == null)
                    .collect(Collectors.toList()));
        } else {
            loadMatches(matches);
        }
    }

    @FXML
    private void updateScore() {
        Match selectedMatch = matchListView.getSelectionModel().getSelectedItem();
        if (selectedMatch == null) {
            return;
        }

        try {
            Integer score1 = Integer.parseInt(score1Field.getText());
            Integer score2 = Integer.parseInt(score2Field.getText());

            selectedMatch.setScore1(score1);
            selectedMatch.setScore2(score2);

            if (score1 > score2) {
                selectedMatch.setResult("team1");
            } else if (score2 > score1) {
                selectedMatch.setResult("team2");
            } else {
                selectedMatch.setResult("draw");
            }

            matchDAO.updateMatch(selectedMatch);
            loadMatches(matches);

        } catch (NumberFormatException e) {
            System.out.println("Invalid score.");
        }
    }

    @FXML
    private void deleteMatch() {
        Match selectedMatch = matchListView.getSelectionModel().getSelectedItem();
        if (selectedMatch == null) {
            return;
        }

        matchDAO.deleteMatchById(selectedMatch.getMatchId());
        matches.remove(selectedMatch);
        loadMatches(matches);
    }

    @FXML
    private void editMatch(ActionEvent event) {
        Match selectedMatch = matchListView.getSelectionModel().getSelectedItem();
        if (selectedMatch == null) {
            System.out.println("No match selected for editing.");
            return;
        }
        SceneSwitcher.switchScenes(event, "/views/matchViews/matchEditView.fxml", "Edit Match", selectedMatch, MatchEditController.class);
    }

    @FXML
    private void createMatch(javafx.event.ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/matchViews/matchAddView.fxml", "Pied Pipers", null, AddMatchController.class);
    }

    @FXML
    private void goBack(javafx.event.ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/mainMenuView.fxml", "Pied Pipers", null, MenuController.class);
    }

    @FXML
    protected void logout(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/loginView.fxml", "Pied Pipers", null, loginController.class);
    }
}

