package com.example.granadafire.controller;

import com.example.granadafire.DAO.PlayerDAO;
import com.example.granadafire.entities.Player;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class PlayerController {
    PlayerDAO playerDAO= new PlayerDAO();

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
    public void newSearch(){
        System.out.println("new search");
    }

    @FXML
    public void deleteButton(){
        System.out.println("tested");
    }
    Player newPlayer = new Player("joe","lastname","nickname","street",1,"city","country","email",null, null);

    @FXML
    public void updateButton(){

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

    }

}
