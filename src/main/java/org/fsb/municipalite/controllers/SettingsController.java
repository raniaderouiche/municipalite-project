package org.fsb.municipalite.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.fsb.municipalite.entities.Compte;
import org.fsb.municipalite.services.impl.CompteServiceImpl;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

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

    private Compte compte;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

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
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Password");
        dialog.setContentText("Password :");
        dialog.setHeaderText(null);
        Optional<String> result = dialog.showAndWait();
        try{
            if(result.isPresent()) {
                if (result.get().matches(compte.getPassword())) {
                    username.setDisable(false);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Incorrect Password");
                    alert.setContentText("The password you entered is incorrect");
                    alert.setHeaderText(null);
                    alert.show();
                }
            }
        }catch(Exception e){
            System.out.println("No Password Entered");
        }

    }

    public void editPassword(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Password");
        dialog.setContentText("Password :");
        dialog.setHeaderText(null);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(value -> {
            try {
                if (result.get().matches(compte.getPassword())) {
                    password1.setDisable(false);
                    password2.setDisable(false);
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
