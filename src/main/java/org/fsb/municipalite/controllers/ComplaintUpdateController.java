package org.fsb.municipalite.controllers;

import org.fsb.municipalite.entities.Complaint;	
import org.fsb.municipalite.entities.Complaint.Etat;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ComplaintUpdateController {

    @FXML
    private TextField citizenNameField;

    @FXML
    private TextField subjectField;
    
    @FXML
    private ToggleGroup statusGroup;
    
    @FXML
    private RadioButton processedRB;

    @FXML
    private RadioButton unprocessedRB;
    
    RadioButton selectedRadioButton;
    
    public void getCurrentComplaint(Complaint c) {
    	// testie hne wzid kel label wzid listener
    	c.setNomCitoyen(citizenNameField.getText());
    	c.setSujet(subjectField.getText());
    	if(processedRB.isSelected()) {
    		c.setEtat(Etat.processed);
    	}
    	if(unprocessedRB.isSelected()) {
    		c.setEtat(Etat.unprocessed);
    	}
    }
    public void setComplaintDialogPane(Complaint c) {
    	selectedRadioButton = (RadioButton) statusGroup.getSelectedToggle();
    	
    	citizenNameField.setText(c.getNomCitoyen());
    	subjectField.setText(c.getSujet());
    	if(c.getEtat().equals(Etat.processed)) {
    		
    		processedRB.setSelected(true);
    	}else if(c.getEtat().equals(Etat.unprocessed)){
    		unprocessedRB.setSelected(true);
    	}
    }
}
