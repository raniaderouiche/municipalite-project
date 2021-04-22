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
    TableColumn<Complaint, String> subject;
    @FXML
    TableColumn<Complaint, Long> cin; 
    public ObservableList<Complaint> data;

   @Override
   public void initialize(URL location, ResourceBundle resources) {
	   System.out.println("complaint page controller");
        Id.setCellValueFactory(new PropertyValueFactory<Complaint,Long>("id"));
        Version.setCellValueFactory(new PropertyValueFactory<Complaint,Long>("version"));
        Date.setCellValueFactory(new PropertyValueFactory<Complaint,LocalDateTime>("createdAt"));
        Status.setCellValueFactory(new PropertyValueFactory<Complaint,Complaint.Etat>("etat"));
        Name.setCellValueFactory(new PropertyValueFactory<Complaint,String>("nomCitoyen"));
        cin.setCellValueFactory(new PropertyValueFactory<Complaint,Long>("cin"));
        subject.setCellValueFactory(new PropertyValueFactory<Complaint, String>("sujet"));
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

    @FXML
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

}



