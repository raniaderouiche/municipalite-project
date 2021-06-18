package org.fsb.municipalite.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.fsb.municipalite.entities.Revenus;

import java.net.URL;
import java.util.ResourceBundle;

public class RevenusDialogController implements Initializable{

    @FXML
    Label inv_source;

    @FXML
    TextField somme_rev;

    @FXML
    Label inv_somme;

    @FXML
    Label inv_revdate;

    @FXML
    DatePicker date_rev;

    @FXML
    ChoiceBox<String> source_rev;

    @FXML
    Label titleLabel;

    ObservableList<String> secList = FXCollections.observableArrayList("Authorization", "Complaints", "Events", "Tools", "Projects", "Tasks", "Teams");

    public void initialize(URL arg0, ResourceBundle arg1) {
        source_rev.setItems(secList);
        source_rev.setValue("Authorization");
    }
    public void setCurrentRevenus(Revenus r){
        r.setSource_rev(source_rev.getValue());
        r.setSomme_rev(Double.parseDouble(somme_rev.getText()));
        r.setDate_rev(date_rev.getValue());
    }

    public void setRevenusDialogPane(Revenus r){
        somme_rev.setText(r.getSomme_rev()+"");
        source_rev.setValue(r.getSource_rev());
        date_rev.setValue(r.getDate_rev());
    }
}
