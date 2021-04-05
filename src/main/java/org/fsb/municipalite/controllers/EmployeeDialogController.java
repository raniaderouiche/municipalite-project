package org.fsb.municipalite.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Projet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;


public class EmployeeDialogController implements Initializable{

    @FXML
    TextField nom_field;

    @FXML
    TextField prenom_field;

    @FXML
    TextField cin_field;

    @FXML
    ChoiceBox<String> civilStatusBox;

    @FXML
    ToggleGroup genderGroup;
    
    @FXML
    RadioButton maleRadioButton;
    @FXML
    RadioButton femaleRadioButton;
    @FXML
    DatePicker dnPicker;

    ObservableList<String> civilStatusList = FXCollections.observableArrayList("Single", "Married", "Divorced");
    RadioButton selectedRadioButton;
    
    public void initialize(URL arg0, ResourceBundle arg1) {
    	civilStatusBox.setValue("Single");
    	civilStatusBox.setItems(civilStatusList);
    }
    
    public void setCurrentEmployee(Employee e) {
        // testie hne wzid kel label wzid listener
        e.setNom(nom_field.getText());
        e.setPrenom(prenom_field.getText());
        e.setCin(cin_field.getText());
        e.setEtatCivil(civilStatusBox.getSelectionModel().getSelectedItem());
        e.setSexe(((RadioButton)genderGroup.getSelectedToggle()).getText());
        e.setDateNaissance(dnPicker.getValue().atStartOfDay());

    }
    public void setEmpDialogPane(Employee e) {
    	selectedRadioButton = (RadioButton) genderGroup.getSelectedToggle();
    	
    	nom_field.setText(e.getNom());
    	prenom_field.setText(e.getPrenom());
    	cin_field.setText(e.getCin());
    	civilStatusBox.setValue(e.getEtatCivil());
    	if(e.getSexe().equals("Male")) {
    		maleRadioButton.setSelected(true);
    	}else {
    		femaleRadioButton.setSelected(true);
    	}
    	dnPicker.setValue(e.getDateNaissance().toLocalDate());
    }
    
   
}
