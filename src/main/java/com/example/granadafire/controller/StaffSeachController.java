package com.example.granadafire.controller;

import com.example.granadafire.DAO.StaffDAO;
import com.example.granadafire.entities.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

public class StaffSeachController {
    private StaffDAO staffDAO = new StaffDAO();

    @FXML
    private ListView<Staff> staffResultList;

    @FXML
    public void initialize() {
        List<Staff> staff = staffDAO.getAllStaff();

        ObservableList<Staff> observableStaff = FXCollections.observableArrayList(staff);


        staffResultList.setItems(observableStaff);

        staffResultList.setOnMouseClicked(this::handleClick);
    }

    private void handleClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Staff selectedItem = staffResultList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/staffView.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(loader.load()));

                    StaffController controller = loader.getController();
                    controller.setStaff(selectedItem);

                    stage.setTitle("Pied Pipers");
                    stage.show();
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/staffView.fxml"));
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
