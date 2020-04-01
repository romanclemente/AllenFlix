package Ventanas;

import Controller.Controlador_temporal;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;


public class Ventana_Temporal extends Stage {
    private String overwiew, name;
    private Image path;
    private Controlador_temporal controlador_temporal;

    public Ventana_Temporal(String overwiew, String name, Image path) {
        this.overwiew = overwiew;
        this.name = name;
        this.path = path;
        initGUI();
    }

    private void initGUI() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../layouts/Layout_Temporal.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        controlador_temporal = loader.getController();
        controlador_temporal.comunicarParametros(name, overwiew, path);
        this.setScene(scene);
        this.show();

    }


}


