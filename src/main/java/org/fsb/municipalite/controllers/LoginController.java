package org.fsb.municipalite.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.fsb.municipalite.entities.Compte;
import org.fsb.municipalite.services.impl.CompteServiceImpl;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
	
	private double xOffset = 0;
    private double yOffset = 0;
	
	
	public void login(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/mainInterface.fxml"));
		root = loader.load();
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		MainInterfaceController mic = loader.getController();
		
		
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
					Compte c = compteService.getById(compteService.findOne("username",usernameText.getText()).getId());
					
					if(c.getLoginSessions() == 0) {
						boolean applyPressed = launchQuestion(c);
						if(applyPressed) {
							c.setLoginSessions(c.getLoginSessions()+1);
							compteService.update(c);
							mic.setUserAccount(c);
						}
					}
					else {
						c.setLoginSessions(c.getLoginSessions()+1);
						compteService.update(c);
						mic.setUserAccount(c);
						//open app
						stage.setScene(scene);
						stage.setMaximized(true);
					}
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
	
	public boolean launchQuestion(Compte c) {

		try {
			FXMLLoader f = new FXMLLoader();
			f.setLocation(getClass().getResource("/interfaces/QuestionsDialog.fxml"));
			Pane questionsDialogPane = f.load();

			//get the current controller and put it in edc
			SecurityQuestionController sqc = f.getController();

			Dialog<ButtonType> d = new Dialog<>();

			//this is just for adding an icon to the dialog pane
			Stage s = (Stage) d.getDialogPane().getScene().getWindow();
			s.getIcons().add(new Image("/assets/img/icon.png"));

			d.setDialogPane((DialogPane) questionsDialogPane);
			d.setTitle("Security Question");
			d.setResizable(false);
			d.initStyle(StageStyle.UNDECORATED);

			//these two are for moving the window with the mouse
			questionsDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					xOffset = event.getSceneX();
					yOffset = event.getSceneY();
				}
			});

			questionsDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					d.setX(event.getScreenX() - xOffset);
					d.setY(event.getScreenY() - yOffset);
				}
			});


			sqc.getAnswer().textProperty().addListener((observable, oldValue, newValue) -> {
				if(newValue.isEmpty()) {
					sqc.getInv_answer().setVisible(true);

				}else
					sqc.getInv_answer().setVisible(false);
			});


			//to apply css on the dialog pane buttons
			d.getDialogPane().lookupButton(ButtonType.OK).getStyleClass().add("dialogButtons");
			d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");
			//make name field first to be selected
			sqc.getAnswer().requestFocus();

			d.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(Bindings.createBooleanBinding(() ->
			sqc.getAnswer().getText().isEmpty() || sqc.getQuestionChoice().getValue() == null , sqc.getAnswer().textProperty(), sqc.getQuestionChoice().valueProperty()));
			Optional<ButtonType> clickedButton = d.showAndWait();
			if (clickedButton.get() == ButtonType.OK) {
				c.setQuestion(sqc.getQuestionChoice().getValue().toString());
				c.setAnswer(sqc.getAnswer().getText());
				
				stage.setScene(scene);
				stage.setMaximized(true);
				return true;
				
			}else
				return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
