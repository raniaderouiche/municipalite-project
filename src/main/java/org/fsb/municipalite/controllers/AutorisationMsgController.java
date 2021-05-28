package org.fsb.municipalite.controllers;


import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import org.fsb.municipalite.entities.Autorisation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.fsb.municipalite.services.APIs.DownloadPDF;
import org.fsb.municipalite.services.impl.AutorisationServiceImpl;

public class AutorisationMsgController {
	@FXML
    Label msgLabel;
	@FXML
    Label subjectLabel;
	@FXML
	MaterialDesignIconView check;
	@FXML
	Label title;

	Autorisation autorisation;
	
	public void setAutorisationMsgDialogPane(Autorisation a) {
    	msgLabel.setText(a.getMsg());
    	subjectLabel.setText(a.getSujet());
    	autorisation=a;
    }

    public void download(ActionEvent event) {
    	AutorisationServiceImpl autorisationService = new AutorisationServiceImpl();
        Autorisation auto = autorisationService.getById(autorisation.getId());
        DownloadPDF.downloadPDF(auto,msgLabel.getScene().getWindow());
    }
}
