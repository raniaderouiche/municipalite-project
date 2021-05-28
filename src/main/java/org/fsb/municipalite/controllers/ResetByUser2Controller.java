package org.fsb.municipalite.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import org.fsb.municipalite.entities.Compte;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private JFXTextField answer;

    @FXML
    private Label inv_answer;
    

    ObservableList<String> questionsList = FXCollections.observableArrayList("What was your childhood nickname?", "What is the name of your favorite childhood friend?", "In what city or town was your first job?", "In what city or town did your parents meet?");
	
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	choices.setItems(questionsList);

	}

    @FXML
    void next(ActionEvent event) {
    	if(choices.getValue() != null)
    		if(choices.getValue().toString().equals(compte.getQuestion()))
    			if(answer.getText().equals(compte.getAnswer())) {
    				stage =(Stage)((Node)event.getSource()).getScene().getWindow();		
    				FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/PasswordResetFinal.fxml"));
    				try {
    					root = loader.load();
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
    				scene = new Scene(root);
    				System.out.println(compte);
    				PasswordResetFController passwordResetController = loader.getController();
    				passwordResetController.setUserAccount(compte);

    				stage.setScene(scene);

    			}else {
    				inv_answer.setText("Verify your answer");
    				inv_answer.setVisible(true);
    			}
    		else {
				inv_answer.setText("Invalid!");
    			inv_answer.setVisible(true);
    		}
    	else {
    		inv_answer.setText("please fill all required fields");
			inv_answer.setVisible(true);
    	}
    		
    		

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
    }

}
