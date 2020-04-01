package Controller;

import Logica.BBDD;
import Logica.Usuario;
import Logica.Usuario_Serie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_Valoracion implements Initializable {
    BBDD bbdd;
    @FXML
    ImageView serie;
    @FXML
    Button like, dislike;
    private String user_name, name;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instancias();
        acciones();
    }

    private void instancias() {
        bbdd = new BBDD();
    }

    private void acciones() {
        like.setOnAction(new AccionBoton());
        dislike.setOnAction(new AccionBoton());
    }

    public void comunicarParametros(String image, String name, String user) {
        this.user_name = user;
        this.name = name;
        serie.setImage(new Image(image));
        serie.setFitHeight(500);
        serie.setFitWidth(300);

        ImageView likebtn = new ImageView(new Image(getClass().getResourceAsStream("../Resources/like.png")));
        likebtn.setFitWidth(50);
        likebtn.setFitHeight(50);
        like.setGraphic(likebtn);

        ImageView dislikebtn = new ImageView(new Image(getClass().getResourceAsStream("../Resources/dislike.png")));
        dislikebtn.setFitWidth(50);
        dislikebtn.setFitHeight(50);
        dislike.setGraphic(dislikebtn);
    }

    private class AccionBoton implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            boolean add, disagree;
            if (event.getSource() == like) {
                add = true;
                disagree = false;
            } else {
                add = false;
                disagree = true;
            }
            Usuario_Serie usuario_serie = new Usuario_Serie(user_name, name, add, disagree);
            boolean valoracion = bbdd.enviarApreciacion(usuario_serie);
            if (valoracion) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Solicitud enviada con exito");
                alert.setContentText("Solicitud añadida con exito, gracias por usar este servicio");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Usted ya ha enviado una solicitud");
                alert.setContentText("Sentimos decirle que ya ha valorado esta serie");
                alert.show();
            }
        }
    }
}
