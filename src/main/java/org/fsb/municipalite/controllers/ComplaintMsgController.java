package org.fsb.municipalite.controllers;


import org.fsb.municipalite.entities.Complaint;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ComplaintMsgController {
	@FXML
    Label msgLabel;
	public void setComplaintMsgDialogPane(Complaint c) {
    	msgLabel.setText(c.getMsg());
    }
	
}
