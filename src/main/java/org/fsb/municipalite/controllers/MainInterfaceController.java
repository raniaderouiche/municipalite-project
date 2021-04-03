package org.fsb.municipalite.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainInterfaceController{

	@FXML
	Label username_label;	
	@FXML
	Label l2;	
	@FXML
	Pane mainPane;	
	@FXML
	BorderPane contentBorderPane;
	
	
	public void displayName(String username) {
		username_label.setText(username);
	}
	@FXML
	public void logoutButton(MouseEvent event) {
		Stage s = (Stage)((Label)event.getSource()).getScene().getWindow();
		s.close();
	}
	
	@FXML
	public void dashboardButton(MouseEvent event) {
		System.out.println("Dashboard Clicked");
		
		Pane view = CustomFxmlLoader.getPage("Dashboard_GUI");
		contentBorderPane.setCenter(view);

	}
	@FXML
	private void servicesButton(MouseEvent event) {
		System.out.println("Services Clicked");
		Pane view = CustomFxmlLoader.getPage("scene_1");
		contentBorderPane.setCenter(view);
	}
	@FXML
	private void toolsButton(MouseEvent event) {
		//l2 == event.getSource() : true
		System.out.println("Tools Button Clicked");
		Pane view = CustomFxmlLoader.getPage("MaterielPage");
		contentBorderPane.setCenter(view);
	}
	@FXML
	private void TeamsStaffButton(MouseEvent event) {
		
		System.out.println("TS Clicked");
		Pane view = CustomFxmlLoader.getPage("TeamsStaffGUI");
		
		contentBorderPane.setCenter(view);
	}
	@FXML
	private void FinanceButton(MouseEvent event) {
		
		System.out.println("Finance Clicked");
		Pane view = CustomFxmlLoader.getPage("FinanceGUI");
		
		contentBorderPane.setCenter(view);
	}
}
