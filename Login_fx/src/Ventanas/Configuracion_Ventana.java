package Ventanas;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Configuracion_Ventana extends Stage {

    public Configuracion_Ventana() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../Layouts/Estadistica_Layout.fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 1280, 860);
        this.setScene(scene);
        this.initStyle(StageStyle.DECORATED);
        this.show();
    }

}