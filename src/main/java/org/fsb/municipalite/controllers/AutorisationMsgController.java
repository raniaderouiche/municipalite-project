package org.fsb.municipalite.controllers;

import org.fsb.municipalite.entities.Autorisation;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AutorisationMsgController {
	@FXML
    Label msgLabel;
	public void setAutorisationMsgDialogPane(Autorisation c) {
    	msgLabel.setText(c.getMsg());
    }
	
}
