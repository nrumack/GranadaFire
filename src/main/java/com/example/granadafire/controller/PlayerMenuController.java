package com.example.granadafire.controller;
import com.example.granadafire.entities.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.TextField;

public class PlayerMenuController {

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





    public void goToSearch(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playerSearchView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Pied Pipers");
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void goToCreate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playerView.fxml"));
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
    public void backButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainMenuView.fxml"));
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
