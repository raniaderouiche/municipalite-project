package org.fsb.municipalite.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML
	private TextField usernameText;
	@FXML
	private TextField passwordText;
	@FXML
	private Label conditionText;
	@FXML
	private Button Login_Button;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	public void login(ActionEvent event) throws IOException {
		
		
		if(usernameText.getText().length()==0) {
			
			conditionText.setText("Username is empty!");
			usernameText.setStyle("-fx-border-color: red");
			if(passwordText.getText().length()==0) passwordText.setStyle("-fx-border-color: red");
			else passwordText.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
				
		}
		else if(passwordText.getText().length()==0){
			conditionText.setText("Password is empty!");
			passwordText.setStyle("-fx-border-color: red");
			if(usernameText.getText().length()==0) usernameText.setStyle("-fx-border-color: red");
			else usernameText.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
		}
		else {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/mainInterface.fxml"));
			root = loader.load();
			
			stage =(Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.setMaximized(true);
			stage.show();
		}
	}
}
