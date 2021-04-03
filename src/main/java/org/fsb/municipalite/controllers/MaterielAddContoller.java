package org.fsb.municipalite.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.fsb.municipalite.entities.Materiel;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.services.impl.MaterielServiceImpl;

public class MaterielAddContoller {


    @FXML
    RadioButton avail;
    @FXML 
    RadioButton unavail;
    @FXML 
    RadioButton outoford;
    @FXML
    TextField name;
    @FXML
    TextField ref;
    @FXML
    TextField proj_id;
    @FXML
    TextField msg;
    @FXML
    public void addItem() throws ClassNotFoundException {
        Materiel materiel = new Materiel();
        materiel.setNom(name.getText());
        materiel.setReference(Long.parseLong(ref.getText()));
        if (avail.isSelected()) {
            materiel.setEtat(Materiel.Etat.disponible);
        }
        if (unavail.isSelected()) {
            materiel.setEtat(Materiel.Etat.occupe);
        }
        if (outoford.isSelected()) {
            materiel.setEtat(Materiel.Etat.enPanne);
        }
        MaterielServiceImpl materielService = new MaterielServiceImpl();
        materielService.create(materiel);
        msg.setText("ITEM ADDED !");
        name.clear();
        ref.clear();
        proj_id.clear();
    }
    @FXML
    public void onClickEventBack(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/interfaces/MaterielPage.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public void getFieldsData() {
    	
    }
}
