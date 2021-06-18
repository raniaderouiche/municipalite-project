package org.fsb.municipalite.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class FinancesController {
    @FXML
    private BorderPane ContainerF;

    @FXML
    void actButton(MouseEvent event) {
        System.out.println("Activity Reports clicked");
        BorderPane MainInterface = (BorderPane) ContainerF.getParent();
        Pane view = CustomFxmlLoader.getPage("ActivityMsg");
        MainInterface.setCenter(view);
    }

    @FXML
    void financeButton(MouseEvent event) {
        System.out.println("Financial Reports clicked");

        BorderPane MainInterface = (BorderPane) ContainerF.getParent();
        Pane view = CustomFxmlLoader.getPage("FinanceReportMsg");
        MainInterface.setCenter(view);
    }
    @FXML
    void statsButton(MouseEvent event) {
        System.out.println("Statistiques page clicked");

        BorderPane MainInterface = (BorderPane) ContainerF.getParent();
        Pane view = CustomFxmlLoader.getPage("BudRevDepPage");
        MainInterface.setCenter(view);
    }
}
