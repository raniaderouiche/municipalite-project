package org.fsb.municipalite.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import java.net.URL;
import java.util.ResourceBundle;

public class ActivityReportsMsgController implements Initializable {
    @FXML
    ChoiceBox<String> secteurbox;
    @FXML
    DatePicker datelabel;

    ObservableList<String> secList = FXCollections.observableArrayList("Authorization", "Complaints", "Events", "Tools", "Projects", "Tasks", "Teams");

    public void initialize(URL arg0, ResourceBundle arg1) {
        secteurbox.setItems(secList);
        secteurbox.setValue("Authorization");
    }

    @FXML
    public void downloadA(ActionEvent event) {
        System.out.println("download button clicked");
        ActivityReportsPrintController.downloadPDF(secteurbox.getValue(),datelabel.getValue(), secteurbox.getScene().getWindow());

        }

    }



