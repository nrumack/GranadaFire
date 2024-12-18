package com.example.granadafire.controller.staffControllers;

import com.example.granadafire.DAO.StaffDAO;
import com.example.granadafire.controller.Controller;
import com.example.granadafire.controller.SceneSwitcher;
import com.example.granadafire.controller.gameControllers.GameController;
import com.example.granadafire.entities.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

public class StaffSeachController {
    private StaffDAO staffDAO = new StaffDAO();

    @FXML
    private TextField searchField;
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
                SceneSwitcher.switchScenes(event, "/views/staffViews/staffView.fxml", "Pied Pipers", selectedItem, StaffController.class);
            }
        }
    }

    @FXML
    private void searchStaff() {
        String searchKeyword = searchField.getText().trim();
        if (searchKeyword.isEmpty()) {
            List<Staff> allStaff = staffDAO.getAllStaff();
            staffResultList.setItems(FXCollections.observableArrayList(allStaff));
        } else {
            List<Staff> filteredStaff = staffDAO.getStaffByName(searchKeyword);
            staffResultList.setItems(FXCollections.observableArrayList(filteredStaff));
        }
    }

    public void backButton(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/mainMenuView.fxml", "Pied Pipers", null, Controller.class);
    }

    public void createNewButton(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/staffViews/staffView.fxml", "Pied Pipers", null, StaffController.class);
    }
}
