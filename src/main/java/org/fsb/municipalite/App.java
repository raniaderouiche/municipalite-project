package org.fsb.municipalite;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class App extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/LoginScene.fxml"));
        Scene scene = new Scene(root);
        Image icon = new Image("/assets/img/icon.png");
        stage.setScene(scene);
        stage.getIcons().add(icon);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Bizerte Municipality");
        stage.show();
    }


    public static void main(String[] args) {
        //create entity manager instance and run it for a first time to migrate changes
        /*EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("todo");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.close();*/

        //launch app window
        launch();
    }
}