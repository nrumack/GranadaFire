package controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class GameController {
    @FXML
    private Label gamesButtonTest;

    @FXML
    protected void onTestButtonClick() {
        gamesButtonTest.setText("Test");
    }
}