package org.fsb.municipalite.controllers;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import org.fsb.municipalite.entities.Autorisation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.fsb.municipalite.services.APIs.DownloadPDF;
import org.fsb.municipalite.services.impl.AutorisationServiceImpl;

public class AutorisationMsgController {
	@FXML
	TextArea msgLabel;
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
	@FXML
    public void download(ActionEvent event) {
    	AutorisationServiceImpl autorisationService = new AutorisationServiceImpl();
        Autorisation auto = autorisationService.getById(autorisation.getId());
        DownloadPDF.downloadPDF(auto,msgLabel.getScene().getWindow());
    }
}
