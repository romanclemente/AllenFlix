package Controller;

import Logica.BBDD;
import Logica.Usuario;
import Ventanas.Logeo;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controlador_recuperar implements Initializable {
    BBDD bbdd;
    ArrayList nums;
    @FXML
    Button volver, recuperar;
    @FXML
    PasswordField pass;
    @FXML
    JFXTextField nombre_usu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instancias();
        acciones();
    }

    private void acciones() {
        volver.setOnAction(new ManejoClick());
        recuperar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Usuario usuario = new Usuario(nombre_usu.getText(), "forget");
                boolean exito = bbdd.recuperarPass(usuario);
                if (exito) {
                    Alert alert= new Alert(Alert.AlertType.INFORMATION,"Exito");
                    alert.setTitle("Felicidades");
                    alert.setContentText("Contraseña actualizada con exito");
                    alert.show();
                    Logeo logeo = new Logeo();
                    nombre_usu.getScene().getWindow().hide();
                } else {
                    Alert alert= new Alert(Alert.AlertType.ERROR,"Error");
                    alert.setTitle("Felicidades");
                    alert.setContentText("No ha podido ser cambiada, o bien el usuario no es correcto, o no ha sido posible, intentalo en unos minutos");
                    alert.show();
                }
            }
        });
    }

    private void instancias() {
        bbdd = new BBDD();
        definirParametros();
        nombre_usu.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 9) {
                return null;
            } else {
                if (nums.contains(change.getText())) {
                    return null;
                } else {
                    return change;
                }

            }
        }));
        pass.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 9) {
                return null;
            } else {
                return change;
            }
        }));
        volver.setBackground(null);
        volver.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Resources/volver-boton-es.png"))));
        volver.setOnMouseExited(new cursorOriginal());
        volver.setOnMouseEntered(new cambioMano());
        recuperar.setOnMouseEntered(new cambioMano());
        recuperar.setOnMouseExited(new cursorOriginal());
    }

    private void definirParametros() {
        nums = new ArrayList();
        nums.add("0");
        nums.add("1");
        nums.add("2");
        nums.add("3");
        nums.add("4");
        nums.add("5");
        nums.add("6");
        nums.add("7");
        nums.add("8");
        nums.add("9");
        nums.add("?");
        nums.add("¿");
        nums.add("*");
        nums.add("^");
        nums.add("=");
        nums.add("ñ");
        nums.add(",");
        nums.add(".");
        nums.add("|");
        nums.add("@");
        nums.add("\\");
        nums.add("ª");
        nums.add("º");
        nums.add("(");
        nums.add(")");
        nums.add("{");
        nums.add("}");
        nums.add("ç");
        nums.add("&");
        nums.add("$");
        nums.add("%");
        nums.add("\"");
        nums.add("/");
        nums.add("·");
        nums.add("`");
        nums.add("+");
        nums.add("-");
        nums.add("_");
        nums.add(";");
        nums.add(":");
        nums.add("+");
        nums.add("*");
        nums.add("´");
        nums.add("ḉ");
        nums.add("!");
        nums.add("¡");
        nums.add("'");
    }

    private class ManejoClick implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() == volver) {
                {
                    Stage stage = (Stage) volver.getScene().getWindow();
                    Parent parent = null;
                    try {
                        parent = FXMLLoader.load(getClass().getResource("../Layouts/Loggin.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(parent, 1280, 860);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
    }

    private class cambioMano implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            Scene scene = pass.getScene();
            scene.setCursor(Cursor.HAND);
        }
    }

    private class cursorOriginal implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            Scene scene = pass.getScene();
            scene.setCursor(Cursor.DEFAULT);
        }
    }
}
