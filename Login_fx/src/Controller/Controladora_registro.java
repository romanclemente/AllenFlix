package Controller;

import Logica.BBDD;
import Logica.Usuario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controladora_registro implements Initializable {
    BBDD bbdd;
    ArrayList nums;
    int punto = 0;
    int arroba = 0;
    @FXML
    TextField correo, nombre_usu, telefono;
    @FXML
    PasswordField pass;
    @FXML
    CheckBox check;
    @FXML
    Button volver, registrar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instancias();
        acciones();
    }

    private void instancias() {
        bbdd = new BBDD();
        definirParametros();
        volver.setBackground(null);
        volver.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Resources/volver-boton-es.png"))));
        telefono.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    telefono.setText(oldValue);
                }
            }
        });
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
        correo.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 13) {
                return null;
            } else {
                String temp = change.getText();
                if (temp.equals("@") || temp.equals(".")) {
                    if (temp.equals("@")) {
                        arroba++;
                        if (arroba > 1) {
                            return null;
                        } else {
                            return change;
                        }
                    } else {
                        punto++;
                        if (punto > 1) {
                            return null;
                        } else {
                            return change;
                        }
                    }
                } else {
                    if (nums.contains(change.getText())) {
                        return null;
                    } else {
                        return change;
                    }
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
        volver.setOnMouseEntered(new cambioMano());
        volver.setOnMouseExited(new cursorOriginal());
        registrar.setOnMouseEntered(new cambioMano());
        registrar.setOnMouseExited(new cursorOriginal());
        check.setOnMouseEntered(new cambioMano());
        check.setOnMouseExited(new cursorOriginal());

        //numero
        /*user.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    user.setText(oldValue);
                }
            }
        });
        //letras
        user.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 9) {
                return null;
            } else {
                   if (nums.contains(change.getText())){
                    return null;}
                   else{
                       return change;
                   }

            }
        }));*/
    }

    private void acciones() {
        volver.setOnAction(new ManejoButton());
        registrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                {
                    if (!correo.getText().equalsIgnoreCase("") && !nombre_usu.getText().equalsIgnoreCase("")
                            && !telefono.getText().equalsIgnoreCase("") && !pass.getText().equalsIgnoreCase("")) {
                        boolean info = false;
                        if (check.isSelected()) {
                            info = true;
                        }
                        Usuario usuario = new Usuario(correo.getText(), nombre_usu.getText(), telefono.getText(), pass.getText(), info);

                        if (!bbdd.comprobarConcidencia(usuario)) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Usuario registrado con existo");
                            alert.setHeaderText("El nuevo usuario se ha generado con existo");
                            alert.setContentText("Gracias por confiar con nosotros");
                            Optional<ButtonType> resultado = alert.showAndWait();

                            if (resultado.get() == ButtonType.OK) {
                                Stage stage = (Stage) nombre_usu.getScene().getWindow();
                                Parent parent = null;

                                try {
                                    parent = FXMLLoader.load(getClass().getResource("../Layouts/Loggin.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Scene scene = new Scene(parent, 1280, 860);
                                stage.setScene(scene);
                                stage.show();
                            } else if (resultado.get() == ButtonType.CANCEL) {
                                Stage stage = (Stage) nombre_usu.getScene().getWindow();
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
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error al registrar");
                            alert.setHeaderText("El nombre de usuario o correo ya existe");
                            alert.setContentText("Intentelo de nuevo con otro correo o usuario");
                            alert.show();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error al registrar");
                        alert.setHeaderText("Campos vacios");
                        alert.setContentText("Por favor, inserte todos los campos e intente de nuevo");
                        alert.show();
                    }
                }
            }
        });
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

    private class ManejoButton implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() == volver) {
                {
                    Stage stage = (Stage) nombre_usu.getScene().getWindow();
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
            Scene scene = nombre_usu.getScene();
            scene.setCursor(Cursor.HAND);
        }
    }

    private class cursorOriginal implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            Scene scene = nombre_usu.getScene();
            scene.setCursor(Cursor.DEFAULT);
        }
    }
}

