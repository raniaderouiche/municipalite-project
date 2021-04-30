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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResetByUser1Controller {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
    @FXML
    private TextField username;

    @FXML
    private Label inv_username;

    @FXML
    void back(ActionEvent event) {

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

    @FXML
    void next(ActionEvent event) {
    	
    	CompteServiceImpl compteService = new CompteServiceImpl();
    	List<String> usernameList = compteService.selectAllInONEColumn("username");
    	try {

    		if(usernameList.contains(username.getText())) {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/ResetByUsername2.fxml"));
    			root = loader.load();

    			ResetByUser2Controller ruc = loader.getController();

    			//a3mlelna select by username
    			List<Compte> cList = compteService.selectAll();
    			for(Compte c : cList) {	
    				if(c.getUsername().equals(username.getText())) {
    					ruc.setCompte(c);
    					break;
    				}
    			}
    			stage =(Stage)((Node)event.getSource()).getScene().getWindow();		
    			scene = new Scene(root);
    			stage.setScene(scene);
		}else {
			inv_username.setVisible(true);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
    }

}
