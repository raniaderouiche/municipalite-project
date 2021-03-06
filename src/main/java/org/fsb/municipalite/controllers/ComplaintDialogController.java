package org.fsb.municipalite.controllers;

import org.fsb.municipalite.entities.Complaint;
import org.fsb.municipalite.entities.Complaint.Etat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;


public class ComplaintDialogController {
			    
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

	    public void setComplaintDialogPane(Complaint c) {
	    	name.setText(c.getNomCitoyen());
	    	cin.setText(Long.toString(c.getCin()));
	    	subject.setText(c.getSujet());
	    	msg.setText(c.getMsg());
			msgLength.setText(msg.getText().length()+"/3000");
			if(c.getEtat().equals(Etat.processed)) {
	    		processed.setSelected(true);
	    	}else if(c.getEtat().equals(Etat.unprocessed)){
	    		unprocessed.setSelected(true);
	    	}
	    }
	    public void getCurrentComplaint(Complaint c) {
	    	// testie hne wzid kel label wzid listener
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

