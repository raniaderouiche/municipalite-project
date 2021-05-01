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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import org.fsb.municipalite.entities.Complaint;
import org.fsb.municipalite.entities.Municipalite;
import org.fsb.municipalite.services.impl.ComplaintServiceImpl;
import org.fsb.municipalite.services.impl.MunicipaliteServiceImpl;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
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
  
    public ObservableList<Complaint> data;
    
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	Id.setCellValueFactory(new PropertyValueFactory<Complaint,Long>("id"));
    	Version.setCellValueFactory(new PropertyValueFactory<Complaint,Long>("version"));
    	Date.setCellValueFactory(new PropertyValueFactory<Complaint,LocalDateTime>("createdAt"));
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
    		System.out.println("COMPLAINT ABOUT TO BE ADDED !");
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
    
    
    public void message(MouseEvent event) {
 	   try {
 	    	FXMLLoader f = new FXMLLoader();
 	    	f.setLocation(getClass().getResource("/interfaces/ComplaintMsg.fxml"));
 			Pane complaintDialogPane = f.load();
 			ComplaintMsgController edc = f.getController();
 			
 			tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
 			    @Override
 			    public void handle(MouseEvent mouseEvent) {
 			        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
 			            if(mouseEvent.getClickCount() == 2){
 			            	if(tableView.getSelectionModel().getSelectedItem() != null) {
 			    				ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
 			    				Complaint c = (Complaint) tableView.getSelectionModel().getSelectedItem();
 			    		        Complaint test = complaintService.getById(c.getId());

 			    				edc.setComplaintMsgDialogPane(test);
 			    				Dialog<ButtonType> d = new Dialog<>();
 			    				
 			    				Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
 			    				stage.getIcons().add(new Image("/assets/img/icon.png"));
 			    				
 			    				d.setDialogPane((DialogPane) complaintDialogPane);
 			    				d.setTitle("Complaint message");
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
 			    				d.getDialogPane().lookupButton(ButtonType.CLOSE).getStyleClass().add("dialogButtons");
 			    				Optional<ButtonType> clickedButton = d.showAndWait();

 			    	    		//new Complaint creation and addition
 			    	    		if (clickedButton.get() == ButtonType.CLOSE) {
 			    	    			stage.close();
 			    	    		}
 			    	 			System.out.println("everything is well done");   				
 			            	}
 			            }
 			        }
 			    }
 			});
         	
 	   }catch(Exception e) {
 		   e.printStackTrace();
 	   }
    }
    
    @FXML
	void selectAll(ActionEvent event) {
		this.tableView.getSelectionModel().selectAll();
	}
    
    public void print() {
    	MunicipaliteServiceImpl mc = new MunicipaliteServiceImpl();
    	Municipalite m = mc.getById(Long.parseLong("47"));
    	if (tableView.getSelectionModel().getSelectedItem() != null) {
    		ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
			Complaint c = (Complaint) tableView.getSelectionModel().getSelectedItem();
	        Complaint test = complaintService.getById(c.getId());
	        try {
				DirectoryChooser dirChooser = new DirectoryChooser();
			    dirChooser.setTitle("Select a folder");
			    
			    Window primaryStage = null;
				File selectedDir = dirChooser.showDialog(primaryStage);
				if(selectedDir != null){     
					Document document = new Document();					
					PdfWriter.getInstance(document, new FileOutputStream(selectedDir.getAbsolutePath()+"/Complaint"+Long.toString(test.getId())+".pdf"));
					document.open();
					
					Paragraph p1 = new Paragraph();
					Paragraph p2 = new Paragraph();
					Paragraph p3 = new Paragraph();
					Paragraph p4 = new Paragraph();
					Paragraph p5 = new Paragraph();
					
					Font municipalityName=new Font();
					municipalityName.setStyle(Font.BOLD);
					municipalityName.setSize(18);
					
					Font documentType=new Font();
					documentType.setSize(14);
					documentType.setStyle(Font.UNDERLINE);
					
					Font documentFooter=new Font();
					
					p1.add(m.getNom()+"\n");
					p1.setAlignment(Element.ALIGN_CENTER);
					p1.setFont(municipalityName);
					document.add(p1);
					
					p2.add("Complaint");
					p2.setAlignment(Element.ALIGN_CENTER);
					p2.setFont(documentType);
					document.add(p2);
					
					p3.add("Complaint Number : "+test.getId()+"\n");
					p3.add("Date : "+test.getCreatedAt()+"\n");
					p3.add("Citizen's name : "+test.getNomCitoyen()+"\n");
					p3.add("Citizen's CIN: "+test.getCin()+"\n");
					p3.add("Subject : "+test.getSujet()+"\n");
					p3.add("\nBody :\n "+test.getMsg());
					document.add(p3);
					
					p4.add(test.getMsg());
					document.add(p4);
					
					p5.add("\nContact : \n");
					p5.add("   number : "+m.getTel()+"\n");
					p5.add("   email : "+m.getEmail()+"\n");
					p5.add("   adress : "+m.getAdresse()+"\n");
					p5.add("   web site : "+m.getWebsite()+"\n");
					p5.setAlignment(Element.ALIGN_BOTTOM);
					document.add(p5);
					
					document.close();
			
				}
						
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			System.out.println("itext PDF program executed");
    	}
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
	    return name.matches("[a-zA-Z ]+");
	}
    
    //you can write a string with a alphabetic \n character and numbers
    public boolean isAlphaE(String name) {
	    return name.matches("[a-zA-Z 0-9]+[a-zA-Z .',0-9\n]*");
	}
}



