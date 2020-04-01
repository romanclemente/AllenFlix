package Ventanas;

import Controller.Controlador_Valoracion;
import Controller.Controlador_Video;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

public class Ventana_valoracion extends Stage {
    private String image, name, user;
    private Controlador_Valoracion controlador_valoracion;

    public Ventana_valoracion(String image, String name, String user) {
        this.image = image;
        this.name = name;
        this.user = user;
        initGUI();
    }

    private void initGUI() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Layouts/Valoracion.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        controlador_valoracion = loader.getController();
        controlador_valoracion.comunicarParametros(image, name, user);
        this.setScene(scene);
        this.show();
    }
}
