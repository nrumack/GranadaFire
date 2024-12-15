package com.example.granadafire.controller.playerControllers;

import com.example.granadafire.DAO.GameDAO;
import com.example.granadafire.DAO.PlayerDAO;
import com.example.granadafire.DAO.TeamDAO;
import com.example.granadafire.entities.Game;
import com.example.granadafire.entities.Player;
import com.example.granadafire.entities.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;


public class PlayerController {
    PlayerDAO playerDAO = new PlayerDAO();
    TeamDAO teamDAO =  new TeamDAO();
    GameDAO gameDAO = new GameDAO();

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField nickname;
    @FXML
    private TextField street;
    @FXML
    private TextField areaCode;
    @FXML
    private TextField city;
    @FXML
    private TextField country;
    @FXML
    private TextField email;
    @FXML
    private TextField team;
    @FXML
    private TextField game;
    private Player player;

    @FXML
    private Button deletePlayer;
    @FXML
    private Button clearButton;
    @FXML
    private Button updatePlayer;
    @FXML
    private Button savePlayer;

    @FXML
    private void initialize() {
        toggleButtonsVisibility();
    }

    public void setPlayer(Player player) {
        this.player = player;
        updateView();
        toggleButtonsVisibility();
    }

    private void toggleButtonsVisibility() {
        if (player != null && player.getId() > 0) {
            deletePlayer.setVisible(true);
            deletePlayer.setManaged(true);
            deletePlayer.setDisable(false);
            updatePlayer.setVisible(true);
            updatePlayer.setManaged(true);
            updatePlayer.setDisable(false);
        } else {
            deletePlayer.setVisible(false);
            deletePlayer.setManaged(false);
            deletePlayer.setDisable(true);
            updatePlayer.setVisible(false);
            updatePlayer.setManaged(false);
            updatePlayer.setDisable(true);
            updatePlayer.setStyle("-fx-background-color: C0BCBC");
        }
        if (player != null) {
            clearButton.setVisible(false);
            clearButton.setManaged(false);
            clearButton.setDisable(true);
            savePlayer.setVisible(false);
            savePlayer.setManaged(false);
            savePlayer.setDisable(true);
        } else {
            clearButton.setVisible(true);
            clearButton.setManaged(true);
            clearButton.setDisable(false);
            clearButton.setStyle("-fx-background-color: C0BCBC");
            savePlayer.setVisible(true);
            savePlayer.setManaged(true);
            savePlayer.setDisable(false);
            savePlayer.setStyle("-fx-background-color: C0BCBC");
        }
    }
    @FXML
    private void clearButton(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        nickname.clear();
        street.clear();
        areaCode.clear();
        city.clear();
        country.clear();
        email.clear();
    }


    private void updateView() {
        if (player != null) {
            firstNameField.setText(player.getFirstName());
            lastNameField.setText(player.getLastName());
            nickname.setText(player.getNickname());
            street.setText(player.getStreet());
            areaCode.setText(String.valueOf(player.getPostNumber()));
            city.setText(player.getCity());
            country.setText(player.getCountry());
            email.setText(player.getEmail());

            team.setText(player.getTeam() != null ? player.getTeam().getTeamName() : "");
            game.setText(player.getGame() != null ? player.getGame().getGameName() : "");
        }
    }

    @FXML
    public void deletePlayer(ActionEvent event) {
        try {
            int playerId = player.getId();
            playerDAO.deletePlayer(playerId);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playerViews/playerSearchView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Pied Pipers");
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void savePlayer(ActionEvent event) {
        try {

            String firstNameInput = firstNameField.getText();
            String lastNameInput = lastNameField.getText();
            String nicknameInput = nickname.getText();
            String streetInput = street.getText();
            String areaCodeInput = areaCode.getText();
            String cityInput = city.getText();
            String countryInput = country.getText();
            String emailInput = email.getText();
            String teamInput = team.getText(); // Team name
            String gameInput = game.getText(); // Game name

            Team assignedTeam = null;
            Game assignedGame = null;


            if (teamInput != null && !teamInput.isEmpty()) {
                assignedTeam = teamDAO.getTeamByName(teamInput);
                if (assignedTeam == null) {
                    System.out.println("Team not found: " + teamInput);
                }
            }

            if (gameInput != null && !gameInput.isEmpty()) {
                assignedGame = gameDAO.getGameByName(gameInput);
                if (assignedGame == null) {
                    System.out.println("Game not found: " + gameInput);
                }
            }

            resetErrorsStyles();

            if (firstNameInput.isEmpty()){
                showError(firstNameField, "First Name is required");
            }
            if (lastNameInput.isEmpty()) {
                showError(lastNameField, "Last Name is required");
            }
            if (nicknameInput.isEmpty()) {
                showError(nickname, "Nickname is required");
            }
            if (isValidAreaCode(areaCodeInput)) {
                showError(areaCode, "Only digits and max 6");
            }
            if (isValidEmail(emailInput)) {
                showError(email, "invalid email");
            }

            if (firstNameInput.isEmpty() || lastNameInput.isEmpty() || nicknameInput.isEmpty() || !isValidAreaCode(areaCodeInput) || !isValidEmail(emailInput)) {
                return;
            }

            Player newPlayer = new Player(
                    firstNameInput,
                    lastNameInput,
                    nicknameInput,
                    streetInput,
                    Integer.parseInt(areaCodeInput),
                    cityInput,
                    countryInput,
                    emailInput,
                    assignedTeam,
                    assignedGame
            );


            playerDAO.savePlayer(newPlayer);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playerViews/playerSearchView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Pied Pipers");
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePlayer(ActionEvent event) {
        try {

            String firstNameInput = firstNameField.getText();
            String lastNameInput = lastNameField.getText();
            String nicknameInput = nickname.getText();
            String streetInput = street.getText();
            String areaCodeInput = areaCode.getText();
            String cityInput = city.getText();
            String countryInput = country.getText();
            String emailInput = email.getText();
            String teamInput = team.getText();

            resetErrorsStyles();

            if (firstNameInput.isEmpty()){
                showError(firstNameField, "First Name is required*");
            }
            if (lastNameInput.isEmpty()) {
                showError(lastNameField, "Last Name is required*");
            }
            if (nicknameInput.isEmpty()) {
                showError(nickname, "Nickname is required*");
            }
            if (isValidAreaCode(areaCodeInput)) {
                showError(areaCode, "Only digits and max 6");
            }
            if (isValidEmail(emailInput)) {
                showError(email, "invalid email");
            }

            if (firstNameInput.isEmpty() || lastNameInput.isEmpty() || nicknameInput.isEmpty() || !isValidAreaCode(areaCodeInput) || !isValidEmail(emailInput)) {
                return;
            }

            Player existingPlayer = playerDAO.getPlayerById(player.getId());

            if (existingPlayer != null) {
                existingPlayer.setFirstName(firstNameInput);
                existingPlayer.setLastName(lastNameInput);
                existingPlayer.setNickname(nicknameInput);
                existingPlayer.setStreet(streetInput);
                existingPlayer.setPostNumber(Integer.parseInt(areaCodeInput));
                existingPlayer.setCity(cityInput);
                existingPlayer.setCountry(countryInput);
                existingPlayer.setEmail(emailInput);

                if (!teamInput.isEmpty()) {
                    Team updatedTeam = teamDAO.getTeamByName(teamInput);
                    if (updatedTeam != null) {
                        existingPlayer.setTeam(updatedTeam);
                    } else {
                        System.out.println("Team not found: " + teamInput);
                    }
                }

                playerDAO.updatePlayer(existingPlayer);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playerViews/playerSearchView.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Pied Pipers");
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } else {
                System.out.println("Player not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean isValidAreaCode(String areaCode) {
        return areaCode.matches("\\d{1,6}");
    }

    private void showError(TextField field, String message) {
        field.setStyle("-fx-border-color: red");
        field.setPromptText(message);
        field.setStyle("-fx-prompt-atext-fill: red;");
    }

    private void resetErrorsStyles() {
        firstNameField.setStyle("");
        lastNameField.setStyle("");
        nickname.setStyle("");
        email.setStyle("");
        areaCode.setStyle("");

        firstNameField.setPromptText("First Name");
        lastNameField.setPromptText("Last Name");
        nickname.setPromptText("Nickname");
        email.setPromptText("Email Address");
        areaCode.setPromptText("Area Code");
    }

    @FXML
    private void backButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playerViews/playerSearchView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Pied Pipers");
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
