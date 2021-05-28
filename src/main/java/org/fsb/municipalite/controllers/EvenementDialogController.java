package org.fsb.municipalite.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.fsb.municipalite.entities.Evenement;

import java.net.URL;
import java.util.ResourceBundle;

public class EvenementDialogController implements Initializable {

    @FXML Label titleLabel;
    @FXML TextField name;
    @FXML TextField budget;
    @FXML ChoiceBox place;
    @FXML DatePicker eventDate;
    @FXML Label inv_name;
    @FXML Label inv_budget;
    @FXML Label inv_eventdate;
    @FXML TextArea description;


    ObservableList<String> locationList = FXCollections.observableArrayList("Bizerte Nord", "Bizerte Sud", "Djoumine","El Alia","Ghar El Melh","Ghezala","Mateur","Menzel Bourguiba","Menzel Jemil","Ras Jabel","Tinja","Utique","Zarzouna");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        place.setValue("Bizerte Nord");
        place.setItems(locationList);
    }

    public void setCurrentEvent(Evenement evenement){
        evenement.setNom(name.getText());
        evenement.setBudget(Long.parseLong(budget.getText()));
        evenement.setLieu(place.getValue().toString());
        evenement.setDateEvenement(eventDate.getValue());
        evenement.setDescription(description.getText());
    }

    public void setEventDialogPane(Evenement evenement){
        name.setText(evenement.getNom());
        budget.setText(evenement.getBudget() +"");
        place.setValue(evenement.getLieu());
        eventDate.setValue(evenement.getDateEvenement());
        description.setText(evenement.getDescription());
    }
}
