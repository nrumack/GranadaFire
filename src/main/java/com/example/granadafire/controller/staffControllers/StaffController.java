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
    @FXML
    private Button clearButton;
    @FXML
    private Button updateStaff;
    @FXML
    private Button saveStaff;

    private Staff staff;

    @FXML
    public void initialize() {
        toggleButtonsVisibility();
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
        updateView();
        toggleButtonsVisibility();
    }

    private void toggleButtonsVisibility() {
        if (staff != null && staff.getId() > 0) {
            deleteButton.setVisible(true);
            deleteButton.setManaged(true);
            deleteButton.setDisable(false);
            updateStaff.setVisible(true);
            updateStaff.setManaged(true);
            updateStaff.setDisable(false);
        } else {
            deleteButton.setVisible(false);
            deleteButton.setManaged(false);
            deleteButton.setDisable(true);
            updateStaff.setVisible(false);
            updateStaff.setManaged(false);
            updateStaff.setDisable(true);
            updateStaff.setStyle("-fx-background-color: C0BCBC");
        }
        if (staff != null) {
            clearButton.setVisible(false);
            clearButton.setManaged(false);
            clearButton.setDisable(true);
            saveStaff.setVisible(false);
            saveStaff.setManaged(false);
            saveStaff.setDisable(true);
        } else {
            clearButton.setVisible(true);
            clearButton.setManaged(true);
            clearButton.setDisable(false);
            clearButton.setStyle("-fx-background-color: C0BCBC");
            saveStaff.setVisible(true);
            saveStaff.setManaged(true);
            saveStaff.setDisable(false);
            saveStaff.setStyle("-fx-background-color: C0BCBC");
        }
    }

    @FXML
    private void clearButton(ActionEvent event) { clearFields(); }
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
    public void deleteButton(ActionEvent event) {
        try {
            if (staff != null) {
                staffDAO.deleteStaff(staff);
                clearFields();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/staffViews/staffSearchView.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Pied Pipers");
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
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
    public void updateStaff(ActionEvent event) {
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
                showError(firstNameField, "First Name is required*");
            }
            if (lastNameInput.isEmpty()) {
                showError(lastNameField, "Last Name is required*");
            }
            if (nicknameInput.isEmpty()) {
                showError(nickname, "Nickname is required*");
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

            Staff existingStaff = staffDAO.getStaffById(staff.getId());

            if (existingStaff != null) {
                existingStaff.setFirstName(firstNameInput);
                existingStaff.setLastName(lastNameInput);
                existingStaff.setNickname(nicknameInput);
                existingStaff.setStreet(streetInput);
                existingStaff.setPostNumber(Integer.parseInt(areaCodeInput));
                existingStaff.setCity(cityInput);
                existingStaff.setCountry(countryInput);
                existingStaff.setEmail(emailInput);

                staffDAO.updateStaff(existingStaff);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/staffViews/staffSearchView.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Pied Pipers");
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
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
        field.setStyle("-fx-border-color: red");
        field.setPromptText(message);
        field.setStyle("-fx-prompt-atext-fill: red;");
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