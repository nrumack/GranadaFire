package com.example.granadafire.controller.generalControllers;

import com.example.granadafire.DAO.StaffDAO;
import com.example.granadafire.entities.Staff;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
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
        linkContainer.setPageCount((int) Math.ceil((double) staffList.size() / ButtonsPerPage));  // Set number of pages
        linkContainer.setPageFactory(pageIndex -> createPageContent(staffList, pageIndex));

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
    public void handleLabelClick(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/generalViews/mainMenuView.fxml"));
            Parent newRoot = loader.load();
            Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(newRoot));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
