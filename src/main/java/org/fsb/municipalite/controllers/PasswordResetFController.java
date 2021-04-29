package org.fsb.municipalite.controllers;

import java.io.IOException;

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

    @FXML
    private PasswordField password1;

    @FXML
    private PasswordField password2;

	private Compte userAccount;

    @FXML
    void changePassword(ActionEvent event) {
    	if(password1.getText().matches(password2.getText()) && !password1.getText().isEmpty() && !password2.getText().isEmpty()) {
    		CompteServiceImpl compteService = new CompteServiceImpl();
			userAccount.setPassword(password1.getText());
    		compteService.update(userAccount);
    		System.out.println(userAccount);
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

	public Compte getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(Compte userAccount) {
		this.userAccount = userAccount;
	}
}
