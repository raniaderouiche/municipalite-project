package org.fsb.municipalite.controllers;

import org.fsb.municipalite.entities.Materiel;
import org.fsb.municipalite.entities.Materiel.Etat;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class MaterielUpdateController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField refField;

    @FXML
    private TextField projectField;
    
    @FXML
    private ToggleGroup statusGroup;
    
    @FXML
    private RadioButton availableRB;

    @FXML
    private RadioButton UnavailableRB;

    @FXML
    private RadioButton orderRB;
    
    RadioButton selectedRadioButton;
    
    public void getCurrentMateriel(Materiel m) {
    	// testie hne wzid kel label wzid listener
    	m.setNom(nameField.getText());
    	m.setReference(Long.parseLong(refField.getText()));
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
    	selectedRadioButton = (RadioButton) statusGroup.getSelectedToggle();
    	
    	nameField.setText(m.getNom());
    	System.out.println("hi");
    	refField.setText(m.getReference().toString());
    	//andna mochkla fel idproject so ala heka dima null 7ata nriglouha
    	projectField.setText("null");
    	
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
