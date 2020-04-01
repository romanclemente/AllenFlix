package Controller;

import Logica.BBDD;
import Logica.Serie;
import Logica.Usuario;
import Ventanas.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

public class Controlador_Estadistica implements Initializable {
    BBDD bbdd;
    @FXML
    StackedBarChart Estadistica;
    @FXML
    Button calcular;
    @FXML
    CategoryAxis xAxis;
    @FXML
    NumberAxis yAxis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        xAxis.setLabel("Series vistas");
        xAxis.getCategories().addAll("Me han gustado", "No me han gustado");

        yAxis.setLabel("Vistas");

        instancias();
        acciones();
    }

    private void instancias() {
        bbdd = new BBDD();
    }

    private void acciones() {
        calcular.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int likes = bbdd.seriesLike();
                  int dislikes=bbdd.seriesDislike();


                XYChart.Series dataSeries1 = new XYChart.Series();
                dataSeries1.setName("Me han gustado");

                dataSeries1.getData().add(new XYChart.Data("2020", likes));

                Estadistica.getData().add(dataSeries1);

                XYChart.Series dataSeries2 = new XYChart.Series();
                dataSeries2.setName("No me han gustado");

                dataSeries2.getData().add(new XYChart.Data("2020", dislikes));

                Estadistica.getData().add(dataSeries2);


                calcular.setOnAction(null);


            }
        });

    }
}