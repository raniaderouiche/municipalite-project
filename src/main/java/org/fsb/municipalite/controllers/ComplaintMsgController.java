package org.fsb.municipalite.controllers;

import javafx.event.ActionEvent;
import org.fsb.municipalite.services.APIs.DownloadPDF;
import org.fsb.municipalite.entities.Complaint;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.fsb.municipalite.services.impl.ComplaintServiceImpl;

public class ComplaintMsgController {
	@FXML
    Label msgLabel;
	@FXML
    Label subjectLabel;
    @FXML
    Label title;

    Complaint complaint;
	
	public void setComplaintMsgDialogPane(Complaint c) {
    	msgLabel.setText(c.getMsg());
    	subjectLabel.setText(c.getSujet());
    	complaint=c;
    }

    public void download(ActionEvent event) {
        ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
        Complaint comp = complaintService.getById(complaint.getId());
        DownloadPDF.downloadPDF(comp,msgLabel.getScene().getWindow());
    }
}
