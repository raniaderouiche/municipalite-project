package org.fsb.municipalite.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.fsb.municipalite.entities.Depenses;


public class DepensesDialogController {

    @FXML Label titleLabel;
    @FXML TextField secteur_dep;
    @FXML TextField somme_dep;
    @FXML DatePicker date_dep;


    @FXML DatePicker inv_depdate;
    @FXML Label inv_depsec;
    @FXML Label inv_depsomme;






    public void setCurrentDepenses (Depenses d){
        d.setSecteurDep(secteur_dep.getText());
        d.setDateDep(date_dep.getValue());
        d.setSommeDep(Long.parseLong(somme_dep.getText()));
    }

    public void setDepensesDialogPane(Depenses d){
        secteur_dep.setText(d.getSecteurDep());
        somme_dep.setText(d.getSommeDep() +"");
        date_dep.setValue(d.getDateDep());

    }


}
