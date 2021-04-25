package org.fsb.municipalite.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.fsb.municipalite.entities.Compte;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResetByUser2Controller implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private Compte compte;
    
	@FXML
    private ChoiceBox choices;

    @FXML
    private TextField answer;

    @FXML
    private Label inv_answer;
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
				choices.setDisable(true);
	}

    @FXML
    void next(ActionEvent event) {
    	/*if(answer.getText().equals(CurrentAccount.getAnswer())) {
    		stage =(Stage)((Node)event.getSource()).getScene().getWindow();		
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/PasswordResetFinal.fxml"));
        	try {
        		root = loader.load();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        	scene = new Scene(root);
        	stage.setScene(scene);
    	}else {
    		inv_answer.setVisible(true);
    	}
    	*/
    }
    
    @FXML
    void back(ActionEvent event) {
    	stage =(Stage)((Node)event.getSource()).getScene().getWindow();		
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/ResetByUsername1.fxml"));
    	try {
    		root = loader.load();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	scene = new Scene(root);
    	stage.setScene(scene);
    }
    
    
    public void setCompte(Compte c) {
    	compte = c;
    	choices.setValue(this.compte.getUsername());
    }

}
