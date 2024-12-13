package com.example.granadafire.controller.playerControllers;

import com.example.granadafire.DAO.PlayerDAO;
import com.example.granadafire.entities.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class PlayerController {
    PlayerDAO playerDAO = new PlayerDAO();
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
    public void deleteButton() {
        System.out.println("tested");
    }

    @FXML
    public void updateButton() {
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
            String gameInput = game.getText();

            Player newPlayer = new Player(firstNameInput, lastNameInput, nicknameInput, streetInput, Integer.parseInt(areaCodeInput), cityInput, countryInput, emailInput, null, null);
            playerDAO.savePlayer(newPlayer);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void backButton(ActionEvent event) {
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
