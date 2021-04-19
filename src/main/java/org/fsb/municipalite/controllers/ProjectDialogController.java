package org.fsb.municipalite.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.entities.Materiel;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.services.impl.EquipeServiceImpl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectDialogController implements Initializable {
  
	@FXML
	Label titleLabel;
	@FXML
    TextField name;
    @FXML
    TextField budget;
    @FXML
    DatePicker start;
    @FXML
    DatePicker end;
    @FXML
    Label inv_name;
    @FXML 
    Label inv_budget;
    @FXML
    Label warning;
    @FXML
    ChoiceBox<String> place;
    @FXML 
    ChoiceBox team;
    @FXML RadioButton finished;
    @FXML RadioButton unfinished;

    ObservableList teams =  FXCollections.observableArrayList();

    ObservableList<String> locationList = FXCollections.observableArrayList("Bizerte Nord", "Bizerte Sud", "Djoumine","El Alia","Ghar El Melh","Ghezala","Mateur","Menzel Bourguiba","Menzel Jemil","Ras Jabel","Tinja","Utique","Zarzouna");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        place.setValue("Bizerte Nord");
        place.setItems(locationList);
        setChoiceBox();
    }

    public void setChoiceBox(){
        EquipeServiceImpl equipeService = new EquipeServiceImpl();
        List<Equipe> list = equipeService.selectAll();
        for(Equipe e : list){
            teams.add(e.getId() + ", " + e.getNom());
        }
        team.setItems(teams);
    }

    public void setCurrentProject(Projet projet) {
        projet.setName(name.getText());
        projet.setBudget(Integer.parseInt(budget.getText()));
        projet.setLieu(place.getValue());
        projet.setDateDebut(start.getValue());
        projet.setDateFin(end.getValue());
        if(finished.isSelected()) { projet.setEtat(Projet.Etat.Finished); }
        if (unfinished.isSelected()) {projet.setEtat(Projet.Etat.Unfinished);}
        EquipeServiceImpl equipeService = new EquipeServiceImpl();
        Equipe e = equipeService.getById(Long.parseLong(team.getValue().toString().split(",")[0]));
        projet.setEquipe(e);
    }

    public void setProjectDialogPane(Projet projet) {
        //ken ma khedmetch ma tkhafouch dali tlaa incel
        EquipeServiceImpl equipeService = new EquipeServiceImpl();
        Equipe e = equipeService.getById(projet.getEquipe().getId());
        team.setValue(e.getId() + ", " + e.getNom());
        name.setText(projet.getName());
        budget.setText(projet.getBudget() +"");
        place.setValue(projet.getLieu());
        start.setValue(projet.getDateDebut());
        end.setValue(projet.getDateFin());
        if(projet.getEtat() == Projet.Etat.Finished){
            finished.setSelected(true);
        }
        if(projet.getEtat() == Projet.Etat.Unfinished){
            unfinished.setSelected(true);
        }

    }

}
