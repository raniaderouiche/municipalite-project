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
    @FXML
    TextField date_rev;
    @FXML
    TextField source_rev;

    @FXML
    Label inv_somme;
    @FXML
    Label inv_source;
    @FXML
    Label inv_revdate;


    public void setCurrentRevenus(Revenus r){
        System.out.println(somme_rev.getText() + date_rev.getText() + source_rev.getText() + "shit");
        r.setSource_rev(source_rev.getText());
        r.setSomme_rev(Long.parseLong(somme_rev.getText()));
        r.setDate_rev(date_rev.getText());
    }

    public void setRevenusDialogPane(Revenus r){
        somme_rev.setText(r.getSomme_rev().toString());
        source_rev.setText(r.getSource_rev());
        date_rev.setText(r.getDate_rev());
    }
}
