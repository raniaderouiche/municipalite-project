package org.fsb.municipalite.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    Label inv_name;
    @FXML
    Label inv_ref;


}
