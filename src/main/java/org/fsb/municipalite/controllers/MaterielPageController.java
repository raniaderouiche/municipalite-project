package org.fsb.municipalite.controllers;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.fsb.municipalite.entities.Materiel;
import org.fsb.municipalite.services.impl.MaterielServiceImpl;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
    TableColumn<Materiel, String> Reference;
    //@FXML
  //  TableColumn<Materiel, Projet> Project;
    
    public ObservableList<Materiel> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Id.setCellValueFactory(new PropertyValueFactory<Materiel,Long>("id"));
        Date.setCellValueFactory(new PropertyValueFactory<Materiel,LocalDateTime>("createdAt"));
        Version.setCellValueFactory(new PropertyValueFactory<Materiel,Long>("version"));
        Status.setCellValueFactory(new PropertyValueFactory<Materiel,Materiel.Etat>("etat"));
        Name.setCellValueFactory(new PropertyValueFactory<Materiel,String>("nom"));
        Reference.setCellValueFactory(new PropertyValueFactory<Materiel,String>("reference"));
        //Project.setCellValueFactory(new PropertyValueFactory<Materiel,Projet>("projet_id"));

        MaterielServiceImpl materielService =new MaterielServiceImpl();
        List<Materiel> list = materielService.selectAll();
        data  =  FXCollections.observableArrayList();
        for (Materiel m : list) {
            data.addAll(m);
        }
        tableView.setItems(data);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            ListenerSearch(newValue);
        });
  
    }

    public void ListenerSearch(String n){
        MaterielServiceImpl materielService = new MaterielServiceImpl();
        data = FXCollections.observableArrayList();
        List<Materiel> list;
        if(!(n.equals(""))) {
            if (isNumeric(n)){
                list = materielService.selectLike("id", n ); //rigel il % kbal ma nbi3ouha
                for (Materiel m : list) {
                    data.add(m);
                }
            }else{
                list = materielService.selectLike("nom", "'" + n + "%'");
                for (Materiel m : list) {
                    data.add(m);
                }
            }
            tableView.setItems(data);
        } else {
            list = materielService.selectAll();
            for (Materiel m : list) {
                data.add(m);
            }
            tableView.setItems(data);
        }

    }
		

    @FXML
    public void onClickEventRemove(ActionEvent event) {
    	if(tableView.getSelectionModel().getSelectedItem() != null) {
            ObservableList<Materiel> selectedItems = tableView.getSelectionModel().getSelectedItems();
            for (Materiel m : selectedItems){
                MaterielServiceImpl materielService = new MaterielServiceImpl();
                materielService.remove(m.getId());
            }
            monStock(event);
    	}
    	
      
    }

    //refresh
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

    //Add tools
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

			//name field listener
			mac.name.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!isAlpha(newValue)) {
                    mac.inv_name.setVisible(true);
                }else
                    mac.inv_name.setVisible(false);
            });

			//ref field listener
			mac.ref.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!Pattern.matches("[a-zA-Z0-9]+", newValue)) {
                    mac.inv_ref.setVisible(true);
                }else
                    mac.inv_ref.setVisible(false);
            });

			//apply button binder
            d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                    mac.name.getText().isEmpty()
                            || mac.ref.getText().isEmpty()
                            ||!isAlpha(mac.name.getText())
                            ||!Pattern.matches("[a-zA-Z0-9]+", mac.ref.getText()),
			                mac.name.textProperty(),mac.ref.textProperty()));
			
			Optional<ButtonType> clickedButton = d.showAndWait();
			if(clickedButton.get() == ButtonType.APPLY) {
			    Materiel mat = new Materiel();
			    mat.setNom(mac.name.getText());
			    mat.setReference(mac.ref.getText());
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
				
			}
			
			
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        monStock(event);

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

                //name field listener
                muc.nameField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if(!isAlpha(newValue)) {
                        muc.inv_name.setVisible(true);
                    }else
                        muc.inv_name.setVisible(false);
                });

                //ref field listener
                muc.refField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if(!Pattern.matches("[a-zA-Z0-9]+", newValue)) {
                        muc.inv_ref.setVisible(true);
                    }else
                       muc.inv_ref.setVisible(false);
                });

                //apply button binder
                d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                                muc.nameField.getText().isEmpty()
                                        || muc.refField.getText().isEmpty()
                                        ||!isAlpha(muc.nameField.getText())
                                        ||!Pattern.matches("[a-zA-Z0-9]+", muc.refField.getText()),
                                        muc.nameField.textProperty(),muc.refField.textProperty()));

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

    //Test if String is numeric
    public boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    //Test if String is alphabetical
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

}
