package org.fsb.municipalite.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PasswordChangedController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private JFXButton loginButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginButton.setDisableVisualFocus(true);
	}
    @FXML
    void login(ActionEvent event) {
    	stage =(Stage)((Node)event.getSource()).getScene().getWindow();		
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/loginScene.fxml"));
    	try {
    		root = loader.load();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	scene = new Scene(root);
    	stage.setScene(scene);
    }


}
