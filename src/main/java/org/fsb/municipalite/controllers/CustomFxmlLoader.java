package org.fsb.municipalite.controllers;

import java.net.URL;

import org.fsb.municipalite.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class CustomFxmlLoader {
	private static Pane view;

	public static Pane getPage(String fileName) {
		try {
			URL fileUrl = App.class.getResource("/interfaces/" + fileName + ".fxml");
			if(fileUrl == null)
				throw new java.io.FileNotFoundException("FXML file can't be found");
			view = FXMLLoader.load(fileUrl);
		}catch(Exception e) {
			System.out.println("No page " + fileName + " please check FxmlLoader");
		}
		return view;
	}
}
