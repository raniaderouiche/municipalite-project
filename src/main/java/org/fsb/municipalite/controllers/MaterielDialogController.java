package org.fsb.municipalite.controllers;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import org.fsb.municipalite.entities.Materiel;
import org.fsb.municipalite.entities.Materiel.Etat;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class MaterielDialogController {

	@FXML
    Label titleLabel;
    @FXML
    TextField nameField;
    @FXML
	Label inv_name;
    @FXML
    TextField refField;
    @FXML 
    Label inv_ref;
    @FXML
    ToggleGroup statusGroup;
    @FXML
    ChoiceBox projectsChoice;
    @FXML
    RadioButton availableRB;
    @FXML
    RadioButton UnavailableRB;
    @FXML
    RadioButton orderRB;

    
    
   
    
    public void getCurrentMateriel(Materiel m) {
    	// testie hne wzid kel label wzid listener
    	m.setNom(nameField.getText());
    	m.setReference(refField.getText());
    	//set project to fix !
    	//m.setProjet(projet);
    	if(availableRB.isSelected()) {
    		m.setEtat(Etat.disponible);
    	}
    	if(UnavailableRB.isSelected()) {
    		m.setEtat(Etat.occupe);
    	}
    	if(orderRB.isSelected()) {
    		m.setEtat(Etat.enPanne);
    	}
    	
    }
    public void setMaterielDialogPane(Materiel m) {
    	
    	nameField.setText(m.getNom());
    	refField.setText(m.getReference().toString());
    	
    	if(m.getEtat().equals(Etat.disponible)) {
    		
    		availableRB.setSelected(true);
    	}else if(m.getEtat().equals(Etat.occupe)){
    		UnavailableRB.setSelected(true);
    	}
    	else {
    		orderRB.setSelected(true);
    	}
    	

    }
}
