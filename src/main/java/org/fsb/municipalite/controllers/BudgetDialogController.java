package org.fsb.municipalite.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.fsb.municipalite.entities.Budget;

import java.net.URL;
import java.util.ResourceBundle;


public class BudgetDialogController implements Initializable {

    @FXML
    Label titleLabel;
    @FXML
    ChoiceBox<String> secteur;
    @FXML
    TextField budget;
    @FXML
    DatePicker year;
    @FXML
    Label inv_sec;
    @FXML
    Label inv_budget;
    @FXML
    Label inv_budgetdate;

    ObservableList<String> secList = FXCollections.observableArrayList("Authorization", "Complaints", "Events", "Tools", "Projects", "Tasks", "Teams");

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        secteur.setItems(secList);
        secteur.setValue("Authorization");
    }

    public void setCurrentBudget(Budget b){
        b.setSecteur(secteur.getValue());
        b.setBudget(Double.parseDouble(budget.getText()));
        b.setYear(year.getValue());
    }

    public void setBudgetDialogPane(Budget b){
        secteur.setValue(b.getSecteur());
        budget.setText(b.getBudget() +"");
        year.setValue(b.getYear());
    }



}
