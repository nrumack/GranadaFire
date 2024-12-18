package com.example.granadafire.controller.generalControllers;

import com.example.granadafire.DAO.StaffDAO;
import com.example.granadafire.controller.Controller;
import com.example.granadafire.controller.SceneSwitcher;
import com.example.granadafire.entities.Staff;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;


import java.util.List;


// Made by Martin
public class loginController {

    @FXML
    private VBox loginButtonContainer;
    @FXML
    private Pagination linkContainer;
    private static final int ButtonsPerPage = 6;

    private StaffDAO staffDAO = new StaffDAO();

    @FXML
    public void initialize() {
        List<Staff> staffList = staffDAO.getAllStaff();

        if (staffList.size() < 6) {
            linkContainer.setVisible(false);
            createPageContent(staffList, 0);

            for (Staff staff : staffList) {
                Button button = new Button(staff.getNickname());
                button.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff; -fx-background-color: #C0BCBC;");
                button.setPrefHeight(30.0);
                button.setPrefWidth(150.0);
                button.setOnMouseClicked(event -> handleLabelClick(event));
                loginButtonContainer.getChildren().add(button);
            }
        } else {
            linkContainer.setVisible(true);
            linkContainer.setPageCount((int) Math.ceil((double) staffList.size() / ButtonsPerPage));  // Set number of pages
            linkContainer.setPageFactory(pageIndex -> createPageContent(staffList, pageIndex));
        }
    }

    private VBox createPageContent(List<Staff> staffList, int pageIndex) {
        VBox pageContent = new VBox(10);
        pageContent.setAlignment(Pos.TOP_CENTER);

        int startIndex = pageIndex * ButtonsPerPage;
        int endIndex = Math.min(startIndex + ButtonsPerPage, staffList.size());

        for (Staff staff : staffList) {
            Button button = new Button(staff.getNickname());
            button.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff; -fx-background-color: #C0BCBC;");
            button.setPrefHeight(30.0);
            button.setPrefWidth(150.0);
            button.setOnMouseClicked(event -> handleLabelClick(event));
            pageContent.getChildren().add(button);
        }

        return pageContent;
    }

    @FXML
    public void handleLabelClick(javafx.scene.input.MouseEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/mainMenuView.fxml", "Pied Pipers", null, Controller.class);
    }
}
