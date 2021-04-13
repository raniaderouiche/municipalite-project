package org.fsb.municipalite.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import org.fsb.municipalite.entities.Materiel;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.services.impl.MaterielServiceImpl;


import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MaterielPageController implements Initializable{

	@FXML
    TextField searchBox;
    @FXML
    TableView<Materiel> tableView;
    @FXML
    TableColumn<Materiel, Long> Id;
    @FXML
    TableColumn<Materiel, LocalDateTime> Date;
    @FXML
    TableColumn<Materiel, Long> Version;
    @FXML
    TableColumn<Materiel, Materiel.Etat> Status;
    @FXML
    TableColumn<Materiel, String> Name;
    @FXML
    TableColumn<Materiel, Long> Reference;
    @FXML
    TableColumn<Materiel, Projet> Project;
    
    public ObservableList<Materiel> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        Id.setCellValueFactory(new PropertyValueFactory<Materiel,Long>("id"));
        Date.setCellValueFactory(new PropertyValueFactory<Materiel,LocalDateTime>("createdAt"));
        Version.setCellValueFactory(new PropertyValueFactory<Materiel,Long>("version"));
        Status.setCellValueFactory(new PropertyValueFactory<Materiel,Materiel.Etat>("etat"));
        Name.setCellValueFactory(new PropertyValueFactory<Materiel,String>("nom"));
        Reference.setCellValueFactory(new PropertyValueFactory<Materiel,Long>("reference"));
        Project.setCellValueFactory(new PropertyValueFactory<Materiel,Projet>("projet_id"));

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
    	//zidouna haka alert wjaw 
    	// w multiple select
    	if(tableView.getSelectionModel().getSelectedItem() != null) {
    		Materiel m = (Materiel) tableView.getSelectionModel().getSelectedItem();
            MaterielServiceImpl materielService = new MaterielServiceImpl();
            materielService.remove(m.getId());
            monStock(event);
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
				
			}
			
			
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        monStock(event);

    }

    public boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void search(ActionEvent event) {
        if(!(searchBox.getText().isEmpty())){
            tableView.getItems().clear();
            MaterielServiceImpl materielService =new MaterielServiceImpl();
            data  =  FXCollections.observableArrayList();
            if(isNumeric(searchBox.getText())){
                List<Materiel> list = materielService.selectBy("id",searchBox.getText());
                for (Materiel m : list) {
                    data.add(m);
                }
            }else{
                List<Materiel> list = materielService.selectBy("nom","'" + searchBox.getText() + "'");
                for (Materiel m : list) {
                    data.add(m);
                }
            }
            tableView.setItems(data);
            searchBox.clear();
        }
    }

    @FXML
    public void UpdateEvent(ActionEvent event) {
    	try {
	    	FXMLLoader f = new FXMLLoader();
			f.setLocation(getClass().getResource("/interfaces/MaterielPageUpdate.fxml"));
			Pane materielDialogPane = f.load();
			MaterielUpdateController muc = f.getController();
	    	
			if(tableView.getSelectionModel().getSelectedItem() != null) {
				MaterielServiceImpl materielService = new MaterielServiceImpl();
				Materiel m = (Materiel) tableView.getSelectionModel().getSelectedItem();
		        Materiel test = materielService.getById(m.getId());

				System.out.println(m.getId()+m.getNom()+m.getReference()+m.getProjet()+m.getEtat());
				muc.setMaterielDialogPane(test);
				Dialog<ButtonType> d = new Dialog<>();
				d.setDialogPane((DialogPane) materielDialogPane);
				d.setTitle("Update materiel");
				Optional<ButtonType> clickedButton = d.showAndWait();
				if(clickedButton.get() == ButtonType.APPLY) {
						
						muc.getCurrentMateriel(test);
				        materielService.update(test);
				        monStock(event);
				}
			}
		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

}
