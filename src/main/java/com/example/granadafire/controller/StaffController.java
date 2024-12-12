package com.example.granadafire.controller;

import com.example.granadafire.DAO.StaffDAO;
import com.example.granadafire.entities.Player;
import com.example.granadafire.entities.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StaffController {
    StaffDAO staffDAO = new StaffDAO();

    public TextField firstNameField;
    public TextField lastNameField;
    public TextField nickname;
    public TextField street;
    public TextField areaCode;
    public TextField city;
    public TextField country;
    public TextField email;
    private Staff staff;

    public void setStaff(Staff staff) {
        this.staff = staff;
        updateView();
    }

    private void updateView() {
        if (staff != null) {
            firstNameField.setText(staff.getFirstName());
            lastNameField.setText(staff.getLastName());
            nickname.setText(staff.getNickname());
            street.setText(staff.getStreet());
            areaCode.setText(String.valueOf(staff.getPostNumber()));
            city.setText(staff.getCity());
            country.setText(staff.getCountry());
            email.setText(staff.getEmail());
        }
    }


    @FXML
    public void backButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/staffSearchView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Pied Pipers");
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteButton() {
        System.out.println("tested");
    }

    @FXML
    public void saveStaff() {
        try {
            String firstNameInput = firstNameField.getText();
            String lastNameInput = lastNameField.getText();
            String nicknameInput = nickname.getText();
            String streetInput = street.getText();
            String areaCodeInput = areaCode.getText();
            String cityInput = city.getText();
            String countryInput = country.getText();
            String emailInput = email.getText();


            Staff newStaff = new Staff(1, firstNameInput, lastNameInput, nicknameInput, streetInput, Integer.parseInt(areaCodeInput), cityInput, countryInput, emailInput);
            staffDAO.saveStaff(newStaff);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
