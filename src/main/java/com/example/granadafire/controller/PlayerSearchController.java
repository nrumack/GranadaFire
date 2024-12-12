package com.example.granadafire.controller;

import com.example.granadafire.DAO.PlayerDAO;
import com.example.granadafire.entities.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

public class PlayerSearchController {


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
    public void createNewButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playerView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Pied Pipers");
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    PlayerDAO playerDAO = new PlayerDAO();

    @FXML
    private ListView<Player> resultList;

    @FXML
    public void initialize() {
        List<Player> players = playerDAO.getAllPlayers();
        System.out.println(players);
        ObservableList<Player> observablePlayers = FXCollections.observableArrayList(players);


        resultList.setItems(observablePlayers);
        resultList.setOnMouseClicked(this::handleClick);
    }


    private void handleClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Player selectedItem = resultList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playerView.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(loader.load()));

                    PlayerController controller = loader.getController();
                    controller.setPlayer(selectedItem);

                    stage.setTitle("Pied Piper");
                    stage.show();

                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
