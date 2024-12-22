package com.example.granadafire.controller.staffControllers;

import com.example.granadafire.DAO.StaffDAO;
import com.example.granadafire.controller.SceneSwitcher;
import com.example.granadafire.controller.generalControllers.loginController;
import com.example.granadafire.entities.Game;
import com.example.granadafire.entities.Staff;
import com.example.granadafire.util.DataReciever;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//Created by Nicho
public class StaffController implements DataReciever {
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

    private Staff selectedStaff;

    @FXML
    public void initialize() { toggleButtonsVisibility(); }

    @Override
    public void recieveData(Object data) {
        if (data instanceof Staff) {
            this.selectedStaff = (Staff) data;
            setStaff(this.selectedStaff);
            updateView();
        }
    }

    public void setStaff(Staff selectedStaff) {
        this.selectedStaff = selectedStaff;
        updateView();
        toggleButtonsVisibility();
    }

    private void toggleButtonsVisibility() {
        if (selectedStaff != null && selectedStaff.getId() > 0) {
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
        }
        if (selectedStaff != null) {
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
            saveStaff.setVisible(true);
            saveStaff.setManaged(true);
            saveStaff.setDisable(false);
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
        if (selectedStaff != null) {
            firstNameField.setText(selectedStaff.getFirstName());
            lastNameField.setText(selectedStaff.getLastName());
            nickname.setText(selectedStaff.getNickname());
            street.setText(selectedStaff.getStreet());
            areaCode.setText(String.valueOf(selectedStaff.getPostNumber()));
            city.setText(selectedStaff.getCity());
            country.setText(selectedStaff.getCountry());
            email.setText(selectedStaff.getEmail());
        }
    }

    @FXML
    public void backButton(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/staffViews/staffSearchView.fxml", "Pied Pipers", null, StaffSeachController.class);
    }

    @FXML
    public void deleteButton(ActionEvent event) {
        try {
            if (selectedStaff != null) {
                staffDAO.deleteStaff(selectedStaff);
                clearFields();
                SceneSwitcher.switchScenes(event, "/views/staffViews/staffSearchView.fxml", "Pied Pipers", null, StaffSeachController.class);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    public void saveStaff(ActionEvent event) {
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

            if (firstNameInput.isEmpty()) {
                showError(firstNameField, "First Name is required");
            }
            if (lastNameInput.isEmpty()) {
                showError(lastNameField, "Last Name is required");
            }
            if (nicknameInput.isEmpty()) {
                showError(nickname, "Nickname is required");
            }
            if (!areaCodeInput.isEmpty() && !isValidAreaCode(areaCodeInput)) {
                showError(areaCode, "Only digits and max 6");
            }
            if (!emailInput.isEmpty() && !isValidEmail(emailInput)) {
                showError(email, "Invalid email");

            }

            if (firstNameInput.isEmpty() || lastNameInput.isEmpty() || nicknameInput.isEmpty()) {
                return;
            }

            System.out.println("Validation passed. Saving staff...");
            Staff newStaff = new Staff(0, firstNameInput, lastNameInput, nicknameInput, streetInput, areaCodeInput.isEmpty() ? 0 : Integer.parseInt(areaCodeInput), cityInput, countryInput, emailInput);
            staffDAO.saveStaff(newStaff);

            SceneSwitcher.switchScenes(event, "/views/staffViews/staffSearchView.fxml", "Pied Pipers", null, StaffSeachController.class);

        } catch (Exception e) {
            e.printStackTrace();
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

            Staff existingStaff = staffDAO.getStaffById(selectedStaff.getId());

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

                SceneSwitcher.switchScenes(event, "/views/staffViews/staffSearchView.fxml", "Pied Pipers", null, StaffSeachController.class);
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
        field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
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
    @FXML
    protected void logout(ActionEvent event) {
        SceneSwitcher.switchScenes(event, "/views/generalViews/loginView.fxml", "Pied Pipers", null, loginController.class);
    }
}