package com.example.granadafire;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class GamesController {
    @FXML
    private Label gamesButtonTest;

    @FXML
    protected void onTestButtonClick() {
        gamesButtonTest.setText("Test");
    }
}