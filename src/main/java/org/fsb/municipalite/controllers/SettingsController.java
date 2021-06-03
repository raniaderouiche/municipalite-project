package org.fsb.municipalite.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.fsb.municipalite.entities.Compte;
import org.fsb.municipalite.services.impl.CompteServiceImpl;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingsController {

    @FXML
    Label firstName;
    @FXML
    Label lastName;
    @FXML
    Label cin;
    @FXML
    Label dob;
    @FXML
    Label gender;
    @FXML
    Label civilStatus;
    @FXML
    Label role;
    @FXML
    TextField username;
    @FXML
    TextField password1;
    @FXML
    TextField password2;
    @FXML
    Label inv_password1;
    @FXML
    Label inv_password2;

    @FXML
    Label inv_username;

    @FXML Button edit_username;
    @FXML Button edit_password;


    private Compte compte;


    public void setAccountInfos(Compte c){
        CompteServiceImpl cService = new CompteServiceImpl();
        compte= cService.getById(c.getId());
        firstName.setText(compte.getEmployee().getPrenom());
        lastName.setText(compte.getEmployee().getNom());
        cin.setText(compte.getEmployee().getCin());
        dob.setText(compte.getEmployee().getDateNaissance().toString());
        gender.setText(compte.getEmployee().getSexe());
        civilStatus.setText(compte.getEmployee().getEtatCivil());
        role.setText(compte.getEmployee().getRole());
        username.setText(compte.getUsername());
        password1.setText(compte.getPassword());
    }

    public void setAccountUpdate(Compte compte){
        compte.setUsername(username.getText());
        compte.setPassword(password1.getText());
    }

    public void editUsername(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Enter Password");
        dialog.setHeaderText(null);

        ImageView imageView = new ImageView(this.getClass().getResource("/assets/img/Privacy.png").toString());
        imageView.setFitHeight(75);
        imageView.setFitWidth(75);
        dialog.setGraphic(imageView);

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        PasswordField password = new PasswordField();
        password.getStyleClass().clear();

        password.setPromptText("Password");
        //set up gridpane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        //add password field
        grid.add(new Label("Password:"), 0, 0);
        grid.add(password, 1, 0);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> password.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return password.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(Password -> {
            try {
                if (result.get().matches(compte.getPassword())) {
                    username.setDisable(false);
                    edit_username.setVisible(false);

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Incorrect Password");
                    alert.setContentText("The password you entered is incorrect");
                    alert.setHeaderText(null);
                    alert.show();
                }
            } catch (Exception e) {
                System.out.println("No Password Entered");
            }
        });

    }

    public void editPassword(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Enter Password");
        dialog.setHeaderText(null);

        ImageView imageView = new ImageView(this.getClass().getResource("/assets/img/Privacy.png").toString());
        imageView.setFitHeight(75);
        imageView.setFitWidth(75);
        dialog.setGraphic(imageView);

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        PasswordField password = new PasswordField();
        password.getStyleClass().clear();
        password.getStyleClass().add("dialogTextFields");


        password.setPromptText("Password");
        //set gridpane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("Password:"), 0, 0);
        grid.add(password, 1, 0);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> password.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return password.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(Password -> {
            try {
                if (result.get().matches(compte.getPassword())) {
                    password1.setDisable(false);
                    password2.setDisable(false);
                    edit_password.setVisible(false);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Incorrect Password");
                    alert.setContentText("The password you entered is incorrect");
                    alert.setHeaderText(null);
                    alert.show();
                }
            } catch (Exception e) {
                System.out.println("No Password Entered");
            }
        });

    }
}
