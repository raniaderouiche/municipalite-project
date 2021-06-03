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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
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
    TableColumn<Complaint, LocalDateTime> Date;
    @FXML
    TableColumn<Complaint, Complaint.Etat> Status;
    @FXML
    TableColumn<Complaint, String> Name;
    @FXML
    TableColumn<Complaint, String> Subject;
    @FXML
    TableColumn<Complaint, Long> Cin; 
  
    public ObservableList<Complaint> data;
    
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	Id.setCellValueFactory(new PropertyValueFactory<Complaint,Long>("id"));
    	Date.setCellValueFactory(new PropertyValueFactory<Complaint,LocalDateTime>("DateComplaint"));
    	Status.setCellValueFactory(new PropertyValueFactory<Complaint,Complaint.Etat>("etat"));
    	Name.setCellValueFactory(new PropertyValueFactory<Complaint,String>("nomCitoyen"));
    	Cin.setCellValueFactory(new PropertyValueFactory<Complaint,Long>("cin"));
    	Subject.setCellValueFactory(new PropertyValueFactory<Complaint, String>("sujet"));

    	ComplaintServiceImpl complaintService =new ComplaintServiceImpl();
    	List<Complaint> list = complaintService.selectAll();
    	data  =  FXCollections.observableArrayList();
    	for (Complaint c : list) {
    		data.addAll(c);
    	}
    	tableView.setItems(data);
    	searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
    		ListenerSearch(newValue);
    	});
		tableView.setRowFactory( tv -> {
			TableRow<Complaint> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (! row.isEmpty()) && event.getButton().equals(MouseButton.PRIMARY) ) {
					Complaint rowData = row.getItem();
					message(rowData);
				}
				if ((row.isEmpty()) && event.getButton().equals(MouseButton.PRIMARY) ) 
					this.tableView.getSelectionModel().clearSelection();
					
			});
			return row ;
		});
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

    public void onClickEventAdd(ActionEvent event) {

    	try {
    		FXMLLoader f = new FXMLLoader();
    		f.setLocation(getClass().getResource("/interfaces/ComplaintAddPage.fxml"));
    		Pane compDialogPane = f.load();
    		ComplaintDialogController edc = f.getController();

    		Dialog<ButtonType> d = new Dialog<>();
    		//this is just for adding an icon to the dialog pane
    		Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
    		stage.getIcons().add(new Image("/assets/img/icon.png"));

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
    			edc.msgLength.setText(Integer.toString(edc.msg.getText().length())+"/3000");
    			if(!isAlphaE(newValue)) {
					edc.labMsg.setVisible(true);
				}else
					edc.labMsg.setVisible(false);
			});
    		//to apply css on the dialog pane buttons
    		d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
    		d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");

    		//make name field first to be selected

    		//apply button binder
    		d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() -> 
	    		edc.name.getText().isEmpty() || !isAlpha(edc.name.getText())||
				edc.cin.getText().isEmpty()||edc.cin.getText().length() != 8||!isNumeric(edc.cin.getText())||				
				edc.subject.getText().length() >60|| edc.subject.getText().isEmpty()||!isAlpha(edc.subject.getText())||
				edc.msg.getText().length()>3000 ||edc.msg.getText().isEmpty()||!isAlphaE(edc.msg.getText()),												
				edc.name.textProperty(),edc.cin.textProperty(),edc.subject.textProperty(),
				edc.msg.textProperty()));


    		Optional<ButtonType> clickedButton = d.showAndWait();
    		//new Complaint creation and addition
    		if (clickedButton.get() == ButtonType.APPLY) {
    			Complaint c = new Complaint();
    			c.setNomCitoyen(edc.name.getText());
    			c.setCin(Long.parseLong(edc.cin.getText()));
    			c.setSujet(edc.subject.getText());
    			c.setMsg(edc.msg.getText());
    			if (edc.processed.isSelected()) {
    				c.setEtat(Complaint.Etat.processed);
    			}
    			if (edc.unprocessed.isSelected()) {
    				c.setEtat(Complaint.Etat.unprocessed);
    			}

    			ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
    			complaintService.create(c);
    			System.out.println("COMPLAINT ADDED !");
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
	    	f.setLocation(getClass().getResource("/interfaces/ComplaintAddPage.fxml"));
			Pane complaintDialogPane = f.load();
			ComplaintDialogController edc = f.getController();

			if(tableView.getSelectionModel().getSelectedItem() != null) {
				ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
				Complaint c = (Complaint) tableView.getSelectionModel().getSelectedItem();
		        Complaint test = complaintService.getById(c.getId());

				edc.setComplaintDialogPane(test);
				edc.titleLabel.setText("Update Complaint");
				Dialog<ButtonType> d = new Dialog<>();
				
				Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image("/assets/img/icon.png"));
				
				d.setDialogPane((DialogPane) complaintDialogPane);
				d.setTitle("Update Complaint");
				d.setResizable(false);
				d.initStyle(StageStyle.UNDECORATED);
				
				
				complaintDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			           @Override
			           public void handle(MouseEvent event) {
			               xOffset = event.getSceneX();
			               yOffset = event.getSceneY();
			           }
					});
				
				complaintDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
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
					edc.msgLength.setText(newValue.length()+"/3000");
					if(!isAlphaE(newValue)) {
						edc.labMsg.setVisible(true);
					}else
						edc.labMsg.setVisible(false);
				});


				d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
				d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");
				
				//make name field first to be selected

				//apply button binder
				d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() -> 
												edc.name.getText().isEmpty() || !isAlpha(edc.name.getText())||
												edc.cin.getText().isEmpty()||edc.cin.getText().length() != 8||!isNumeric(edc.cin.getText())||				
												edc.subject.getText().length() >60|| edc.subject.getText().isEmpty()||!isAlpha(edc.subject.getText())||
												edc.msg.getText().length()>3000 ||edc.msg.getText().isEmpty()||!isAlphaE(edc.msg.getText()),												
												edc.name.textProperty(),edc.cin.textProperty(),edc.subject.textProperty(),
												edc.msg.textProperty()));

				Optional<ButtonType> clickedButton = d.showAndWait();
				if(clickedButton.get() == ButtonType.APPLY) {
						
						edc.getCurrentComplaint(test);
				        complaintService.update(test);
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
            alert.setTitle("Delete Complaint ?");
			alert.setHeaderText(null);
            alert.setContentText("Are you Sure You Want to Delete Selected Complaint(s) ?");
            Optional <ButtonType> action = alert.showAndWait();
            if(action.get() == ButtonType.OK) {
                ObservableList<Complaint> selectedComplaintList = tableView.getSelectionModel().getSelectedItems();
                for(Complaint c : selectedComplaintList) {
                	ComplaintServiceImpl cService = new ComplaintServiceImpl();
                	cService.remove(c.getId());
                }
                monStock(event);
            }
        }
    }
    
    
    public void message(Complaint complaint) {
 	   try {
		   FXMLLoader f = new FXMLLoader();
		   f.setLocation(getClass().getResource("/interfaces/ComplaintMsg.fxml"));
		   Pane complaintDialogPane = f.load();
		   ComplaintMsgController edc = f.getController();

		   ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
		   Complaint comp = complaintService.getById(complaint.getId());

		   edc.title.setText(edc.title.getText()+" "+ comp.getId());
		   edc.setComplaintMsgDialogPane(comp);
		   Dialog<ButtonType> d = new Dialog<>();

		   Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
		   stage.getIcons().add(new Image("/assets/img/icon.png"));

		   d.setDialogPane((DialogPane) complaintDialogPane);
		   d.setTitle("Complaint Message");
		   d.initStyle(StageStyle.UNDECORATED);

		   complaintDialogPane.setOnMousePressed(event -> {
			   xOffset = event.getSceneX();
			   yOffset = event.getSceneY();
		   });
		   complaintDialogPane.setOnMouseDragged(event -> {
			   d.setX(event.getScreenX() - xOffset);
			   d.setY(event.getScreenY() - yOffset);
		   });

		   d.getDialogPane().lookupButton(ButtonType.CLOSE).getStyleClass().add("dialogButtons");
		   d.showAndWait();


         	
 	   }catch(Exception e) {
 		   e.printStackTrace();
 	   }
    }
    
    @FXML
	void selectAll(ActionEvent event) {
		this.tableView.getSelectionModel().selectAll();
	}
    

    //the String contain just numbers
    public boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    
    //the string should contain just character for a to z and A to Z
    public boolean isAlpha(String name) {
	    return name.matches("[a-zA-Z' ' ]+");
	}
    
    //you can write a string with a alphabetic \n character and numbers
    public boolean isAlphaE(String name) {
	    return name.matches("[a-zA-Z 0-9]+[a-zA-Z .',0-9\n' ']*");
	}
}



