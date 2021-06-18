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
import java.time.LocalDate;
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
    TableColumn<Materiel, String> Project;
    @FXML
    TableColumn<Materiel, LocalDate> start;
    @FXML
    TableColumn<Materiel,LocalDate> end;

    
    
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
        start.setCellValueFactory(new PropertyValueFactory<Materiel,LocalDate>("startDate"));
        end.setCellValueFactory(new PropertyValueFactory<Materiel,LocalDate>("endDate"));
        Name.setCellValueFactory(new PropertyValueFactory<Materiel,String>("nom"));
        Reference.setCellValueFactory(new PropertyValueFactory<Materiel,String>("reference"));
        Project.setCellValueFactory(new PropertyValueFactory<Materiel,String>("ProjetValue"));

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
                if(!Pattern.matches("[a-zA-Z ]+", newValue)) {
                    mac.inv_name.setVisible(true);
                }else
                    mac.inv_name.setVisible(false);
            }); 

			//ref field listener
			mac.refField.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!Pattern.matches("[-a-zA-Z0-9]+", newValue)) {
                    mac.inv_ref.setVisible(true);
                }else
                    mac.inv_ref.setVisible(false);
            });

			mac.projectsChoice.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != "No Project Selected") {
                    mac.orderRB.setDisable(true);
                    mac.availableRB.setDisable(true);
                    mac.UnavailableRB.setSelected(true);
                    mac.startDate.setDisable(false);
                    mac.endDate.setDisable(false);

                }else {
                    mac.availableRB.setSelected(true);
                    mac.availableRB.setDisable(false);
                    mac.orderRB.setDisable(false);
                    mac.startDate.setDisable(true);
                    mac.endDate.setDisable(true);
                    mac.startDate.getEditor().clear();
                    mac.endDate.getEditor().clear();
                }
            });

            mac.startDate.valueProperty().addListener((observable, oldValue, newValue) -> {
                try{
                    if(newValue != null) {
                        if(newValue.isAfter(mac.endDate.getValue())) {
                            mac.inv_date2.setVisible(true);
                            mac.inv_date1.setVisible(false);
                        }
                        else if(mac.endDate.getValue().isBefore(LocalDate.now())){
                            mac.inv_date1.setVisible(true);
                            mac.inv_date2.setVisible(false);
                        }
                        else {
                            mac.inv_date2.setVisible(false);
                            mac.inv_date1.setVisible(false);
                        }
                    }
                }catch(Exception e){
                    System.out.println("start date picked");
                }
            });

            mac.endDate.valueProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    if(newValue != null) {
                        if(newValue.isBefore(mac.startDate.getValue())) {
                            mac.inv_date2.setVisible(true);
                            mac.inv_date1.setVisible(false);
                        }
                        else if(newValue.isBefore(LocalDate.now())){
                            mac.inv_date1.setVisible(true);
                            mac.inv_date2.setVisible(false);
                        }
                        else {
                            mac.inv_date1.setVisible(false);
                            mac.inv_date2.setVisible(false);
                        }
                    }
                }catch(Exception e) {
                    System.out.println("end date picked");
                }

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
                            || !mac.refField.getText().matches("[a-zA-Z0-9]+")
                            ||!isAlpha(mac.nameField.getText())
                            ||mac.startDate.getValue() == null
                            ||mac.endDate.getValue() == null
                            ||(mac.startDate.getValue().isAfter(mac.endDate.getValue()))
                            ||(mac.endDate.getValue().isBefore(LocalDate.now()))
                            ||!Pattern.matches("[a-zA-Z0-9]+", mac.refField.getText()),
			                mac.nameField.textProperty(),mac.refField.textProperty(),
                            mac.startDate.valueProperty(),mac.endDate.valueProperty()));
			
			Optional<ButtonType> clickedButton = d.showAndWait();
			if(clickedButton.get() == ButtonType.APPLY) {

			    Materiel mat = new Materiel();
                mac.setCurrentMateriel(mat);
			    MaterielServiceImpl materielService = new MaterielServiceImpl();
			    materielService.create(mat);
		        monStock(event);
			}
			
        } catch (Exception e) {
            e.printStackTrace();
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
				d.setTitle("Update Item " + test.getId());
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
                    if(!Pattern.matches("[a-zA-Z ]+", newValue)) {
                        muc.inv_name.setVisible(true);
                    }else
                        muc.inv_name.setVisible(false);
                });

                //ref field listener
                muc.refField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if(!Pattern.matches("[-a-zA-Z0-9]+", newValue)) {
                        muc.inv_ref.setVisible(true);
                    }else
                        muc.inv_ref.setVisible(false);
                });

                muc.startDate.valueProperty().addListener((observable, oldValue, newValue) -> {
                    try{
                        if(newValue != null) {
                            if(newValue.isAfter(muc.endDate.getValue())) {
                                muc.inv_date2.setVisible(true);
                                muc.inv_date1.setVisible(false);
                            }
                            else if(muc.endDate.getValue().isBefore(LocalDate.now())){
                                muc.inv_date1.setVisible(true);
                                muc.inv_date2.setVisible(false);
                            }
                            else {
                                muc.inv_date2.setVisible(false);
                                muc.inv_date1.setVisible(false);
                            }
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                });

                muc.endDate.valueProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        if(newValue != null) {
                            if(newValue.isBefore(muc.startDate.getValue())) {
                                muc.inv_date2.setVisible(true);
                                muc.inv_date1.setVisible(false);
                            }
                            else if(newValue.isBefore(LocalDate.now())){
                                muc.inv_date1.setVisible(true);
                                muc.inv_date2.setVisible(false);
                            }
                            else {
                                muc.inv_date1.setVisible(false);
                                muc.inv_date2.setVisible(false);
                            }
                        }
                    }catch(Exception e) {
                        System.out.println("end date picked");
                    }

                });

                muc.projectsChoice.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != "No Project Selected") {
                        muc.orderRB.setDisable(true);
                        muc.availableRB.setDisable(true);
                        muc.UnavailableRB.setSelected(true);
                        muc.startDate.setDisable(false);
                        muc.endDate.setDisable(false);

                    }else {
                        muc.availableRB.setSelected(true);
                        muc.availableRB.setDisable(false);
                        muc.orderRB.setDisable(false);
                        muc.startDate.setDisable(true);
                        muc.endDate.setDisable(true);
                        muc.startDate.getEditor().clear();
                        muc.endDate.getEditor().clear();
                    }
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
                                        || !muc.refField.getText().matches("[a-zA-Z0-9]+")
                                        ||!isAlpha(muc.nameField.getText())
                                        ||(muc.startDate.getValue() == null&& muc.projectsChoice.getValue() != "No Project Selected")
                                        ||(muc.endDate.getValue() == null&& muc.projectsChoice.getValue() != "No Project Selected")
                                        ||muc.startDate.getValue().isAfter(muc.endDate.getValue())
                                        ||muc.endDate.getValue().isBefore(LocalDate.now())
                                        ||!Pattern.matches("[a-zA-Z0-9]+", muc.refField.getText()),
                                        muc.nameField.textProperty(),
                                        muc.refField.textProperty(),
                                        muc.startDate.valueProperty(),
                                        muc.endDate.valueProperty()
                                        ));

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

        return name.matches("[a-zA-Z ]+");
    }
    @FXML
	void selectAll(ActionEvent event) {
		this.tableView.getSelectionModel().selectAll();
	}

}
