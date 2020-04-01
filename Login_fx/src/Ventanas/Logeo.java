package Ventanas;


import Controller.Controlador_Principal;
import Logica.Serie;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class Logeo extends Stage {

    public Logeo() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../Layouts/Loggin.fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 1280, 860);
        this.setScene(scene);
        this.initStyle(StageStyle.DECORATED);
        this.show();
    }

}