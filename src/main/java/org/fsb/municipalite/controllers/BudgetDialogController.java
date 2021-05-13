package org.fsb.municipalite.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.fsb.municipalite.entities.Budget;



public class BudgetDialogController  {

    @FXML
    Label titleLabel;
    @FXML
    TextField secteur;
    @FXML
    TextField budget;
    @FXML
    TextField year;

    @FXML
    Label inv_sec;
    @FXML
    Label inv_budget;
    @FXML
    Label inv_budgetdate;


    public void setCurrentBudget(Budget b){
        b.setSecteur(secteur.getText());
        b.setBudget(Long.parseLong(budget.getText()));
        b.setYear(year.getText());
    }

    public void setBudgetDialogPane(Budget b){
        secteur.setText(b.getSecteur());
        budget.setText(b.getBudget() +"");
        year.setText(b.getYear());
    }


}
