package com.example.granadafire.controller;

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

    public TextField firstNameField;
    public TextField lastNameField;
    public TextField nickname;
    public TextField street;
    public TextField areaCode;
    public TextField city;
    public TextField country;
    public TextField email;
    public TextField team;
    public TextField game;

    @FXML
    public void backButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playerMenuView.fxml"));
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
    public void newSearch(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playerSearchView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Hello World");
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
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

}
