package org.fsb.municipalite.controllers;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.fsb.municipalite.entities.Materiel;
import org.fsb.municipalite.services.impl.MaterielServiceImpl;
import java.net.URL;
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
    TableColumn<Materiel, String> Date;
    @FXML
    TableColumn<Materiel, Long> Version;
    @FXML
    TableColumn<Materiel, Materiel.Etat> Status;
    @FXML
    TableColumn<Materiel, String> Name;
    @FXML
    TableColumn<Materiel, String> Reference;
    @FXML
    TableColumn<Materiel, Long> Project;
    
    
    //define dialog window offsets here
    private double xOffset = 0;
    private double yOffset = 0;
    
    public ObservableList<Materiel> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Id.setCellValueFactory(new PropertyValueFactory<Materiel,Long>("id"));
        Date.setCellValueFactory(new PropertyValueFactory<Materiel,String>("CreatedAtValue"));
        Version.setCellValueFactory(new PropertyValueFactory<Materiel,Long>("version"));
        Status.setCellValueFactory(new PropertyValueFactory<Materiel,Materiel.Etat>("etat"));
        Name.setCellValueFactory(new PropertyValueFactory<Materiel,String>("nom"));
        Reference.setCellValueFactory(new PropertyValueFactory<Materiel,String>("reference"));
        Project.setCellValueFactory(new PropertyValueFactory<Materiel,Long>("ProjetValue"));

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
		
    //add try catch
    @FXML
    public void onClickEventRemove(ActionEvent event) {
    	if(tableView.getSelectionModel().getSelectedItem() != null) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			//this is just for adding an icon to the dialog pane
			stage.getIcons().add(new Image("/assets/img/icon.png"));
			alert.setTitle("Delete Materiel?");
			alert.setHeaderText(null);
			alert.setContentText("Are you Sure You Want to Delete Selected Item(s) ?");
			Optional <ButtonType> action = alert.showAndWait();
			if(action.get() == ButtonType.OK) {
	            ObservableList<Materiel> selectedItems = tableView.getSelectionModel().getSelectedItems();
	            for (Materiel m : selectedItems){
	                MaterielServiceImpl materielService = new MaterielServiceImpl();
	                materielService.remove(m.getId());
	            }
	            monStock(event);

	        }
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
        	f.setLocation(getClass().getResource("/interfaces/MaterielDialogPage.fxml"));
			Pane materielDialogPane = f.load();
			MaterielDialogController mac = f.getController();
			Dialog<ButtonType> d = new Dialog<>();
			//this is just for adding an icon to the dialog pane
			Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/img/icon.png"));
			d.setDialogPane((DialogPane) materielDialogPane);
			d.setTitle("Add Materiel Item");
			d.setResizable(false);
			d.initStyle(StageStyle.UNDECORATED);
		
			//these two are for moving the window with the mouse
			materielDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	               xOffset = event.getSceneX();
	               yOffset = event.getSceneY();
	           }
			});
			materielDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	               d.setX(event.getScreenX() - xOffset);
	               d.setY(event.getScreenY() - yOffset);
	           }
            });
			
			//name field listener
			mac.nameField.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!isAlpha(newValue)) {
                    mac.inv_name.setVisible(true);
                }else
                    mac.inv_name.setVisible(false);
            }); 

			//ref field listener
			mac.refField.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!Pattern.matches("[a-zA-Z0-9]+", newValue)) {
                    mac.inv_ref.setVisible(true);
                }else
                    mac.inv_ref.setVisible(false);
            });


			//to apply css on the dialog pane buttons
			d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
			d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");
			
			//make name field first to be selected
			mac.nameField.requestFocus(); 
			
			//apply button binder
            d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                    mac.nameField.getText().isEmpty()
                            || mac.refField.getText().isEmpty()
                            ||!isAlpha(mac.nameField.getText())
                            ||!Pattern.matches("[a-zA-Z0-9]+", mac.refField.getText()),
                            
			                mac.nameField.textProperty(),mac.refField.textProperty()));
			
			Optional<ButtonType> clickedButton = d.showAndWait();
			if(clickedButton.get() == ButtonType.APPLY) {

			    Materiel mat = new Materiel();
			    /*
			    mat.setNom(mac.nameField.getText());
			    mat.setReference(mac.refField.getText());
			    if (mac.availableRB.isSelected()) {
			        mat.setEtat(Materiel.Etat.disponible);
			    }
			    if (mac.UnavailableRB.isSelected()) {
			        mat.setEtat(Materiel.Etat.occupe);
			    }
			    if (mac.orderRB.isSelected()) {
			        mat.setEtat(Materiel.Etat.enPanne);
			    }*/

                mac.setCurrentMateriel(mat);

			    MaterielServiceImpl materielService = new MaterielServiceImpl();
			    materielService.create(mat);
		        monStock(event);

				
			}
			
			
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    @FXML
    public void UpdateEvent(ActionEvent event) {
    	try {
	    	FXMLLoader f = new FXMLLoader();
			f.setLocation(getClass().getResource("/interfaces/MaterielDialogPage.fxml"));
			Pane materielDialogPane = f.load();
			MaterielDialogController muc = f.getController();
			muc.titleLabel.setText("Update Item");
			if(tableView.getSelectionModel().getSelectedItem() != null) {
				MaterielServiceImpl materielService = new MaterielServiceImpl();
				Materiel m = (Materiel) tableView.getSelectionModel().getSelectedItem();
		        Materiel test = materielService.getById(m.getId());
				muc.setMaterielDialogPane(test);
                Dialog<ButtonType> d = new Dialog<>();
				//this is just for adding an icon to the dialog pane
				Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image("/assets/img/icon.png"));
				d.setDialogPane((DialogPane) materielDialogPane);
				d.setTitle("Update Materiel Item");
				d.setResizable(false);
				d.initStyle(StageStyle.UNDECORATED);
				d.setDialogPane((DialogPane) materielDialogPane);
				d.setTitle("Update materiel");


				//these two are for moving the window with the mouse
				materielDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
		           @Override
		           public void handle(MouseEvent event) {
		               xOffset = event.getSceneX();
		               yOffset = event.getSceneY();
		           }
				});
				materielDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
		           @Override
		           public void handle(MouseEvent event) {
		               d.setX(event.getScreenX() - xOffset);
		               d.setY(event.getScreenY() - yOffset);
		           }
	            });
				
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

                //to apply css on the dialog pane buttons
    			d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
    			d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");
    			
    			//make name field first to be selected
    			muc.nameField.requestFocus();
    			
                //apply button binder
                d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                                muc.nameField.getText().isEmpty()
                                        || muc.refField.getText().isEmpty()
                                        ||!isAlpha(muc.nameField.getText())
                                        ||!Pattern.matches("[a-zA-Z0-9]+", muc.refField.getText()),
                                        muc.nameField.textProperty(),muc.refField.textProperty()));

				Optional<ButtonType> clickedButton = d.showAndWait();
				if(clickedButton.get() == ButtonType.APPLY) {
						
						muc.setCurrentMateriel(test);
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
    @FXML
	void selectAll(ActionEvent event) {
		this.tableView.getSelectionModel().selectAll();
	}

}
