package org.fsb.municipalite.controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.fsb.municipalite.entities.Compte;

public class ResetByEmail2Controller {

    @FXML
    private JFXTextField code;

    @FXML
    private Label incorrect;
    
	private Stage stage;
	private Scene scene;
	private Parent root;

	private String resetCode;
	private Compte userAccount;

    @FXML
    void back(ActionEvent event) {

    	stage =(Stage)((Node)event.getSource()).getScene().getWindow();		
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/ResetByEmail1.fxml"));
    	try {
    		root = loader.load();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	scene = new Scene(root);
    	stage.setScene(scene);
    }

    @FXML
    void next(ActionEvent event) {
    	if(code.getText().matches(resetCode)) {

			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/PasswordResetFinal.fxml"));
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			scene = new Scene(root);

			PasswordResetFController passwordResetController = loader.getController();
			passwordResetController.setUserAccount(userAccount);

			stage.setScene(scene);


		}else
			incorrect.setVisible(true);
    }

	public String getResetCode() {
		return resetCode;
	}

	public void setResetCode(String resetCode) {
		this.resetCode = resetCode;
	}

	public Compte getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(Compte userAccount) {
		this.userAccount = userAccount;
	}
}
