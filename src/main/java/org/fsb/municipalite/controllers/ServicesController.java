package org.fsb.municipalite.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ServicesController {

	@FXML
    private BorderPane Container;

    @FXML
    void autoButton(MouseEvent event) {
    	BorderPane MainInterface = (BorderPane) Container.getParent();
		Pane view = CustomFxmlLoader.getPage("AutorisationPage");
		MainInterface.setCenter(view);
    }

    @FXML
    void compButton(MouseEvent event) {
    	BorderPane MainInterface = (BorderPane) Container.getParent();
		Pane view = CustomFxmlLoader.getPage("ComplaintPage");
		MainInterface.setCenter(view);
    }

}
