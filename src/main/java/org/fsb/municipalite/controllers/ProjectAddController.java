package org.fsb.municipalite.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.services.impl.EquipeServiceImpl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectAddController implements Initializable {

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

    @FXML Label inv_budget;

    @FXML Label warning;

    @FXML
    ChoiceBox<String> place;

    @FXML ChoiceBox team;

    ObservableList teams =  FXCollections.observableArrayList();

    ObservableList<String> locationList = FXCollections.observableArrayList("Bizerte Nord", "Bizerte Sud", "Djoumine","El Alia","Ghar El Melh","Ghezala","Mateur","Menzel Bourguiba","Menzel Jemil","Ras Jabel","Tinja","Utique","Zarzouna");

    public void setChoiceBox(){
        EquipeServiceImpl equipeService = new EquipeServiceImpl();
        List<Equipe> list = equipeService.selectAll();
        for(Equipe e : list){
            teams.add(e.getId() + ", " + e.getNom());
        }
        team.setItems(teams);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChoiceBox();
        place.setValue("Bizerte Nord");
        place.setItems(locationList);
    }
}
