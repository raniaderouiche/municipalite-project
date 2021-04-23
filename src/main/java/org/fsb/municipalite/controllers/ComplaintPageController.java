package org.fsb.municipalite.controllers;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;		
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.fsb.municipalite.entities.Complaint;
import org.fsb.municipalite.services.impl.ComplaintServiceImpl;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ComplaintPageController implements Initializable{

	@FXML
    TextField searchBox;
    @FXML
    TableView<Complaint> tableView;
    @FXML
    TableColumn<Complaint, Long> Id;
    @FXML
    TableColumn<Complaint, Long> Version;
    @FXML
    TableColumn<Complaint, LocalDateTime> Date;
    @FXML
    TableColumn<Complaint, Complaint.Etat> Status;
    @FXML
    TableColumn<Complaint, String> Name;
    @FXML
    TableColumn<Complaint, String> Subject;
    @FXML
    TableColumn<Complaint, Long> Cin; 
    
    
    
	    ObservableList<String> civilStatusList = FXCollections.observableArrayList("Single", "Married", "Divorced");
    RadioButton selectedRadioButton;
    public ObservableList<Complaint> data;
    private double xOffset = 0;
    private double yOffset = 0;

   @Override
   public void initialize(URL location, ResourceBundle resources) {
	   System.out.println("complaint page controller");
        Id.setCellValueFactory(new PropertyValueFactory<Complaint,Long>("id"));
        Version.setCellValueFactory(new PropertyValueFactory<Complaint,Long>("version"));
        Date.setCellValueFactory(new PropertyValueFactory<Complaint,LocalDateTime>("createdAt"));
        Status.setCellValueFactory(new PropertyValueFactory<Complaint,Complaint.Etat>("etat"));
        Name.setCellValueFactory(new PropertyValueFactory<Complaint,String>("nomCitoyen"));
        Cin.setCellValueFactory(new PropertyValueFactory<Complaint,Long>("cin"));
        Subject.setCellValueFactory(new PropertyValueFactory<Complaint, String>("sujet"));
        System.out.println(22);

        ComplaintServiceImpl complaintService =new ComplaintServiceImpl();
        List<Complaint> list = complaintService.selectAll();
        data  =  FXCollections.observableArrayList();
        for (Complaint c : list) {
            data.addAll(c);
        }
        tableView.setItems(data);
       searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
           System.out.println("textfield changed from " + oldValue + " to " + newValue);
           ListenerSearch(newValue);
       });
   	}

    public boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void ListenerSearch(String n){
        ComplaintServiceImpl complaintService =new ComplaintServiceImpl();
        data = FXCollections.observableArrayList();
        List<Complaint> list;
        if (!(n.equals(""))) {
            if (isNumeric(n)) {
                list = complaintService.selectLike("id", n );
                for (Complaint p : list) {
                    data.add(p);
                }
            } else {
                list =complaintService.selectLike("nomCitoyen", "'" + n + "%'");
                for (Complaint p : list) {
                    data.add(p);
                }
            }
            tableView.setItems(data);
        } else {
            list = complaintService.selectAll();
            for (Complaint p : list) {
                data.add(p);
            }
            tableView.setItems(data);
        }

    }
		
    @FXML
    public void onClickEventRemove(ActionEvent event) {
    	//zidouna haka alert wjaw 
    	// w multiple select
    	if(tableView.getSelectionModel().getSelectedItem() != null) {
    		Complaint m = (Complaint) tableView.getSelectionModel().getSelectedItem();
            ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
            complaintService.remove(m.getId());
            monStock(event);
    	}
    	
      
    }

    //refraiche
    @FXML
    public void monStock(ActionEvent event){
        tableView.getItems().clear();
        ComplaintServiceImpl complaintService =new ComplaintServiceImpl();
        List<Complaint> list = complaintService.selectAll();
        data  =  FXCollections.observableArrayList();
        for (Complaint m : list) {
            data.add(m);
        }
        tableView.setItems(data);
    }

    /*@FXML
    public void onClickEventAdd(ActionEvent event) {
        try {
        	FXMLLoader f = new FXMLLoader();
        	f.setLocation(getClass().getResource("/interfaces/ComplaintAddPage.fxml"));
			Pane complaintDialogPane = f.load();
			
			ComplaintAddController compc = f.getController();
			
			Dialog<ButtonType> d = new Dialog<>();
			d.setDialogPane((DialogPane) complaintDialogPane);
			d.setTitle("add ");
			Optional<ButtonType> clickedButton = d.showAndWait();
			if(clickedButton.get() == ButtonType.APPLY) {
				if(compc.name.getText().length()>0 && compc.subject.getText().length()>0 ) {
					Complaint comp = new Complaint();
					comp.setNomCitoyen(compc.name.getText());
			        comp.setSujet(compc.subject.getText());
			        comp.setCin(Long.parseLong(compc.cin.getText()));
			        if (compc.processed.isSelected()) {
			            comp.setEtat(Complaint.Etat.processed);
			        }
			        if (compc.unprocessed.isSelected()) {
			            comp.setEtat(Complaint.Etat.unprocessed);
			        }
			        ComplaintServiceImpl materielService = new ComplaintServiceImpl();
			        materielService.create(comp);
			        compc.msg.setText("ITEM ADDED !");
				}
				
			}			
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        monStock(event);

    }*/
    public void onClickEventAdd(ActionEvent event) {

		try {
			FXMLLoader f = new FXMLLoader();
			f.setLocation(getClass().getResource("/interfaces/ComplaintAddPage.fxml"));
			Pane compDialogPane = f.load();
			//get the current controller and put it in edc
			ComplaintDialogController edc = f.getController();
			
			Dialog<ButtonType> d = new Dialog<>();
			//this is just for adding an icon to the dialog pane
			
			Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/img/icon.png"));
			System.out.println("addComplaint clicked");
			d.setDialogPane((DialogPane) compDialogPane);
			d.setTitle("Add Complaint");
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
			//to apply css on the dialog pane buttons
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

			//new Complaint creation and addition
			if (clickedButton.get() == ButtonType.APPLY) {
				Complaint complaint = new Complaint();
				edc.setCurrentComplaint(complaint);				
		        ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
		        complaintService.create(complaint);
		        System.out.println("complaint added");
		        monStock(event);
		        
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



    @FXML
    public void UpdateComplaint(ActionEvent event) {
    	try {
	    	FXMLLoader f = new FXMLLoader();
			f.setLocation(getClass().getResource("/interfaces/ComplaintPageUpdate.fxml"));
			Pane complaintDialogPane = f.load();
			ComplaintUpdateController muc = f.getController();
	    	
			if(tableView.getSelectionModel().getSelectedItem() != null) {
				ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
				Complaint c = (Complaint) tableView.getSelectionModel().getSelectedItem();
		        Complaint test = complaintService.getById(c.getId());

				muc.setComplaintDialogPane(test);
				Dialog<ButtonType> d = new Dialog<>();
				d.setDialogPane((DialogPane) complaintDialogPane);
				d.setTitle("Update Complaint");
				Optional<ButtonType> clickedButton = d.showAndWait();
				if(clickedButton.get() == ButtonType.APPLY) {
						
						muc.getCurrentComplaint(test);
						System.out.println(test);
				        complaintService.update(test);
				        monStock(event);
				}
			}
		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    public boolean isAlpha(String name) {
	    return name.matches("[a-zA-Z ]+");
	}
}



