package org.fsb.municipalite.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.bytebuddy.asm.Advice;
import org.fsb.municipalite.entities.Autorisation;
import org.fsb.municipalite.entities.Depenses;
import org.fsb.municipalite.entities.Revenus;
import org.fsb.municipalite.services.impl.AutorisationServiceImpl;
import org.fsb.municipalite.services.impl.DepensesServiceImpl;
import org.fsb.municipalite.services.impl.RevenusServiceImpl;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


public class ActivityReportsMsgController implements Initializable {
    @FXML
    ChoiceBox<String> secteurbox;
    @FXML
    DatePicker datelabel;

    LocalDate tf;
    String sec;
    Long revsum = 0L;
    Revenus rev;
    ObservableList<String> secList = FXCollections.observableArrayList("Authorisation", "Complaints", "Events", "Materiel", "Projets", "Tasks", "Teams");


    public void initialize(URL arg0, ResourceBundle arg1) {
        secteurbox.setItems(secList);
        secteurbox.setValue("Authorisation");

    }

    public void downloadA(ActionEvent event) {
        System.out.println("download button clicked");

        ActivityReportsPrintController.downloadPDF(secteurbox.getValue(),datelabel.getValue(), secteurbox.getScene().getWindow());



        /*tf=datelabel.getValue();
        sec=secteurbox.getValue();
        if(sec.matches("Authorisation")) {
            RevenusServiceImpl revenuService = new RevenusServiceImpl();
            List<Revenus> revlist = revenuService.selectBy("SOURCE_REV", "'Authorisation'");
            for (Revenus r : revlist) {
                if (r.getDate_rev().equals(tf)) {
                    System.out.println(r.getDate_rev() );
                    System.out.println(r.getSomme_rev());
                    revsum=revsum+r.getSomme_rev();
                    System.out.println(revsum);
                }
            }
        }



        }*/
    }
}


/*tf=datelabel.getValue();
            if(revlist.get(2).equals(tf)) {
                System.out.println(revlist);
            }*/