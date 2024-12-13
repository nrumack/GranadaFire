package com.example.granadafire.controller.staffControllers;

import com.example.granadafire.DAO.StaffDAO;
import com.example.granadafire.entities.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    @FXML
    private Button deleteButton;

    private Staff staff;

    @FXML
    public void initialize() {
        hideDeleteButton();
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
        updateView();
        hideDeleteButton();
    }
    private void hideDeleteButton() {
        if (deleteButton != null) {
            boolean isVisible = (staff != null);
            deleteButton.setVisible(isVisible);
            System.out.println("delete button ");
        }
    }
    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        nickname.clear();
        street.clear();
        areaCode.clear();
        city.clear();
        country.clear();
        email.clear();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/staffViews/staffSearchView.fxml"));
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
        try {
            if (staff != null) {
                staffDAO.deleteStaff(staff);
                System.out.println("success");
                clearFields();
                backButton(null);
            } else {
                System.out.println("sadge");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

            resetErrorsStyles();

            if (firstNameInput.isEmpty()){
                showError(firstNameField, "First Name is required");
            }
            if (lastNameInput.isEmpty()) {
                showError(lastNameField, "Last Name is required");
            }
            if (nicknameInput.isEmpty()) {
                showError(nickname, "Nickname is required");
            }
            if (isValidAreaCode(areaCodeInput)) {
                showError(areaCode, "Only digits and max 6");
            }
            if (isValidEmail(emailInput)) {
                showError(email, "invalid email");
            }

            if (firstNameInput.isEmpty() || lastNameInput.isEmpty() || nicknameInput.isEmpty() || !isValidAreaCode(areaCodeInput) || !isValidEmail(emailInput)) {
                return;
            }

            Staff newStaff = new Staff(0, firstNameInput, lastNameInput, nicknameInput, streetInput, Integer.parseInt(areaCodeInput), cityInput, countryInput, emailInput);
            staffDAO.saveStaff(newStaff);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void updateStaff() {
        try {
            String firstNameInput = firstNameField.getText();
            String lastNameInput = lastNameField.getText();
            String nicknameInput = nickname.getText();
            String streetInput = street.getText();
            String areaCodeInput = areaCode.getText();
            String cityInput = city.getText();
            String countryInput = country.getText();
            String emailInput = email.getText();

            resetErrorsStyles();

            if (firstNameInput.isEmpty()){
                showError(firstNameField, "First Name is required");
            }
            if (lastNameInput.isEmpty()) {
                showError(lastNameField, "Last Name is required");
            }
            if (nicknameInput.isEmpty()) {
                showError(nickname, "Nickname is required");
            }
            if (isValidAreaCode(areaCodeInput)) {
                showError(areaCode, "Only digits and max 6");
            }
            if (isValidEmail(emailInput)) {
                showError(email, "invalid email");
            }

            if (firstNameInput.isEmpty() || lastNameInput.isEmpty() || nicknameInput.isEmpty() || !isValidAreaCode(areaCodeInput) || !isValidEmail(emailInput)) {
                return;
            }

            if (staff != null && staff.getId() > 0) {
                staff.setFirstName(firstNameInput);
                staff.setLastName(lastNameInput);
                staff.setNickname(nicknameInput);
                staff.setStreet(streetInput);
                staff.setPostNumber(Integer.parseInt(areaCodeInput));
                staff.setCity(cityInput);
                staff.setCountry(countryInput);
                staff.setEmail(emailInput);

                staffDAO.updateStaff(staff);
                System.out.println("success");
                backButton(null);
            } else {
                System.out.println("staff not valid");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean isValidAreaCode(String areaCode) {
        return areaCode.matches("\\d{1,6}");
    }

    private void showError(TextField field, String message) {
        field.setStyle("-fx-border-color: color");

        field.setPromptText(message);
    }

    private void resetErrorsStyles() {
        firstNameField.setStyle("");
        lastNameField.setStyle("");
        nickname.setStyle("");
        email.setStyle("");
        areaCode.setStyle("");

        firstNameField.setPromptText("First Name");
        lastNameField.setPromptText("Last Name");
        nickname.setPromptText("Nickname");
        email.setPromptText("Email Address");
        areaCode.setPromptText("Area Code");
    }
}
