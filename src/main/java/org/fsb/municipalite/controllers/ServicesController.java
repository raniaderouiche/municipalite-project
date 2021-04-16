package org.fsb.municipalite.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ServicesController {

    @FXML
    private Pane autoPane;

    @FXML
    private Pane compPane;
    
    @FXML
    void autoButton(MouseEvent event) {
    	System.out.println("auto clicked");
    	System.out.println(autoPane.getScene());
    }

    @FXML
    void compButton(MouseEvent event) {
    	System.out.println("comp clicked");

    }

}
