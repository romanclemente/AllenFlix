package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_temporal implements Initializable {
    @FXML
    TextField titulo;
    @FXML
    TextArea info;
    @FXML
    ImageView img;


    public void comunicarParametros(String name, String overwiew, Image path) {
        titulo.setEditable(false);
        titulo.setText(name);
        info.setEditable(false);
        info.setText(overwiew);
        img.setImage(path);
        img.setFitWidth(308);
        img.setFitHeight(284);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
