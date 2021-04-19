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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Tache;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;
import org.fsb.municipalite.services.impl.TacheServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TachePageController implements Initializable {
    @FXML
    TextField searchBox;
    @FXML
    TableView<Tache> tableView;
    @FXML
    TableColumn<Tache, Long> Id;
    @FXML
    TableColumn<Tache, String> Name;
    @FXML
    TableColumn<Tache, LocalDateTime> DueDate;
    @FXML
    TableColumn<Tache, Employee> Employe;
    @FXML
    TableColumn<Tache, Tache.Etat> Etat;
    

    public ObservableList<Tache> data;
    
	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    //define your offsets here
    private double xOffset = 0;
    private double yOffset = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Id.setCellValueFactory(new PropertyValueFactory<Tache, Long>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<Tache, String>("Name"));
        DueDate.setCellValueFactory(new PropertyValueFactory<Tache, LocalDateTime>("dueDate"));
        Employe.setCellValueFactory(new PropertyValueFactory<Tache, Employee>("EmployeeId"));
        Etat.setCellValueFactory(new PropertyValueFactory<Tache, Tache.Etat>("etat"));

        TacheServiceImpl TacheService = new TacheServiceImpl();
        List<Tache> list = TacheService.selectAll();
        data  =  FXCollections.observableArrayList();
        for (Tache t : list) {
            data.add(t);
        }
        tableView.setItems(data);
        //adding the listener
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
            ListenerSearch(newValue);
        });
    }
    @FXML
    public void Taches(ActionEvent event) {
    	
        tableView.getItems().clear();
        TacheServiceImpl TacheService = new TacheServiceImpl();
        List<Tache> list = TacheService.selectAll();
        for (Tache t : list) {
            data.add(t);
        }
        tableView.setItems(data);
    }
    public void ListenerSearch(String n){
        
    	TacheServiceImpl tacheService =new TacheServiceImpl();
        data = FXCollections.observableArrayList();
        List<Tache> list;
        if (!(n.equals(""))) {
            if (isNumeric(n)) {
                list = tacheService.selectLike("id", n );
                for (Tache t : list) {
                    data.add(t);
                }
            } else {
                list =tacheService.selectLike("name", "'" + n + "%'");
                for (Tache t : list) {
                    data.add(t);
                }
            }
            tableView.setItems(data);
        } else {
            list = tacheService.selectAll();
            for (Tache t : list) {
                data.add(t);
            }
            tableView.setItems(data);
        }
		
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
	    return name.matches("[a-zA-Z]+");
	}

    @FXML
    public void onClickEventAdd(ActionEvent event) throws IOException {
       
    	try {
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/interfaces/TacheDialogPage.fxml"));
            Pane tacheDialogPane = f.load();
            
			//get the current controller and put it in edc
            TacheDialogController tac = f.getController();

            Dialog<ButtonType> d = new Dialog<>();
            //this is just for adding an icon to the dialog pane
            Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/assets/img/icon.png"));

            d.setDialogPane((DialogPane) tacheDialogPane);
            d.setTitle("Add Task");
            d.setResizable(false);
            d.initStyle(StageStyle.UNDECORATED);

            //these two are for moving the window with the mouse
            tacheDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            tacheDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    d.setX(event.getScreenX() - xOffset);
                    d.setY(event.getScreenY() - yOffset);
                }
            });
            
            
            tac.name.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!isAlpha(newValue)) {
                    tac.inv_name.setVisible(true);

                }else
                    tac.inv_name.setVisible(false);
            });
            
            tac.DD.valueProperty().addListener((observable, oldValue, newValue) -> {
            	if(newValue != null) {
            		if(newValue.isBefore(LocalDate.now())) {
                        tac.inv_date.setVisible(true);

                    }else
                        tac.inv_date.setVisible(false);
            	}
                
            });
            //to apply css on the dialog pane buttons
            d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
            d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");

            //make name field first to be selected
            tac.name.requestFocus();

            d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                            tac.name.getText().isEmpty() ||  !isAlpha(tac.name.getText()) || tac.DD.getValue() == null|| tac.DD.getValue().isBefore(LocalDate.now()) , tac.name.textProperty(), tac.DD.valueProperty()));
            Optional<ButtonType> clickedButton = d.showAndWait();
            if (clickedButton.get() == ButtonType.APPLY) {
            	Tache tache = new Tache();
                tache.setName(tac.name.getText());
                EmployeeServiceImpl eService = new EmployeeServiceImpl();
                Employee e = eService.getById(Long.parseLong(tac.employeeChoice.getValue().toString().split(",")[0]));
                System.out.println("2");
                tache.setEmployee(e);
                System.out.println("3");
                tache.setDueDate(tac.DD.getValue());
                if (tac.Done.isSelected()) {
                    tache.setEtat(Tache.Etat.done);
                }
                if (tac.InProg.isSelected()) {
                    tache.setEtat(Tache.Etat.inProgress);
                }
                TacheServiceImpl tacheService = new TacheServiceImpl();
                tacheService.create(tache);
        		Taches(event);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
			
        }

    @FXML
    public void UpdateTask(ActionEvent event) {
    	
        try {
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/interfaces/TacheDialogPage.fxml"));
            Pane tacheDialogPane = f.load();
            TacheDialogController tdc = f.getController();

            if(tableView.getSelectionModel().getSelectedItem() != null) {
                TacheServiceImpl TacheService = new TacheServiceImpl();
                Tache t = (Tache) tableView.getSelectionModel().getSelectedItem();
                Tache test = TacheService.getById(t.getId());
                
                System.out.println(t.getId()+t.getName()+t.getDueDate()+t.getEmployee()+t.getEtat());
                tdc.setFields(test);
                Dialog<ButtonType> d = new Dialog<>();

                //this is just for adding an icon to the dialog pane
                Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("/assets/img/icon.png"));

                d.setDialogPane((DialogPane) tacheDialogPane);
                d.setTitle("update Task");
                d.setResizable(false);
                d.initStyle(StageStyle.UNDECORATED);

                //these two are for moving the window with the mouse
                tacheDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                    }
                });

                tacheDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        d.setX(event.getScreenX() - xOffset);
                        d.setY(event.getScreenY() - yOffset);
                    }
                });
                
                
                tdc.name.textProperty().addListener((observable, oldValue, newValue) -> {
                    //System.out.println("textfield changed from " + oldValue + " to " + newValue);

                    if(!isAlpha(newValue)) {
                    	
                        tdc.inv_name.setVisible(true);

                    }else
                        tdc.inv_name.setVisible(false);
                });

                //to apply css on the dialog pane buttons
                d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
                d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");

               
                d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                                tdc.name.getText().isEmpty() ||  !isAlpha(tdc.name.getText()), tdc.name.textProperty()));
                
                
                Optional<ButtonType> clickedButton = d.showAndWait();
                if(clickedButton.get() == ButtonType.APPLY) {

                    tdc.setCurrentTache(test);
                    TacheService.update(test);
                    Taches(event);
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    public void onClicktaskRemove(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
        	
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/assets/img/icon.png"));
            alert.setTitle("Delete Task ?");
			alert.setHeaderText(null);
            alert.setContentText("Are you Sure You Want to Delete Selected Task(s) ?");
            Optional <ButtonType> action = alert.showAndWait();
            if(action.get() == ButtonType.OK) {
                ObservableList<Tache> selectedTasksList = tableView.getSelectionModel().getSelectedItems();
                for(Tache t : selectedTasksList) {
                	TacheServiceImpl tService = new TacheServiceImpl();
                	tService.remove(t.getId());
                }
                Taches(event);
            }
        }
    }

}

