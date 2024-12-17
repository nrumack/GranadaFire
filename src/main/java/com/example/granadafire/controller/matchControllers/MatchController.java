package com.example.granadafire.controller.matchControllers;

import com.example.granadafire.DAO.MatchDAO;
import com.example.granadafire.entities.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatchController {

    private final MatchDAO matchDAO = new MatchDAO();
    private List<Match> matches = new ArrayList<>();


    @FXML
    private ListView<String> matchListView;

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
    }

    @FXML
    public void applyFilter(ActionEvent event) {
        String filterCriteria = filterComboBox.getValue();

        if ("Completed".equals(filterCriteria)) {
            loadMatches(matches.stream()
                    .filter(match -> match.getResult() != null)
                    .collect(Collectors.toList()));
        } else if ("Upcoming".equals(filterCriteria)) {
            loadMatches(matches.stream()
                    .filter(match -> match.getResult() == null && match.getMatchDateRaw().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList()));
        } else {
            loadMatches(matches);
        }
    }

    @FXML
    private void loadMatches(List<Match> matches) {
        ObservableList<String> formattedMatches = FXCollections.observableArrayList();

        if (matches == null || matches.isEmpty()) {
            System.out.println("No matches to display.");
            return;
        }

        for (Match match : matches) {
            String participant1 = match.getParticipant1Name();
            String participant2 = match.getParticipant2Name();
            String scores = (match.getResult() == null) ? "N/A - N/A" : String.format("%d - %d", match.getScore1(), match.getScore2());
            String winner = (match.getResult() == null) ? "N/A" : match.getWinnerName();
            String matchStatus = (match.getResult() == null || match.getResult().isEmpty()) ? "(Upcoming)" : "(Completed)";
            String matchString = String.format("[%s] %s (%s) vs %s (%s) - Winner: %s %s",
                    match.getMatchDateRaw(), participant1, scores, participant2, scores, winner, matchStatus);
            formattedMatches.add(matchString);
        }

        matchListView.setItems(formattedMatches);
    }


    @FXML
    private void deleteMatch() {
        Match selectedMatch = matches.get(matchListView.getSelectionModel().getSelectedIndex());

        if (selectedMatch == null) {
            return;
        }

        matchDAO.deleteMatchById(selectedMatch.getMatchId());
        matches.remove(selectedMatch);
        loadMatches(matches);
    }

    @FXML
    private void updateScore() {
        try {
            Match selectedMatch = matches.get(matchListView.getSelectionModel().getSelectedIndex());
            if (selectedMatch == null) {
                System.out.println("No match selected.");
                return;
            }

            if (score1Field.getText().isEmpty() || score2Field.getText().isEmpty()) {
                System.out.println("Scores cannot be empty.");
                return;
            }

            int score1 = Integer.parseInt(score1Field.getText());
            int score2 = Integer.parseInt(score2Field.getText());

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
            System.out.println("Invalid score");
        } catch (Exception e) {
            System.out.println("Error updating match: " + e.getMessage());
        }
    }
    @FXML
    private void createMatch(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/matchViews/matchAddView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Add Match");
            stage.show();

            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/generalViews/mainMenuView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Main Menu");
            stage.show();

            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





