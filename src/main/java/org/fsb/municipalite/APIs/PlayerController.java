package org.fsb.municipalite.APIs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;


public class PlayerController implements Initializable {
    @FXML
    WebView player;

    WebEngine engine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        engine = player.getEngine();
        engine.loadContent("<div style=\"padding:56.25% 0 0 0;position:relative;\"><iframe src=\"https://player.vimeo.com/video/429388049?autoplay=1&loop=1&title=0&byline=0&portrait=0\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\" frameborder=\"0\" allow=\"autoplay; fullscreen; picture-in-picture\" allowfullscreen></iframe></div><script src=\"https://player.vimeo.com/api/player.js\"></script>");

    }
}
