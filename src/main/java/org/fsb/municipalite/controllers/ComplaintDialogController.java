package org.fsb.municipalite.controllers;

import org.fsb.municipalite.entities.Complaint;

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
}

