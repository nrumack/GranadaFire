package com.example.granadafire.controller.playerControllers;

import com.example.granadafire.DAO.GameDAO;
import com.example.granadafire.DAO.PlayerDAO;
import com.example.granadafire.DAO.TeamDAO;
import com.example.granadafire.controller.SceneSwitcher;
import com.example.granadafire.controller.generalControllers.loginController;
import com.example.granadafire.entities.Game;
import com.example.granadafire.entities.Player;
import com.example.granadafire.entities.Team;
import com.example.granadafire.util.DataReciever;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.util.List;
//Created by Nicho/Martin
public class PlayerController implements DataReciever {
    PlayerDAO playerDAO = new PlayerDAO();
    TeamDAO teamDAO = new TeamDAO();
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
    private ComboBox<Game> gameComboBox;
    @FXML
    private ComboBox<Team> teamComboBox;
    @FXML
    private Button deletePlayer;
    @FXML
    private Button clearButton;
    @FXML
    private Button updatePlayer;
    @FXML
    private Button savePlayer;

    private Player selectedPlayer;

    @FXML
    private void initialize() {
        toggleButtonsVisibility();
        loadDropdownData();
    }

    @Override
    public void recieveData(Object data) {
        if (data instanceof Player) {
            this.selectedPlayer = (Player) data;
            setPlayer(this.selectedPlayer);
            updateView();
        }
    }

    public void setPlayer(Player selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
        updateView();
        toggleButtonsVisibility();
    }

    private void toggleButtonsVisibility() {
        if (selectedPlayer != null && selectedPlayer.getId() > 0) {
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
        }

        if (selectedPlayer != null) {
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
            savePlayer.setVisible(true);
            savePlayer.setManaged(true);
            savePlayer.setDisable(false);
        }
    }

    private void loadDropdownData() {
        List<Game> games = gameDAO.getAllGames();
        List<Team> teams = teamDAO.getAllTeams();

        gameComboBox.setItems(FXCollections.observableArrayList(games));
        teamComboBox.setItems(FXCollections.observableArrayList(teams));

        gameComboBox.setConverter(new StringConverter<Game>() {
            @Override
            public String toString(Game game) {
                return game != null ? game.getGameName() : "";
            }

            @Override
            public Game fromString(String string) {
                return games.stream().filter(game -> game.getGameName().equals(string)).findFirst().orElse(null);
            }
        });

        teamComboBox.setConverter(new StringConverter<Team>() {
            @Override
            public String toString(Team team) {
                return team != null ? team.getTeamName() : "";
            }

            @Override
            public Team fromString(String string) {
                return teams.stream().filter(team -> team.getTeamName().equals(string)).findFirst().orElse(null);
            }
        });
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
        gameComboBox.getSelectionModel().clearSelection();
        teamComboBox.getSelectionModel().clearSelection();
    }

    private void updateView() {
        if (selectedPlayer != null) {
            firstNameField.setText(selectedPlayer.getFirstName());
            lastNameField.setText(selectedPlayer.getLastName());
            nickname.setText(selectedPlayer.getNickname());
            street.setText(selectedPlayer.getStreet());
            areaCode.setText(String.valueOf(selectedPlayer.getPostNumber()));
            city.setText(selectedPlayer.getCity());
            country.setText(selectedPlayer.getCountry());
            email.setText(selectedPlayer.getEmail());

            gameComboBox.setValue(selectedPlayer.getGame());
            teamComboBox.setValue(selectedPlayer.getTeam());
        }
    }

    @FXML
    public void deletePlayer(ActionEvent event) {
        try {
            int playerId = selectedPlayer.getId();
            playerDAO.deletePlayer(playerId);

            SceneSwitcher.switchScenes(event, "/views/playerViews/playerSearchView.fxml", "Pied Pipers", null, PlayerSearchController.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

            Game selectedGame = gameComboBox.getValue();
            Team selectedTeam = teamComboBox.getValue();

            resetErrorsStyles();

            if (firstNameInput.isEmpty()) {
                showError(firstNameField, "First Name is required");
            }
            if (lastNameInput.isEmpty()) {
                showError(lastNameField, "Last Name is required");
            }
            if (nicknameInput.isEmpty()) {
                showError(nickname, "Nickname is required");
            }
            if (!isValidAreaCode(areaCodeInput)) {
                showError(areaCode, "Only digits and max 6");
            }
            if (!isValidEmail(emailInput)) {
                showError(email, "Invalid email");
            }
            if (selectedGame == null) {
                showError(gameComboBox, "Game is required");
            }

            if (firstNameInput.isEmpty() || lastNameInput.isEmpty() || nicknameInput.isEmpty() || !isValidAreaCode(areaCodeInput) || !isValidEmail(emailInput) || selectedGame == null) {
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
                    selectedTeam,
                    selectedGame
            );

            playerDAO.savePlayer(newPlayer);

            SceneSwitcher.switchScenes(event, "/views/playerViews/playerSearchView.fxml", "Pied Pipers", null, PlayerSearchController.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
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

            Game selectedGame = gameComboBox.getValue();
            Team selectedTeam = teamComboBox.getValue();

            resetErrorsStyles();

            if (firstNameInput.isEmpty()) {
                showError(firstNameField, "First Name is required*");
            }
            if (lastNameInput.isEmpty()) {
                showError(lastNameField, "Last Name is required");
            }
            if (nicknameInput.isEmpty()) {
                showError(nickname, "Nickname is required");
            }
            if (!isValidAreaCode(areaCodeInput)) {
                showError(areaCode, "Only digits and max 6");
            }
            if (!isValidEmail(emailInput)) {
                showError(email, "Invalid email");
            }
            if (selectedGame == null) {
                showError(gameComboBox, "Game is required");
            }

            if (firstNameInput.isEmpty() || lastNameInput.isEmpty() || nicknameInput.isEmpty() || !isValidAreaCode(areaCodeInput) || !isValidEmail(emailInput) || selectedGame == null) {
                return;
            }

            Player existingPlayer = playerDAO.getPlayerById(selectedPlayer.getId());

            if (existingPlayer != null) {
                existingPlayer.setFirstName(firstNameInput);
                existingPlayer.setLastName(lastNameInput);
                existingPlayer.setNickname(nicknameInput);
                existingPlayer.setStreet(streetInput);
                existingPlayer.setPostNumber(Integer.parseInt(areaCodeInput));
                existingPlayer.setCity(cityInput);
                existingPlayer.setCountry(countryInput);
                existingPlayer.setEmail(emailInput);
                existingPlayer.setTeam(selectedTeam);
                existingPlayer.setGame(selectedGame);

                playerDAO.updatePlayer(existingPlayer);

                SceneSwitcher.switchScenes(event, "/views/playerViews/playerSearchView.fxml", "Pied Pipers", null, PlayerSearchController.class);
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
    }

    private void showError(ComboBox<?> comboBox, String message) {
        comboBox.setStyle("-fx-border-color: red");
    }

    private void resetErrorsStyles() {
        firstNameField.setStyle("");
        lastNameField.setStyle("");
        nickname.setStyle("");
        email.setStyle("");
        areaCode.setStyle("");
        gameComboBox.setStyle("");
        teamComboBox.setStyle("");

        firstNameField.setPromptText("First Name");
        lastNameField.setPromptText("Last Name");
        nickname.setPromptText("Nickname");
        email.setPromptText("Email Address");
        areaCode.setPromptText("Area Code");
    }

    @FXML
    private void backButton(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/playerViews/playerSearchView.fxml", "Pied Pipers", null, PlayerSearchController.class);
    }

    @FXML
    protected void logout(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/loginView.fxml", "Pied Pipers", null, loginController.class);
    }

}