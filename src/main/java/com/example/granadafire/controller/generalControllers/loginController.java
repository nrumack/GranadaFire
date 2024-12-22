package com.example.granadafire.controller.generalControllers;

import com.example.granadafire.DAO.StaffDAO;
import com.example.granadafire.controller.MenuController;
import com.example.granadafire.controller.SceneSwitcher;
import com.example.granadafire.entities.Staff;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;


import java.util.List;


// Made by Martin/Nicho
public class loginController {

    @FXML
    private GridPane loginButtonContainer;
    @FXML
    private Pagination linkContainer;
    private static final int ButtonsPerPage = 6;

    private StaffDAO staffDAO = new StaffDAO();

    @FXML
    public void initialize() {
        List<Staff> staffList = staffDAO.getAllStaff();

        if (staffList.isEmpty()) {
            linkContainer.setVisible(false);
            loginButtonContainer.setVisible(false);
            return;
        }

        if (staffList.size() <= ButtonsPerPage) {
            linkContainer.setVisible(false);
            loginButtonContainer.setVisible(true);
            showButtonsInGrid(staffList);
        } else {
            loginButtonContainer.setVisible(false);
            linkContainer.setVisible(true);
            linkContainer.setPageCount((int) Math.ceil((double) staffList.size() / ButtonsPerPage));
            linkContainer.setPageFactory(pageIndex -> createPageContent(staffList, pageIndex));
        }
    }

    private void showButtonsInGrid(List<Staff> staffList) {
        loginButtonContainer.getChildren().clear();
        loginButtonContainer.setHgap(130);

        int row = 0;
        int col = 0;

        for (Staff staff : staffList) {
            Button button = new Button(staff.getNickname());
            button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-background-color: #000000; -fx-effect: dropshadow(three-pass-box, rgba(255, 255, 255, 0.8), 5, 0.0, 0, 2);");
            button.setPrefHeight(10.0);
            button.setPrefWidth(150.0);
            button.setOnMouseClicked(event -> handleLabelClick(event)); // Button click event handler

            loginButtonContainer.add(button, col, row);
            col++;
            if (col > 1) { col = 0; row++;}
        }
    }

    private GridPane createPageContent(List<Staff> staffList, int pageIndex) {
        GridPane pageContent = new GridPane();
        pageContent.setVgap(10);
        pageContent.setHgap(130);
        pageContent.setAlignment(Pos.TOP_CENTER);

        int startIndex = pageIndex * ButtonsPerPage;
        int endIndex = Math.min(startIndex + ButtonsPerPage, staffList.size());

        for (int i = startIndex; i < endIndex; i++) {
            Staff staff = staffList.get(i);
            Button button = new Button(staff.getNickname());
            button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-background-color: #000000; -fx-effect: dropshadow(three-pass-box, rgba(255, 255, 255, 0.8), 5, 0.0, 0, 2);");
            button.setPrefHeight(30.0);
            button.setPrefWidth(150.0);
            button.setOnMouseClicked(event -> handleLabelClick(event));

            int row = (i - startIndex) / 2;
            int col = (i - startIndex) % 2;
            pageContent.add(button, col, row);
        }

        return pageContent;
    }

    @FXML
    public void handleLabelClick(javafx.scene.input.MouseEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/mainMenuView.fxml", "Pied Pipers", null, MenuController.class);
    }
}