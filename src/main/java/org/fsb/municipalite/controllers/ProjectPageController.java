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
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.services.impl.ProjetServiceImpl;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectPageController implements Initializable {

    @FXML TableView<Projet> tableview;
    @FXML TableColumn<Projet,Long> id;
    @FXML TableColumn<Projet,String> name;
    @FXML TableColumn<Projet,Projet.Etat> status;
    @FXML TableColumn<Projet,LocalDate> startDate;
    @FXML TableColumn<Projet,LocalDate> dueDate;
    @FXML TableColumn<Projet,Integer> budget;
    @FXML TableColumn<Projet,Long> version ;
    @FXML TableColumn<Projet,String> place;
    @FXML TableColumn<Projet, Long> teamColumn;

    public ObservableList<Projet> data;
    @FXML
    TextField searchBox;

    //define your offsets here
    private double xOffset = 0;
    private double yOffset = 0;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        id.setCellValueFactory(new PropertyValueFactory<Projet,Long>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Projet,String>("name"));
        status.setCellValueFactory(new PropertyValueFactory<Projet,Projet.Etat>("etat"));
        startDate.setCellValueFactory(new PropertyValueFactory<Projet, LocalDate>("dateDebut"));
        dueDate.setCellValueFactory(new PropertyValueFactory<Projet,LocalDate>("dateFin"));
        budget.setCellValueFactory(new PropertyValueFactory<Projet, Integer>("budget"));
        version.setCellValueFactory(new PropertyValueFactory<Projet,Long>("version"));
        place.setCellValueFactory(new PropertyValueFactory<Projet,String>("lieu"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<Projet,Long>("equipeValue"));
        data  =  FXCollections.observableArrayList();
        ProjetServiceImpl projetService = new ProjetServiceImpl();
        List<Projet> list = projetService.selectAll();

        for(Projet p : list) {
            data.add(p);
        }
        
        tableview.setItems(data);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
            ListenerSearch(newValue);
        });

    }

    public void ListenerSearch(String n){
        ProjetServiceImpl projetService = new ProjetServiceImpl();
        data = FXCollections.observableArrayList();
        List<Projet> list;
        if (!(n.equals(""))) {
            if (isNumeric(n)) {
                list = projetService.selectLike("id", n ); //rigel il % kbal ma nbi3ouha
                for (Projet p : list) {
                    data.add(p);
                }
            } else {
                list =projetService.selectLike("name", "'" + n + "%'");
                for (Projet p : list) {
                    data.add(p);
                }
            }
            tableview.setItems(data);
        } else {
            list = projetService.selectAll();
            for (Projet p : list) {
                data.add(p);
            }
            tableview.setItems(data);
        }

    }

    @FXML
    public void refresh(ActionEvent event) {
        ProjetServiceImpl projetService = new ProjetServiceImpl();
        tableview.getItems().clear();
        List<Projet> list = projetService.selectAll();
        data  =  FXCollections.observableArrayList();
        for(Projet p : list) {
            data.add(p);
        }
        tableview.setItems(data);
    }

    public boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


    @FXML
    public void add(ActionEvent event) {
        try {
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/interfaces/ProjectAdd.fxml"));
            Pane projetDialogPane = f.load();
            ProjectDialogController pac = f.getController();

            Dialog<ButtonType> d = new Dialog<>();
            
            //this is just for adding an icon to the dialog pane
			Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/img/icon.png"));
			
            d.setDialogPane((DialogPane) projetDialogPane);
            d.setTitle("add project");
            d.setResizable(false);
			d.initStyle(StageStyle.UNDECORATED);
			
			//these two are for moving the window with the mouse
			projetDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	               xOffset = event.getSceneX();
	               yOffset = event.getSceneY();
	           }
			});
       
			projetDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	               d.setX(event.getScreenX() - xOffset);
	               d.setY(event.getScreenY() - yOffset);
	           }
            });
            
            //name field listener
            pac.name.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!isAlpha(newValue)) {
                    pac.inv_name.setVisible(true);
                }else
                    pac.inv_name.setVisible(false);
            });

            pac.budget.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!isNumeric(newValue)) {
                    pac.inv_budget.setVisible(true);
                }else
                    pac.inv_budget.setVisible(false);
            });

            pac.start.valueProperty().addListener((observable, oldValue, newValue) -> {
                try{
                    if(newValue != null) {
                        if(newValue.isAfter(pac.end.getValue())) {
                            pac.warning.setVisible(true);
                        }
                        else {
                            pac.warning.setVisible(false);
                        }
                    }
                }catch(Exception e){
                    System.out.println("start date picked");
                }
            });

            pac.end.valueProperty().addListener((observable, oldValue, newValue) -> {
            	try {
            		if(newValue != null) {
                        if(newValue.isBefore(pac.start.getValue())) {
                            pac.warning.setVisible(true);
                        }
                        else {
                            pac.warning.setVisible(false);
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
			pac.name.requestFocus();
            
            d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                            pac.name.getText().isEmpty()
                                    || pac.budget.getText().isEmpty()
                                    ||!isAlpha(pac.name.getText())
                                    ||!isNumeric(pac.budget.getText())
                                    || pac.start.getValue() == null
                                    || pac.end.getValue() == null
                                    || pac.start.getValue().isAfter(pac.end.getValue())
                                    || pac.team.getValue() == null,
                                    pac.name.textProperty(),
                                    pac.budget.textProperty(),
                                    pac.start.valueProperty(),
                                    pac.end.valueProperty(),
            						pac.team.valueProperty()));


            Optional<ButtonType> clickedButton = d.showAndWait();
            if(clickedButton.get() == ButtonType.APPLY) {
               Projet projet = new Projet();
               ProjetServiceImpl projetService = new ProjetServiceImpl();
               pac.setCurrentProject(projet);
               projetService.create(projet);
               refresh(event);

            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void remove(ActionEvent event) {
        if(tableview.getSelectionModel().getSelectedItem() != null) {
        	
        	Alert alert = new Alert(AlertType.CONFIRMATION);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			//this is just for adding an icon to the dialog pane
			stage.getIcons().add(new Image("/assets/img/icon.png"));
			alert.setTitle("Delete Project ?");
			alert.setHeaderText(null);
			alert.setContentText("Are you Sure You Want to Delete Selected Item(s) ?");
			Optional <ButtonType> action = alert.showAndWait();
			if(action.get() == ButtonType.OK) {
	            ObservableList<Projet> selectedItems = tableview.getSelectionModel().getSelectedItems();
	            for (Projet p : selectedItems){
	                try{
                        ProjetServiceImpl projetService = new ProjetServiceImpl();
                        projetService.remove(p.getId());
                    }catch(Exception e){
                        Alert alert_2 = new Alert(AlertType.ERROR);
                        alert_2.setTitle("Error!");
                        alert_2.setHeaderText(null);
                        alert_2.setContentText("Cannot delete project while refreneced in a the tools table!");
                        alert_2.show();
                    }

	            }
	            refresh(event);
			}
        }
        
    }

    @FXML
    public void update(ActionEvent event) {
        try {
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/interfaces/ProjectAdd.fxml"));
            Pane projectDialogPane = f.load();
            ProjectDialogController puc = f.getController();

            if(tableview.getSelectionModel().getSelectedItem() != null) {

                ProjetServiceImpl projetService = new ProjetServiceImpl();
                Projet m = (Projet) tableview.getSelectionModel().getSelectedItem();
                Projet projet = projetService.getById(m.getId());

                puc.setProjectDialogPane(projet);
                puc.titleLabel.setText("Update Project");
                Dialog<ButtonType> d = new Dialog<>();
                Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image("/assets/img/icon.png"));
				
                d.setDialogPane((DialogPane) projectDialogPane);
                d.setTitle("Update projet");
                d.setResizable(false);
				d.initStyle(StageStyle.UNDECORATED);
				
				//these two are for moving the window with the mouse
				projectDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
		           @Override
		           public void handle(MouseEvent event) {
		               xOffset = event.getSceneX();
		               yOffset = event.getSceneY();
		           }
				});
	       
				projectDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
		           @Override
		           public void handle(MouseEvent event) {
		               d.setX(event.getScreenX() - xOffset);
		               d.setY(event.getScreenY() - yOffset);
		           }
	            });
				
                //name field listener
                puc.name.textProperty().addListener((observable, oldValue, newValue) -> {
                    if(!isAlpha(newValue)) {
                        puc.inv_name.setVisible(true);
                    }else
                        puc.inv_name.setVisible(false);
                });

                puc.budget.textProperty().addListener((observable, oldValue, newValue) -> {
                    if(!isNumeric(newValue)) {
                        puc.inv_budget.setVisible(true);
                    }else
                        puc.inv_budget.setVisible(false);
                });

                puc.start.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        if(newValue.isAfter(puc.end.getValue())) {
                            puc.warning.setVisible(true);
                        }
                        else {
                            puc.warning.setVisible(false);
                        }
                    }
                });

                puc.end.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        if(newValue.isBefore(puc.start.getValue())) {
                            puc.warning.setVisible(true);
                        }
                        else {
                            puc.warning.setVisible(false);
                        }
                    }
                });
                //to apply css on the dialog pane buttons
				d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
				d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");
				
				//make name field first to be selected
				puc.name.requestFocus();
                
                d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                                puc.name.getText().isEmpty()
                                        || puc.budget.getText().isEmpty()
                                        ||!isAlpha(puc.name.getText())
                                        ||!isNumeric(puc.budget.getText())
                                        || puc.start.getValue() == null
                                        || puc.end.getValue() == null
                                        ||puc.start.getValue().isAfter(puc.end.getValue()),
                        puc.name.textProperty(),
                        puc.budget.textProperty(),
                        puc.start.valueProperty(),
                        puc.end.valueProperty()));

                Optional<ButtonType> clickedButton = d.showAndWait();
                if(clickedButton.get() == ButtonType.APPLY) {
                    puc.setCurrentProject(projet);
                    projetService.update(projet);
                    refresh(event);
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    //Test if String is alphabetical
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z ]+");
    }
    @FXML
	void selectAll(ActionEvent event) {
		this.tableview.getSelectionModel().selectAll();
	}

}
