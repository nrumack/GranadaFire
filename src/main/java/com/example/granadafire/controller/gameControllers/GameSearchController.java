package com.example.granadafire.controller.gameControllers;

import com.example.granadafire.DAO.GameDAO;
import com.example.granadafire.entities.Game;
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

public class GameSearchController {
        private GameDAO gameDAO = new GameDAO();

        @FXML
        private TextField searchField;
        @FXML
        private ListView<Game> gameResultList;

        @FXML
        public void initialize() {
            List<Game> game = gameDAO.getAllGames();
            ObservableList<Game> observableStaff = FXCollections.observableArrayList(game);

            gameResultList.setItems(observableStaff);
            gameResultList.setOnMouseClicked(this::handleClick);
        }

        private void handleClick(MouseEvent event) {
            if (event.getClickCount() == 1) {
                Game selectedItem = gameResultList.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gameViews/gameView.fxml"));
                        Stage stage = new Stage();
                        stage.setScene(new Scene(loader.load()));
                        GameController controller = loader.getController();
                        controller.setGame(selectedItem);
                        stage.setTitle("Pied Pipers");
                        stage.show();
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        @FXML
        public void searchGames(){
            String keyword = searchField.getText().trim();
            if (keyword.isEmpty()) {
                List<Game> allGames = gameDAO.getAllGames();
                gameResultList.setItems(FXCollections.observableArrayList(allGames));
            } else {
                List<Game> filteredGame = gameDAO.getGamesByName(keyword);
                gameResultList.setItems(FXCollections.observableArrayList(filteredGame));
            }
        }

        public void backButton(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/generalViews/mainMenuView.fxml"));
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gameViews/gameView.fxml"));
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
