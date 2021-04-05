package org.fsb.municipalite.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TSController implements Initializable{
	
	
	/*
	@FXML
	private TableView<Personnel> mainTable;
	@FXML
	private TableColumn<Personnel, String> nom;
	@FXML
	private TableColumn<Personnel, String> prenom;
	@FXML
	private TableColumn<Personnel, String> cin;
	@FXML
	private TableColumn<Personnel, String> etatCivil;
	@FXML
	private TableColumn<Personnel, String> sexe;
	@FXML
	private TableColumn<Personnel, String> dateNaissance;
	
	ObservableList<Personnel> list_p = FXCollections.observableArrayList();
	
	*/
	/*public ObservableList<Personnel> getdbpersonnellist(){
		
		try {
			
			
			//create jdbc connection and create obj and load class
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Ilovecats2020");
			
			//create statement obj
			Statement s = conn.createStatement();
			
			//execute the query
			ResultSet rs = s.executeQuery("select * from Personnel");
			
			while(rs.next()) {
				list_p.add(new Personnel(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
			}
			
			//close all jdbc obj
			rs.close();
			s.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list_p;
		
	}*/

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		/*
		nom.setCellValueFactory(new PropertyValueFactory<Personnel, String>("nom"));
		prenom.setCellValueFactory(new PropertyValueFactory<Personnel, String>("prenom"));
		cin.setCellValueFactory(new PropertyValueFactory<Personnel, String>("cin"));
		etatCivil.setCellValueFactory(new PropertyValueFactory<Personnel, String>("etatCivil"));
		sexe.setCellValueFactory(new PropertyValueFactory<Personnel, String>("sexe"));
		dateNaissance.setCellValueFactory(new PropertyValueFactory<Personnel, String>("dateNaissance"));
		
		mainTable.setItems(getdbpersonnellist());
		
		
        FilteredList<Personnel> filteredData = new FilteredList<>(list_p,b -> true);
	        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
		        filteredData.setPredicate(perso -> {
		        	if(newValue == null || newValue.isEmpty()) {
		        		return true;
		        	}
		        	String lowerCaseFilter = newValue.toLowerCase();
		        	if (perso.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
						return true; // Filter matches first name.
					}
		        	if (perso.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
						return true; // Filter matches first name.
					}
		        	if (perso.getEtatCivil().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
						return true; // Filter matches first name.
					}
		        	if (perso.getCin().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
						return true; // Filter matches first name.
					}
		        	else 
		        		return false;
		        	});
		        });
        SortedList<Personnel> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(mainTable.comparatorProperty());
		mainTable.setItems(sortedData);
		*/
	}
	
	@FXML
	public void addPersonnel(ActionEvent event) {
		/*
		try {
			FXMLLoader f = new FXMLLoader();
			f.setLocation(getClass().getResource("/addPersonnelBox.fxml"));
			Pane personnelDialogPane = f.load();
			
			PersonnelDialogController pdc = f.getController();
			
			Dialog<ButtonType> d = new Dialog<>();
			d.setDialogPane((DialogPane) personnelDialogPane);
			d.setTitle("add personnel");
			Optional<ButtonType> clickedButton = d.showAndWait();
			
			
			if(clickedButton.get() == ButtonType.APPLY) {
				if(pdc.getPersonnel().getNom().length()>0 && 
				pdc.getPersonnel().getPrenom().length()>0 &&
				pdc.getPersonnel().getCin().length()>0 &&
				pdc.getPersonnel().getEtatCivil().length()>0 &&
				pdc.getPersonnel().getSexe().length()>0 &&
				pdc.getPersonnel().getDateNaissance().length()>0) {
					
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Ilovecats2020");
					Statement s = conn.createStatement(); 
					int rs = s.executeUpdate("INSERT INTO Personnel VALUES('"+pdc.getPersonnel().getNom()+"','"+
							pdc.getPersonnel().getPrenom()+"','"+
							pdc.getPersonnel().getCin()+"','"+
							pdc.getPersonnel().getEtatCivil()+"','"+
							pdc.getPersonnel().getSexe()+"','"+
							pdc.getPersonnel().getDateNaissance()+"')");
					s.close();
					conn.close();

					list_p.add(pdc.getPersonnel());
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	@FXML
	public void updatePersonnel(ActionEvent event) {
		/*
		try {
			FXMLLoader f = new FXMLLoader();
			f.setLocation(getClass().getResource("/addPersonnelBox.fxml"));
			Pane personnelDialogPane = f.load();
			
			PersonnelDialogController pdc = f.getController();
			if(mainTable.getSelectionModel().getSelectedItem() != null) {
				pdc.setPersonnelDialogPane(mainTable.getSelectionModel().getSelectedItem());
				
				Dialog<ButtonType> d = new Dialog<>();
				d.setDialogPane((DialogPane) personnelDialogPane);
				d.setTitle("Update personnel");
				Optional<ButtonType> clickedButton = d.showAndWait();
				
				if(clickedButton.get() == ButtonType.APPLY) {
					if(pdc.getPersonnel().getNom().length()>0 && 
					    pdc.getPersonnel().getPrenom().length()>0 &&
				    	pdc.getPersonnel().getCin().length()>0 &&
					    pdc.getPersonnel().getEtatCivil().length()>0 &&
					    pdc.getPersonnel().getSexe().length()>0 &&
					    pdc.getPersonnel().getDateNaissance().length()>0) {
						
							Class.forName("oracle.jdbc.driver.OracleDriver");
							Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Ilovecats2020");
							Statement s = conn.createStatement(); 
							int rs = s.executeUpdate("UPDATE Personnel SET nom ='"+pdc.getPersonnel().getNom()+"', prenom= '"+
																				   pdc.getPersonnel().getPrenom()+"', cin= '"+
																				   pdc.getPersonnel().getCin()+"', etatcivil= '"+
																				   pdc.getPersonnel().getEtatCivil()+"', sexe= '"+
																				   pdc.getPersonnel().getSexe()+"', datenaissance= '"+
																				   pdc.getPersonnel().getDateNaissance()+"' WHERE cin = '"+
																				   pdc.getPersonnel().getCin()+"'");
							s.close();
							conn.close();
							
							list_p.set(mainTable.getSelectionModel().getSelectedIndex(), pdc.getPersonnel());
					}
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}
	@FXML void deletePersonnel(ActionEvent event) {
		/*
		Personnel selectedP = mainTable.getSelectionModel().getSelectedItem();
		int index = mainTable.getSelectionModel().getSelectedIndex();
		if(index>=0) {
			try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Ilovecats2020");
			Statement s = conn.createStatement(); 
			ResultSet rs = s.executeQuery("DELETE FROM Personnel WHERE cin = '"+selectedP.getCin()+"'");
			System.out.println(rs);
			rs.close();
			s.close();
			conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			list_p.remove(index);
		}*/
	}
	
}
