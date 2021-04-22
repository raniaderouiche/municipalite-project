package org.fsb.municipalite.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.entities.Materiel;
import org.fsb.municipalite.entities.Materiel.Etat;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.services.impl.ProjetServiceImpl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MaterielDialogController implements Initializable {

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
    ChoiceBox projectsChoice;
    @FXML
    RadioButton availableRB;
    @FXML
    RadioButton UnavailableRB;
    @FXML
    RadioButton orderRB;

    ObservableList projects = FXCollections.observableArrayList();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setChoiceBox();
	}

	public void setChoiceBox(){
		ProjetServiceImpl projetService = new ProjetServiceImpl();
		List<Projet> list = projetService.selectAll();
		for(Projet p : list){
			projects.add(p.getId() + ", " + p.getName());
		}
		projectsChoice.setItems(projects);
	}
   
    
    public void setCurrentMateriel(Materiel m) {
    	m.setNom(nameField.getText());
    	m.setReference(refField.getText());
		ProjetServiceImpl projetService = new ProjetServiceImpl();
		if(projectsChoice.getValue() != null) {
			Projet p = projetService.getById(Long.parseLong(projectsChoice.getValue().toString().split(",")[0]));
			m.setProjet(p);
		}
		
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
    	refField.setText(m.getReference());
    	if(m.getProjetValue() != -1l) {
    		ProjetServiceImpl projetService = new ProjetServiceImpl();
    		Projet p = projetService.getById(m.getProjet().getId());
    		projectsChoice.setValue(p.getId() + ", " + p.getName());
    	}
		
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
