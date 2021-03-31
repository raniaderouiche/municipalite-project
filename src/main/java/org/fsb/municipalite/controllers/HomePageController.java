package org.fsb.municipalite.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePageController {
	 @FXML
	 public void onClickViewEmployee(ActionEvent event){
     try {
            Parent parent = FXMLLoader.load(getClass().getResource("/interfaces/EmployeePage.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
	@FXML
    public void onClickAction(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/interfaces/MaterielPage.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
    @FXML
    public void logout(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/interfaces/Login.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}
