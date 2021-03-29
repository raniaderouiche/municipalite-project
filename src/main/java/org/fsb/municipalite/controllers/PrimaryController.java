package org.fsb.municipalite.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


public class PrimaryController {
    @FXML
    public void onClickEvent(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/interfaces/HomePage.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
