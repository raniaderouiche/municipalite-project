package org.fsb.municipalite.controllers;

import java.io.IOException;
import java.util.List;

import org.fsb.municipalite.entities.Compte;
import org.fsb.municipalite.services.impl.CompteServiceImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
	int logSess;
	
	
	public void login(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/mainInterface.fxml"));
		root = loader.load();
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);

		
		
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
			CompteServiceImpl compteService = new CompteServiceImpl();
			List<String> usernameList = compteService.selectAllInONEColumn("username");
			List<String> passwordList = compteService.selectAllInONEColumn("password");
			if(usernameText.getText().matches("override")&&passwordText.getText().matches("override")){
				//default username and password
				stage.setScene(scene);
				stage.setMaximized(true);
			}
			else if(usernameList.contains(usernameText.getText())){
				//if username is correct check password
				if(passwordList.get(usernameList.indexOf(usernameText.getText())).equals(passwordText.getText())){
					//if password correct update login sessions
					Compte c=compteService.getById(compteService.findOne("username",usernameText.getText()).getId());
					logSess = c.getLoginSessions()+1;
					c.setLoginSessions(logSess);
					compteService.update(c);
					//open app
					stage.setScene(scene);
					stage.setMaximized(true);
				}
				else{
					conditionText.setText("Incorrect Password");
				}
			}else{
				conditionText.setText("Account doesn't exist");
			}
		}
	}
	
	@FXML
	void forgotPassword(MouseEvent event) {
		
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/PasswordReset.fxml"));
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		scene = new Scene(root);
		stage.setScene(scene);

		
	}
}
