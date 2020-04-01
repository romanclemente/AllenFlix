package Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.WindowEvent;

public class Controlador_Video {
    @FXML
    MediaView video_pelicula;

    public void comunicarParametros(MediaPlayer mediaPlayer) {
        video_pelicula.setMediaPlayer(mediaPlayer);
        video_pelicula.setFitHeight(800);
        video_pelicula.setFitWidth(1200);
    }
}
