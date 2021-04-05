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
	
	private String username;
	
	public void login(ActionEvent event) throws IOException {
		
		
		username = usernameText.getText();
		
		
		if(username.length()==0) {
			
			conditionText.setText("Username is empty!");
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
