package org.fsb.municipalite.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import org.fsb.municipalite.entities.Complaint;
import org.fsb.municipalite.services.impl.ComplaintServiceImpl;

public class ComplaintAddController {

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
    public void addComplaint() {
    	try {
	        Complaint complaint = new Complaint();
	        complaint.setNomCitoyen(name.getText());
	        complaint.setSujet(subject.getText());
	        if (processed.isSelected()) {
	            complaint.setEtat(Complaint.Etat.processed);
	        }
	        if (unprocessed.isSelected()) {
	            complaint.setEtat(Complaint.Etat.unprocessed);
	        }
	        ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
	        complaintService.create(complaint);
	        msg.setText("COMPLAINT ADDED !");
	        name.clear();
	        subject.clear();
        }catch(Exception e) {
        	e.printStackTrace();
        }
    }
  
}
