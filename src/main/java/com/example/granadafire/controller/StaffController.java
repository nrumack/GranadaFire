package com.example.granadafire.controller;

import com.example.granadafire.DAO.PlayerDAO;
import com.example.granadafire.DAO.StaffDAO;
import com.example.granadafire.entities.Player;
import com.example.granadafire.entities.Staff;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;


// Made by Martin
public class StaffController {

    @FXML
    private Label nameLabel1;
    @FXML
    private Label nameLabel2;
    @FXML
    private Label nameLabel3;
    @FXML
    private Label nameLabel4;
    @FXML
    private Label nameLabel5;
    @FXML
    private Label nameLabel6;
    @FXML
    private Label nameLabel7;

    private StaffDAO staffDAO = new StaffDAO();
    private PlayerDAO playerDAO = new PlayerDAO();


    @FXML
    public void initialize() {
        List<Staff> staffList = staffDAO.getAllStaff();
        List<Player> playerList = playerDAO.getAllPlayers();

        List<Label> labels = Arrays.asList(nameLabel1, nameLabel2, nameLabel3, nameLabel4, nameLabel5, nameLabel6, nameLabel7);
        System.out.println(playerList.get(1).getFirstName());

        setLabelNames(staffList, labels);
        setUnusedLabelsInvisible(labels);
    }
    private void setLabelNames(List<Staff> staffList, List<Label> labels)
    {
        int limit = Math.min(staffList.size(), labels.size());

        for (int i = 0; i < limit; i++) {
            labels.get(i).setText(staffList.get(i).getNickname());
        }
    }
    private void setUnusedLabelsInvisible(List<Label> labels) {
        for (Label label : labels) {
            if (label.getText() == null || label.getText().isEmpty()) {
                label.setVisible(false);
                label.setManaged(false);
            }
        }
    }

    @FXML
    public void handleLabelClick(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainMenuView.fxml"));
            Parent newRoot = loader.load();
            Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(newRoot));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
