package org.fsb.municipalite.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.fsb.municipalite.entities.Materiel;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.services.impl.MaterielServiceImpl;
import org.fsb.municipalite.services.impl.ProjetServiceImpl;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MaterielPageController implements Initializable{

    @FXML
    RadioButton avail;
    @FXML 
    RadioButton unavail;
    @FXML 
    RadioButton outoford;
    @FXML 
    TextField idField;
    @FXML 
    TextField nameField;
    @FXML 
    TextField refField;
    @FXML 
    TextField projField;
    
    @FXML
    TableView tableView;
    @FXML
    TableColumn Id = new TableColumn("ID");
    @FXML
    TableColumn Date = new TableColumn("Date");
    @FXML
    TableColumn Version = new TableColumn("Version");
    @FXML
    TableColumn Status = new TableColumn("Status");
    @FXML
    TableColumn Name = new TableColumn("Name");
    @FXML
    TableColumn Reference = new TableColumn("Reference");
    @FXML
    TableColumn Projet = new TableColumn("Project ID");
    public ObservableList<Materiel> data;
    @FXML
    TextField searchBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().addAll(Id,Date,Version,Status,Name,Reference,Projet);
        Id.setCellValueFactory(new PropertyValueFactory<Materiel,Long>("id"));
        Date.setCellValueFactory(new PropertyValueFactory<Materiel,LocalDateTime>("createdAt"));
        Version.setCellValueFactory(new PropertyValueFactory<Materiel,Long>("version"));
        Status.setCellValueFactory(new PropertyValueFactory<Materiel,Materiel.Etat>("etat"));
        Name.setCellValueFactory(new PropertyValueFactory<Materiel,String>("nom"));
        Reference.setCellValueFactory(new PropertyValueFactory<Materiel,Long>("reference"));
        Projet.setCellValueFactory(new PropertyValueFactory<Materiel,Projet>("projet_id"));

        MaterielServiceImpl materielService =new MaterielServiceImpl();
        List<Materiel> list = materielService.selectAll();
        data  =  FXCollections.observableArrayList();
        for (Materiel m : list) {
            data.addAll(m);
        }
        tableView.setItems(data);

    }

    @FXML
    public void onClickEventRemove(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/interfaces/MaterielPageDelete.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //refraiche
    @FXML
    public void monStock(ActionEvent event){
        tableView.getItems().clear();
        MaterielServiceImpl materielService =new MaterielServiceImpl();
        List<Materiel> list = materielService.selectAll();
        data  =  FXCollections.observableArrayList();
        for (Materiel m : list) {
            data.add(m);
        }
        tableView.setItems(data);
    }

    @FXML
    public void onClickEventAdd(ActionEvent event) {
        try {
        	FXMLLoader f = new FXMLLoader();
        	f.setLocation(getClass().getResource("/interfaces/MaterielAddPage.fxml"));
			Pane materielDialogPane = f.load();
			MaterielAddContoller mac = f.getController();
			
			Dialog<ButtonType> d = new Dialog<>();
			d.setDialogPane((DialogPane) materielDialogPane);
			d.setTitle("add materiel");
			Optional<ButtonType> clickedButton = d.showAndWait();
			if(clickedButton.get() == ButtonType.APPLY) {
				if(mac.name.getText().length()>0 && mac.ref.getText().length()>0 ) {
					Materiel mat = new Materiel();
					mat.setNom(mac.name.getText());
			        mat.setReference(Long.parseLong(mac.ref.getText()));
			        if (mac.avail.isSelected()) {
			            mat.setEtat(Materiel.Etat.disponible);
			        }
			        if (mac.unavail.isSelected()) {
			            mat.setEtat(Materiel.Etat.occupe);
			        }
			        if (mac.outoford.isSelected()) {
			            mat.setEtat(Materiel.Etat.enPanne);
			        }
			        MaterielServiceImpl materielService = new MaterielServiceImpl();
			        materielService.create(mat);
			        mac.msg.setText("ITEM ADDED !");
				}
				monStock(event);
			}
			
			
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    public void onClickEventLogout(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/interfaces/Login.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    public void idSearch(ActionEvent event){
        tableView.getItems().clear();
        MaterielServiceImpl materielService =new MaterielServiceImpl();
        List<Materiel> list = materielService.selectBy("id",searchBox.getText());
        data  =  FXCollections.observableArrayList();
        for (Materiel m : list) {
            data.add(m);
        }
        tableView.setItems(data);
        searchBox.clear();

    }

    @FXML
    public void nameSearch(ActionEvent event) {
        tableView.getItems().clear();
        MaterielServiceImpl materielService =new MaterielServiceImpl();
        List<Materiel> list = materielService.selectBy("nom","'" + searchBox.getText() + "'");
        data  =  FXCollections.observableArrayList();
        for (Materiel m : list) {
            data.add(m);
        }
        tableView.setItems(data);
        searchBox.clear();
    }

    @FXML
    public void refSearch(ActionEvent event) {
        tableView.getItems().clear();
        MaterielServiceImpl materielService =new MaterielServiceImpl();
        List<Materiel> list = materielService.selectBy("reference",searchBox.getText());
        data  =  FXCollections.observableArrayList();
        for (Materiel m : list) {
            data.add(m);
        }
        tableView.setItems(data);
        searchBox.clear();
    }

    
    @FXML
    public void updateItem(ActionEvent event){
        Materiel materiel;
        MaterielServiceImpl materielService = new MaterielServiceImpl();
        materiel = materielService.getById(Long.parseLong(idField.getText()));
        System.out.println(materiel.getId());
        if ((nameField.getText().length()) != 0){
            materiel.setNom(nameField.getText());
        }
        if ((refField.getText().length() != 0)) {
            materiel.setReference(Long.parseLong(refField.getText()));
        }
        if (projField.getText().length() != 0){
            ProjetServiceImpl projetService = new ProjetServiceImpl();
            materiel.setProjet(projetService.getById(Long.parseLong(projField.getText())));
        }
        if (avail.isSelected()) {
            materiel.setEtat(Materiel.Etat.disponible);
        }
        if (unavail.isSelected()) {
            materiel.setEtat(Materiel.Etat.occupe);
        }
        if (outoford.isSelected()) {
            materiel.setEtat(Materiel.Etat.enPanne);
        }
        materielService.update(materiel);
        idField.clear();
        nameField.clear();
        refField.clear();
        projField.clear();
    }


}
