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

public class FinanceReportMsgController implements Initializable {
    @FXML
    ChoiceBox<String> yearbox;


    ObservableList<String> yearList = FXCollections.observableArrayList("2021", "2022", "2023", "2024");
    public void initialize(URL arg0, ResourceBundle arg1) {
        yearbox.setItems(yearList);
        yearbox.setValue("2021");

    }
    public void downloadF(ActionEvent event) {
        System.out.println("download button clicked");

        FinanceReportsPrintController.downloadPDF( yearbox.getValue(), yearbox.getScene().getWindow());
    }
}
