package org.fsb.municipalite.controllers;

import org.fsb.municipalite.entities.Complaint;
import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.entities.Complaint.Etat;
import org.fsb.municipalite.services.impl.ComplaintServiceImpl;

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
	    
	    public void setCurrentComplaint(Complaint complaint) {
	    	complaint.setNomCitoyen(name.getText());
	    	complaint.setSujet(subject.getText());
	    	complaint.setCin(Long.parseLong(cin.getText()));
	    	complaint.setMsg(msg.getText());

	    	if (processed.isSelected()) {
	    		complaint.setEtat(Complaint.Etat.processed);
	    	}
	    	if (unprocessed.isSelected()) {
	    		complaint.setEtat(Complaint.Etat.unprocessed);	
	    	}		    
	    }
	    public void setComplaintDialogPane(Complaint c) {
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

