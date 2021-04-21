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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
	import org.fsb.municipalite.entities.Autorisation;
	import org.fsb.municipalite.services.impl.AutorisationServiceImpl;

	import java.net.URL;
	import java.time.LocalDateTime;
	import java.util.List;
	import java.util.Optional;
	import java.util.ResourceBundle;

public class AutorisationPageController implements Initializable{
		
	    @FXML
	    private BorderPane container;
		@FXML
		public TextField searchBox;
	    @FXML
	    public TableView<Autorisation> tableView;
	    @FXML
	    public TableColumn<Autorisation, Long> Id;
	    @FXML
	    public TableColumn<Autorisation, LocalDateTime> Date;
	    @FXML
	    public TableColumn<Autorisation, Autorisation.Etat> Status;
	    @FXML
	    public TableColumn<Autorisation, String> Name;
	    @FXML
	    public TableColumn<Autorisation, String> subject;
	    
	    public ObservableList<Autorisation> data;

	   @Override
	   public void initialize(URL location, ResourceBundle resources) {
	        Id.setCellValueFactory(new PropertyValueFactory<Autorisation,Long>("id"));
	        Date.setCellValueFactory(new PropertyValueFactory<Autorisation,LocalDateTime>("createdAt"));
	        Status.setCellValueFactory(new PropertyValueFactory<Autorisation,Autorisation.Etat>("etat"));
	        Name.setCellValueFactory(new PropertyValueFactory<Autorisation,String>("NomCitoyen"));
	        subject.setCellValueFactory(new PropertyValueFactory<Autorisation, String>("sujet"));
	        System.out.println(22);

	        AutorisationServiceImpl autorisationService =new AutorisationServiceImpl();
	        List<Autorisation> list = autorisationService.selectAll();
	        data  =  FXCollections.observableArrayList();
	        for (Autorisation a : list) {
	            data.addAll(a);
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
	    	AutorisationServiceImpl autorisationService =new AutorisationServiceImpl();
	        data = FXCollections.observableArrayList();
	        List<Autorisation> list;
	        if (!(n.equals(""))) {
	            if (isNumeric(n)) {
	                list = autorisationService.selectLike("id", n );
	                for (Autorisation a : list) {
	                    data.add(a);
	                }
	            } else {
	                list =autorisationService.selectLike("NomCitoyen","'"+n+"%'");
	                for (Autorisation a : list) {
	                    data.add(a);
	                }
	            }
	            tableView.setItems(data);
	        } else {
	            list = autorisationService.selectAll();
	            for (Autorisation a: list) {
	                data.add(a);
	            }
	            tableView.setItems(data);
	        }

	    }
			
	    @FXML
	    public void onClickEventRemove(ActionEvent event) {
	    	if(tableView.getSelectionModel().getSelectedItem() != null) {
	    		Autorisation a = (Autorisation) tableView.getSelectionModel().getSelectedItem();
	    		AutorisationServiceImpl autorisationService = new AutorisationServiceImpl();
	    		autorisationService.remove(a.getId());
	            monStock(event);
	    	}
	    	
	      
	    }

	    //refraiche
	    @FXML
	    public void monStock(ActionEvent event){
	        tableView.getItems().clear();
	        AutorisationServiceImpl autorisationService =new AutorisationServiceImpl();
	        List<Autorisation> list = autorisationService.selectAll();
	        data  =  FXCollections.observableArrayList();
	        for (Autorisation a : list) {
	            data.add(a);
	        }
	        tableView.setItems(data);
	    }

	    @FXML
	    public void onClickEventAdd(ActionEvent event) {
	        try {
	        	FXMLLoader f = new FXMLLoader();
	        	f.setLocation(getClass().getResource("/interfaces/AutorisationAddPage.fxml"));
				Pane autorisationDialogPane = f.load();
				
				AutorisationAddController auto = f.getController();
				
				Dialog<ButtonType> d = new Dialog<>();
				d.setDialogPane((DialogPane) autorisationDialogPane);
				d.setTitle("add Autorisation");
				Optional<ButtonType> clickedButton = d.showAndWait();
				if(clickedButton.get() == ButtonType.APPLY) {
					if(auto.name.getText().length()>0 && auto.subject.getText().length()>0 ) {
						Autorisation autori = new Autorisation();
						autori.setNomCitoyen(auto.name.getText());
				        autori.setSujet(auto.subject.getText());
				        if (auto.processed.isSelected()) {
				            autori.setEtat(Autorisation.Etat.processed);
				        }
				        if (auto.unprocessed.isSelected()) {
				            autori.setEtat(Autorisation.Etat.unprocessed);
				        }
				        AutorisationServiceImpl autorisationService = new AutorisationServiceImpl();
				        autorisationService.create(autori);
				        auto.msg.setText("Autorisation ADDED !");
					}
					
				}
				
				
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        monStock(event);

	    }


	    @FXML
	    public void UpdateAutorisation(ActionEvent event) {
	    	try {
		    	FXMLLoader f = new FXMLLoader();
				f.setLocation(getClass().getResource("/interfaces/AutorisationPageUpdate.fxml"));
				Pane autorisationDialogPane = f.load();
				AutorisationUpdateController muc = f.getController();
		    	
				if(tableView.getSelectionModel().getSelectedItem() != null) {
					AutorisationServiceImpl autorisationService = new AutorisationServiceImpl();
					Autorisation a = (Autorisation) tableView.getSelectionModel().getSelectedItem();
					Autorisation test = autorisationService.getById(a.getId());

					muc.setAutorisationDialogPane(test);
					Dialog<ButtonType> d = new Dialog<>();
					d.setDialogPane((DialogPane) autorisationDialogPane);
					d.setTitle("Update Autorisation");
					Optional<ButtonType> clickedButton = d.showAndWait();
					if(clickedButton.get() == ButtonType.APPLY) {
							
							muc.getCurrentAutorisation(test);
							System.out.println(test);
							autorisationService.update(test);
					        monStock(event);
					}
				}
			
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    public void goBack(ActionEvent event) {
	    	BorderPane MainInterface = (BorderPane) container.getParent();
			Pane view = CustomFxmlLoader.getPage("ServicesPage");
			MainInterface.setCenter(view);
	    }
	

}
