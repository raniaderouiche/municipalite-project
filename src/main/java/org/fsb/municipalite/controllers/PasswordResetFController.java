package org.fsb.municipalite.controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.fsb.municipalite.entities.Compte;
import org.fsb.municipalite.services.impl.CompteServiceImpl;


public class PasswordResetFController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
    @FXML
    private Label notmatch;

    //4 fields to enable showing and hidding password fields
    @FXML
    private JFXPasswordField  password1;

    @FXML
    private JFXPasswordField password2;

	@FXML
	private JFXTextField textpassword1;

	@FXML
	private JFXTextField textpassword2;

	private Compte userAccount;

	//this variable check which field is active when pressing the change password button
	private boolean activeFields = true;

    @FXML
    void changePassword(ActionEvent event) {

    	if(activeFields == false){
    		password1.setText(textpassword1.getText());
    		password2.setText(textpassword2.getText());
		}
    	if(password1.getText().matches(password2.getText()) && !password1.getText().isEmpty() && !password2.getText().isEmpty()) {
    		CompteServiceImpl compteService = new CompteServiceImpl();
			userAccount.setPassword(password1.getText());
    		compteService.update(userAccount);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/PasswordChanged.fxml"));
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			scene = new Scene(root);
			stage.setScene(scene);
		}else{
    		notmatch.setVisible(true);
		}
    }


	@FXML
	void showPassword(ActionEvent event) {

    	if(password1.isVisible()){
    		activeFields = false;

			password1.setVisible(false);
			password2.setVisible(false);

			textpassword1.setText(password1.getText());
			textpassword2.setText(password2.getText());

			textpassword1.setVisible(true);
			textpassword2.setVisible(true);
		}
    	else if (textpassword1.isVisible()){
    		activeFields = true;

    		textpassword1.setVisible(false);
    		textpassword2.setVisible(false);

    		password1.setText(textpassword1.getText());
    		password2.setText(textpassword2.getText());

    		password1.setVisible(true);
    		password2.setVisible(true);
		}

	}

	public Compte getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(Compte userAccount) {
		this.userAccount = userAccount;
	}
}
