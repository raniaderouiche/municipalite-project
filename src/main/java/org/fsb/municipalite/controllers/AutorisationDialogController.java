package org.fsb.municipalite.controllers;

import org.fsb.municipalite.entities.Autorisation;
import org.fsb.municipalite.entities.Autorisation.Etat;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;


public class AutorisationDialogController {
			    
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
	    TextField cin; 
	    @FXML
	    TextArea msg;
	    @FXML
	    Label msgLabel;
	    @FXML
	    Label labName;
	    @FXML
	    Label labCin;
	    @FXML
	    Label labSubject;
	    @FXML
	    Label labMsg;
	    @FXML
	    Label labStatus;	     
	    @FXML
	    Label titleLabel; 
	
	    RadioButton selectedRadioButton;
	    
	    @FXML
	    private ToggleGroup statusGroup;
	    public void setCurrentAutorisation(Autorisation autorisation) {
	    	autorisation.setNomCitoyen(name.getText());
	    	autorisation.setSujet(subject.getText());
	    	autorisation.setCin(Long.parseLong(cin.getText()));
	    	autorisation.setMsg(msg.getText());
		       
		       if (processed.isSelected()) {
		    	   autorisation.setEtat(Autorisation.Etat.processed);
		       }
	        if (unprocessed.isSelected()) {
	        	autorisation.setEtat(Autorisation.Etat.unprocessed);	
	        }		    
	    }
	    public void setAutorisationDialogPane(Autorisation c) {
	    	name.setText(c.getNomCitoyen());
	    	cin.setText(Long.toString(c.getCin()));
	    	subject.setText(c.getSujet());
	    	msg.setText(c.getMsg());
	    	if(c.getEtat().equals(Etat.processed)) {
	    		
	    		processed.setSelected(true);
	    	}else if(c.getEtat().equals(Etat.unprocessed)){
	    		unprocessed.setSelected(true);
	    	}
	    }
	    public void getCurrentAutorisation(Autorisation c) {
	    	c.setNomCitoyen(name.getText());
	    	c.setSujet(subject.getText());
	    	c.setCin(Long.parseLong(cin.getText()));
		    c.setMsg(msg.getText());
	    	if(processed.isSelected()) {
	    		c.setEtat(Etat.processed);
	    	}
	    	if(unprocessed.isSelected()) {
	    		c.setEtat(Etat.unprocessed);
	    	}
	    }
}

