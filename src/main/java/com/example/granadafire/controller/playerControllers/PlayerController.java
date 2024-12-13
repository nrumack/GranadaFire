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
    private void initialize() {
        System.out.println("PlayerMenuController initialized.");
        System.out.println("firstNameField: " + firstNameField);
    }

    public void setPlayer(Player player) {
        this.player = player;
        updateView();
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

    @FXML
    private void backButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playerViews/playerSearchView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Player Search");
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
