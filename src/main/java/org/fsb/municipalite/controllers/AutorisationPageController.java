package org.fsb.municipalite.controllers;
import javafx.beans.binding.Bindings;   
import javafx.collections.FXCollections;		
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.fsb.municipalite.entities.Autorisation;
import org.fsb.municipalite.services.impl.AutorisationServiceImpl;


import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AutorisationPageController implements Initializable{

	@FXML
    TextField searchBox;
    @FXML
    TableView<Autorisation> tableView;
    @FXML
    TableColumn<Autorisation, Long> Id;
    @FXML
    TableColumn<Autorisation, LocalDateTime> Date;
    @FXML
    TableColumn<Autorisation, Autorisation.Etat> Status;
    @FXML
    TableColumn<Autorisation, String> Name;
    @FXML
    TableColumn<Autorisation, String> Subject;
    @FXML
    TableColumn<Autorisation, Long> Cin; 
    
    public ObservableList<Autorisation> data;
    private double xOffset = 0;
    private double yOffset = 0;

   @Override
   public void initialize(URL location, ResourceBundle resources) {
	   tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	   Id.setCellValueFactory(new PropertyValueFactory<Autorisation,Long>("id"));
	   Date.setCellValueFactory(new PropertyValueFactory<Autorisation,LocalDateTime>("createdAt"));
        Status.setCellValueFactory(new PropertyValueFactory<Autorisation,Autorisation.Etat>("etat"));
        Name.setCellValueFactory(new PropertyValueFactory<Autorisation,String>("nomCitoyen"));
        Cin.setCellValueFactory(new PropertyValueFactory<Autorisation,Long>("cin"));
        Subject.setCellValueFactory(new PropertyValueFactory<Autorisation, String>("sujet"));

        AutorisationServiceImpl autorisationService =new AutorisationServiceImpl();
        List<Autorisation> list = autorisationService.selectAll();
        data  =  FXCollections.observableArrayList();
        for (Autorisation c : list) {
        	data.addAll(c);
        }
        tableView.setItems(data);
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
        	ListenerSearch(newValue);
        });
        
        

   }

    public void ListenerSearch(String n){
    	AutorisationServiceImpl autorisationService =new AutorisationServiceImpl();
        data = FXCollections.observableArrayList();
        List<Autorisation> list;
        if (!(n.equals(""))) {
            if (isNumeric(n)) {
                list = autorisationService.selectLike("id", n );
                for (Autorisation p : list) {
                    data.add(p);
                }
            } else {
                list =autorisationService.selectLike("nomCitoyen", "'" + n + "%'");
                for (Autorisation p : list) {
                    data.add(p);
                }
            }
            tableView.setItems(data);
        } else {
            list = autorisationService.selectAll();
            for (Autorisation p : list) {
                data.add(p);
            }
            tableView.setItems(data);
        }

    }
		
    @FXML
    public void monStock(ActionEvent event){
        tableView.getItems().clear();
        AutorisationServiceImpl autorisationService =new AutorisationServiceImpl();
        List<Autorisation> list = autorisationService.selectAll();
        data  =  FXCollections.observableArrayList();
        for (Autorisation m : list) {
            data.add(m);
        }
        tableView.setItems(data);
    }

    public void onClickEventAdd(ActionEvent event) {

    	try {
    		FXMLLoader f = new FXMLLoader();
    		f.setLocation(getClass().getResource("/interfaces/AutorisationAddPage.fxml"));
    		Pane compDialogPane = f.load();
    		AutorisationDialogController edc = f.getController();

    		Dialog<ButtonType> d = new Dialog<>();
    		//this is just for adding an icon to the dialog pane
    		Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
    		stage.getIcons().add(new Image("/assets/img/icon.png"));
    		
    		d.setDialogPane((DialogPane) compDialogPane);
    		d.setTitle("Add Authorization");
    		d.setResizable(false);
    		d.initStyle(StageStyle.UNDECORATED);

    		//these two are for moving the window with the mouse
    		compDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
    			@Override
    			public void handle(MouseEvent event) {
    				xOffset = event.getSceneX();
    				yOffset = event.getSceneY();
    			}
    		});
    		compDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
    			@Override
    			public void handle(MouseEvent event) {
    				d.setX(event.getScreenX() - xOffset);
    				d.setY(event.getScreenY() - yOffset);
    			}
    		});

    		//name field listener
    		edc.name.textProperty().addListener((observable, oldValue, newValue) -> {
    			if(!isAlpha(newValue)) {
    				edc.labName.setVisible(true);
    			}else
    				edc.labName.setVisible(false);
    		});

    		//cin field listener
    		edc.cin.textProperty().addListener((observable, oldValue, newValue) -> {
    			if(isNumeric(newValue) && newValue.length() == 8) {
    				edc.labCin.setVisible(false);
    			}else
    				edc.labCin.setVisible(true);
    		});

    		//subject listener
    		edc.subject.textProperty().addListener((observable, oldValue, newValue) -> {
    			if(!isAlpha(newValue)) {
    				edc.labSubject.setVisible(true);
    			}else
    				edc.labSubject.setVisible(false);
    		});
    		edc.msg.textProperty().addListener((observable, oldValue, newValue) -> {
				if(!isAlpha(newValue)) {
					edc.labMsg.setVisible(true);
				}else
					edc.labMsg.setVisible(false);
			});
    		d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
    		d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");

    		
    		d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() -> 
    		edc.name.getText().isEmpty() || edc.cin.getText().isEmpty()||
    		edc.cin.getText().length() != 8||!isNumeric(edc.cin.getText())||
    		edc.subject.getText().length() >50||edc.msg.getText().isEmpty()||
    		edc.msg.getText().length()>3000 || edc.subject.getText().isEmpty()||
    		!isAlpha(edc.name.getText()),
    		edc.name.textProperty(),edc.cin.textProperty(),edc.subject.textProperty(),
    		edc.msg.textProperty()));

    		Optional<ButtonType> clickedButton = d.showAndWait();

    		//new Autorisation creation and addition
    		if (clickedButton.get() == ButtonType.APPLY) {
    			Autorisation c = new Autorisation();
    			c.setNomCitoyen(edc.name.getText());
    			c.setCin(Long.parseLong(edc.cin.getText()));
    			c.setSujet(edc.subject.getText());
    			c.setMsg(edc.msg.getText());
    			if (edc.processed.isSelected()) {
    				c.setEtat(Autorisation.Etat.processed);
    			}
    			if (edc.unprocessed.isSelected()) {
    				c.setEtat(Autorisation.Etat.unprocessed);
    			}

    			AutorisationServiceImpl autorisationService = new AutorisationServiceImpl();
    			autorisationService.create(c);
    			System.out.println("Authorization ADDED !");
    			monStock(event);
    		}

    	} catch (Exception e) {
    		e.printStackTrace();	
    	}
    }
    
    @FXML
    public void UpdateAutorisation(ActionEvent event) {
    	try {
	    	FXMLLoader f = new FXMLLoader();
	    	f.setLocation(getClass().getResource("/interfaces/AutorisationAddPage.fxml"));
			Pane autorisationDialogPane = f.load();
			AutorisationDialogController edc = f.getController();
	    	
			if(tableView.getSelectionModel().getSelectedItem() != null) {
				AutorisationServiceImpl autorisationService = new AutorisationServiceImpl();
				Autorisation c = (Autorisation) tableView.getSelectionModel().getSelectedItem();
				Autorisation test = autorisationService.getById(c.getId());

				edc.setAutorisationDialogPane(test);
				Dialog<ButtonType> d = new Dialog<>();
				
				Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image("/assets/img/icon.png"));
				
				d.setDialogPane((DialogPane) autorisationDialogPane);
				d.setTitle("Update Authorization");
				d.setResizable(false);
				d.initStyle(StageStyle.UNDECORATED);
				
				autorisationDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			           @Override
			           public void handle(MouseEvent event) {
			               xOffset = event.getSceneX();
			               yOffset = event.getSceneY();
			           }
					});
				autorisationDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			           @Override
			           public void handle(MouseEvent event) {
			               d.setX(event.getScreenX() - xOffset);
			               d.setY(event.getScreenY() - yOffset);
			           }
		         });
				edc.name.textProperty().addListener((observable, oldValue, newValue) -> {
					if(!isAlpha(newValue)) {
						edc.labName.setVisible(true);
					}else
						edc.labName.setVisible(false);
				});

				//cin field listener
				edc.cin.textProperty().addListener((observable, oldValue, newValue) -> {
					System.out.println("textfield changed from " + oldValue + " to " + newValue);
					if(isNumeric(newValue) && newValue.length() == 8) {
						edc.labCin.setVisible(false);
					}else
						edc.labCin.setVisible(true);
				});

				//subject listener
				edc.subject.textProperty().addListener((observable, oldValue, newValue) -> {
					if(!isAlpha(newValue)) {
						edc.labSubject.setVisible(true);
					}else
						edc.labSubject.setVisible(false);
				});
				
				//message Listener
				edc.msg.textProperty().addListener((observable, oldValue, newValue) -> {
					if(!isAlpha(newValue)) {
						edc.labMsg.setVisible(true);
					}else
						edc.labMsg.setVisible(false);
				});


				d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
				d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");
				
				//make name field first to be selected

				//apply button binder
				d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() -> 
												edc.name.getText().isEmpty() || edc.cin.getText().isEmpty()||
												edc.cin.getText().length() != 8||!isNumeric(edc.cin.getText())||
												edc.subject.getText().length() >50||edc.msg.getText().isEmpty()||
												edc.msg.getText().length()>3000 || edc.subject.getText().isEmpty()||
												!isAlpha(edc.name.getText()),
												edc.name.textProperty(),edc.cin.textProperty(),edc.subject.textProperty(),
												edc.msg.textProperty()));

				
				Optional<ButtonType> clickedButton = d.showAndWait();
				if(clickedButton.get() == ButtonType.APPLY) {
						
						edc.getCurrentAutorisation(test);
						System.out.println(test);
						autorisationService.update(test);
				        monStock(event);
				}
			}
		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
  
    public void onClickEventRemove(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
        	
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/assets/img/icon.png"));
            alert.setTitle("Delete Authorization ?");
			alert.setHeaderText(null);
            alert.setContentText("Are you Sure You Want to Delete Selected Authorization(s) ?");
            Optional <ButtonType> action = alert.showAndWait();
            if(action.get() == ButtonType.OK) {
                ObservableList<Autorisation> selectedAutorisationList = tableView.getSelectionModel().getSelectedItems();
                for(Autorisation c : selectedAutorisationList) {
                	AutorisationServiceImpl cService = new AutorisationServiceImpl();
                	cService.remove(c.getId());
                }
                monStock(event);
            }
        }
    }
    
    
    @FXML
  	void selectAll(ActionEvent event) {
  		this.tableView.getSelectionModel().selectAll();
  	}
    

    public boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public boolean isAlpha(String name) {
	    return name.matches("[a-zA-Z ]+");
	}
      
}



