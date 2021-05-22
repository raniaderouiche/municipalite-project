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
	    @FXML
	    Label msgLength;

	    public void setAutorisationDialogPane(Autorisation a) {
	    	name.setText(a.getNomCitoyen());
	    	cin.setText(Long.toString(a.getCin()));
	    	subject.setText(a.getSujet());
	    	msg.setText(a.getMsg());
	    	msgLength.setText(msg.getText().length()+"/3000");
	    	if(a.getEtat().equals(Etat.processed)) {
	    		processed.setSelected(true);
	    	}else if(a.getEtat().equals(Etat.unprocessed)){
	    		unprocessed.setSelected(true);
	    	}
	    }
	    public void getCurrentAutorisation(Autorisation a) {
	    	// testie hne wzid kel label wzid listener
	    	a.setNomCitoyen(name.getText());
	    	a.setSujet(subject.getText());
	    	a.setCin(Long.parseLong(cin.getText()));
		    a.setMsg(msg.getText());
	    	if(processed.isSelected()) {
	    		a.setEtat(Etat.processed);
	    	}
	    	if(unprocessed.isSelected()) {
	    		a.setEtat(Etat.unprocessed);
	    	}
	    }
}

