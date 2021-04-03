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
			//root = FXMLLoader.load(getClass().getResource("/scene_2.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/mainInterface.fxml"));
			root = loader.load();
			
			//to display username on scene 2
/*			Scene2Controller s2c = loader.getController();
			s2c.displayName(username);

			s2c.contentBorderPane.setCenter(CustomFxmlLoader.getPage("Dashboard_GUI"));
			
*/
			stage =(Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setMaximized(true);
			stage.show();
		}
	}
}
