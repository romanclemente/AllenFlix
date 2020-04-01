package Ventanas;

import Controller.Controlador_Principal;
import Controller.Controlador_Video;
import Logica.Serie;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Ventana_Video extends Stage {
    private MediaPlayer mediaPlayer;
    private Controlador_Video controlador_video;

    public Ventana_Video(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        initGUI();
    }
    private void initGUI() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Layouts/Layout_Video.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        controlador_video = loader.getController();
        controlador_video.comunicarParametros(mediaPlayer);
        this.setScene(scene);
        this.show();
    }
}
