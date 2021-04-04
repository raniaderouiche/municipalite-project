package org.fsb.municipalite.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.fsb.municipalite.entities.Materiel;
import org.fsb.municipalite.entities.Projet;

public class ProjectUpdateController {
    @FXML
    private TextField name;

    @FXML
    private TextField budget;

    @FXML
    private TextField lieu;

    @FXML
    private DatePicker start;

    @FXML
    private DatePicker end;

    public void setCurrentProject(Projet projet) {
        // testie hne wzid kel label wzid listener
        projet.setName(name.getText());
        projet.setBudget(Integer.parseInt(budget.getText()));
        projet.setDateDebut(start.getValue());
        projet.setDateFin(end.getValue());
        projet.setLieu(lieu.getText());

    }

    public void setProjectDialogPane(Projet projet) {
        name.setText(projet.getName());
        System.out.println("hi");
        budget.setText(projet.getBudget() +"");
        lieu.setText(projet.getLieu());
        start.setValue(projet.getDateDebut());
        end.setValue(projet.getDateFin());

    }

}
