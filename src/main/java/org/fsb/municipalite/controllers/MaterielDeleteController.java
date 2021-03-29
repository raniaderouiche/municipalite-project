package org.fsb.municipalite.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.fsb.municipalite.services.impl.MaterielServiceImpl;

public class MaterielDeleteController {
    @FXML
    TextField removeID;
    @FXML
    TextField text;
    @FXML
    public void removeItem(){
        MaterielServiceImpl materielService = new MaterielServiceImpl();
        Long id = Long.parseLong(removeID.getText());
        materielService.remove(id);
        text.setText("ITEM DELETED !");
        removeID.clear();
    }

    @FXML
    public void onClickEvent(ActionEvent event) {
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

}
