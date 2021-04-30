package org.fsb.municipalite.controllers;

import javafx.fxml.FXML; 
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import org.fsb.municipalite.entities.Autorisation;
import org.fsb.municipalite.services.impl.AutorisationServiceImpl;

public class AutorisationAddController {

	@FXML
    TextField name;

    @FXML
    TextField subject;

    @FXML
    RadioButton unprocessed;

    @FXML
    ToggleGroup status;

    @FXML
    RadioButton processed;
    @FXML
    Label msg;
    @FXML
    TextField cin;
    @FXML
    TextArea core;
    
    
    @FXML
    public void addAutorisation() {
    	try {
    		Autorisation autorisation = new Autorisation();
    		autorisation.setNomCitoyen(name.getText());
    		autorisation.setSujet(subject.getText());
    		autorisation.setCin(Long.parseLong(cin.getText()));
    		autorisation.setMsg(core.getText());
	        
	        if (processed.isSelected()) {
	        	autorisation.setEtat(Autorisation.Etat.processed);
	        }
	        if (unprocessed.isSelected()) {
	        	autorisation.setEtat(Autorisation.Etat.unprocessed);
	        }
	        AutorisationServiceImpl autorisationService = new AutorisationServiceImpl();
	        autorisationService.create(autorisation);
	        msg.setText("AUTORISATION ADDED !");
	        name.clear();
	        subject.clear();
        }catch(Exception e) {
        	e.printStackTrace();
        }
    }
  
}

