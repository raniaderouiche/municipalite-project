package org.fsb.municipalite.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.fsb.municipalite.entities.Depenses;

import java.net.URL;
import java.util.ResourceBundle;


public class DepensesDialogController implements Initializable {

    @FXML
    Label titleLabel;
    @FXML
    ChoiceBox<String> secteur_dep;
    @FXML
    TextField somme_dep;
    @FXML
    DatePicker date_dep;
    @FXML
    Label inv_depdate;
    @FXML
    Label inv_depsec;
    @FXML
    Label inv_depsomme;

    ObservableList<String> secList = FXCollections.observableArrayList("Authorization", "Complaints", "Events", "Tools", "Projects", "Tasks", "Teams");

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        secteur_dep.setItems(secList);
        secteur_dep.setValue("Authorization");
    }

    public void setCurrentDepenses (Depenses d){
        d.setSecteur_dep(secteur_dep.getValue());
        d.setDate_dep(date_dep.getValue());
        d.setSomme_dep(Double.parseDouble(somme_dep.getText()));
    }

    public void setDepensesDialogPane(Depenses d){
        secteur_dep.setValue(d.getSecteur_dep());
        somme_dep.setText(d.getSomme_dep() +"");
        date_dep.setValue(d.getDate_dep());

    }

}
