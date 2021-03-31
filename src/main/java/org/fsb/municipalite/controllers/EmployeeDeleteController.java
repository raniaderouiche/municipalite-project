package org.fsb.municipalite.controllers;

import org.fsb.municipalite.services.impl.EmployeeServiceImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmployeeDeleteController {
	@FXML
    TextField removeID;
    @FXML
    TextField text;
	@FXML
    public void removeEmployee(){
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        Long id = Long.parseLong(removeID.getText());
        employeeService.remove(id);
        text.setText("Employee DELETED !");
        removeID.clear();
    }

    @FXML
    public void onClickEvent(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/interfaces/EmployeePage.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
