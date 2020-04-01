package Ventanas;

import Controller.Controlador_Principal;
import Logica.Serie;
import Logica.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Ventana_Aplicacion extends Stage {
    private ArrayList<Serie> lista_series;
    private Usuario usuario;
    private Controlador_Principal controlador_principal;

    public Ventana_Aplicacion(ArrayList<Serie> lista_series, Usuario usuario1) {
        this.lista_series = lista_series;
        this.usuario = usuario1;
        initGUI();
    }

    private void initGUI() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../layouts/Pantalla_Principal.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        controlador_principal = loader.getController();
        controlador_principal.comunicarParametros(lista_series,usuario);
        this.setScene(scene);
        this.show();
    }

}
