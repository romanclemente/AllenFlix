package Controller;

import Logica.BBDD;
import Logica.Serie;
import Logica.Usuario;
import Ventanas.Logeo;
import Ventanas.Ventana_Aplicacion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controladora_loggin implements Initializable {
    BBDD bbdd;
    ArrayList nums;
    @FXML
    Button log;
    @FXML
    Label olvide, registro;
    @FXML
    TextField user, psw;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instancias();
        acciones();

    }

    private void acciones() {
        olvide.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) user.getScene().getWindow();
                Parent parent = null;
                try {
                    parent = FXMLLoader.load(getClass().getResource("../Layouts/RecuperarPassword.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(parent, 1280, 860);
                stage.setScene(scene);
                stage.show();
            }
        });
        olvide.setOnMouseEntered(new cambioMano());
        olvide.setOnMouseExited(new cursorOriginal());
        registro.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) user.getScene().getWindow();
                Parent parent = null;
                try {
                    parent = FXMLLoader.load(getClass().getResource("../Layouts/Registro.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(parent, 1280, 860);
                stage.setScene(scene);
                stage.show();
            }
        });
        registro.setOnMouseEntered(new cambioMano());
        registro.setOnMouseExited(new cursorOriginal());
        log.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!user.getText().equalsIgnoreCase("") && !psw.getText().equalsIgnoreCase("")) {
                    Usuario usuario = new Usuario(user.getText(), psw.getText());
                    if (bbdd.comprobarLoggin(usuario)) {
                        log.setOnAction(null);
                        Usuario usuario1 = bbdd.getUsuario();
                        ArrayList<Serie> series = bbdd.getSer().getSeries();
                        Stage stage = (Stage) user.getScene().getWindow();
                        Ventana_Aplicacion ventana_aplicacion = new Ventana_Aplicacion(series, usuario1);
                        stage.close();


                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error al iniciar sesion");
                        alert.setHeaderText("Por favor, inserte bien los datos");
                        alert.setContentText("El nombre de usuario y/o contraseña son erroneos");
                        alert.show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Campos vacios");
                    alert.setHeaderText("Por favor, rellene los campos restantes");
                    alert.setContentText("Es necesario rellenar los campos para iniciar sesion");
                    alert.show();
                }
            }
        });
        log.setOnMouseEntered(new cambioMano());
        log.setOnMouseExited(new cursorOriginal());
    }

    private void instancias() {

        definirParametros();
        bbdd = new BBDD();
        user.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
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
        psw.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 9) {
                return null;
            } else {
                return change;
            }
        }));
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

    private class cambioMano implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            Scene scene = user.getScene();
            scene.setCursor(Cursor.HAND);
        }
    }

    private class cursorOriginal implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            Scene scene = user.getScene();
            scene.setCursor(Cursor.DEFAULT);
        }
    }
}
