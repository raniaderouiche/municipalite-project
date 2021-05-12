package org.fsb.municipalite.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.fsb.municipalite.entities.Revenus;

public class RevenusDialogController {

    @FXML
    Label titleLabel;
    @FXML
    TextField somme_rev;
    @FXML TextField date_rev;
    @FXML TextField source_rev;

    @FXML Label inv_somme;
    @FXML Label inv_source;
    @FXML Label inv_revdate;




    public void setCurrentRevenus(Revenus r){
        r.setSourceRev(source_rev.getText());
        r.setSommeRev(Long.parseLong(somme_rev.getText()));
        r.setDate(date_rev.getText());
    }

    public void setRevenusDialogPane(Revenus r){
        somme_rev.setText(r.getSourceRev());
        source_rev.setText(r.getSourceRev());
        date_rev.setText(r.toString());
    }
}
