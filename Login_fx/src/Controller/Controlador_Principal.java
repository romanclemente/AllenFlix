package Controller;

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
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class Controlador_Principal implements Initializable {
    ArrayList<Serie> lista_series;
    Controlador_Principal controlador_principal;
    @FXML
    ImageView icono_usuario, fondo_view;
    @FXML
    MenuButton menu_personal;
    @FXML
    ListView populares, tendencias;
    ObservableList<Button> lista_boton_popu, lista_boton_tendencias;
    ObservableList<Serie> lista_todas;
    Usuario actual;
    MediaPlayer mediaPlayer;
    @FXML
    TableColumn<Serie, Void> imagen_tabla;
    @FXML
    TableColumn<Serie, String> nombre_tabla;
    @FXML
    TableColumn<Serie, Double> votacion;
    @FXML
    TableView tabladatos;
    @FXML
    TextField buscador;
    FilteredList<Serie> listaFiltradas;
    Ventana_Temporal ventana_temporal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        acciones();
    }

    private void instancias() throws MalformedURLException {
        File f = new File("src/Resources/pelicula.mp4");
        Media media = new Media(f.toURI().toURL().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(100);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
    }

    private void acciones() {
        fondo_view.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    instancias();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage) tabladatos.getScene().getWindow();
                Ventana_Video ventana_video = new Ventana_Video(mediaPlayer);
            }
        });
        buscador.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                listaFiltradas.setPredicate(new Predicate<Serie>() {
                    @Override
                    public boolean test(Serie serie) {
                        return serie.getName().contains(newValue);
                    }
                });
            }
        });
    }


    public void comunicarParametros(ArrayList<Serie> lista_series, Usuario usuario) {
        actual = usuario;
        controlador_principal = this;
        this.lista_series = lista_series;
        lista_boton_popu = FXCollections.observableArrayList();
        lista_boton_tendencias = FXCollections.observableArrayList();
        lista_todas = FXCollections.observableArrayList();
        lista_todas.setAll(lista_series);
        listaFiltradas = new FilteredList(lista_todas);
        tabladatos.setItems(listaFiltradas);
        nombre_tabla.setCellValueFactory(new PropertyValueFactory<>("name"));
        votacion.setCellValueFactory(new PropertyValueFactory<>("vote_average"));

        for (int i = 0; i <= 20; i++) {
            String url = "https://image.tmdb.org/t/p/w500" + colocarPopulares(lista_series).get(i).getPoster_path();
            String nombre = colocarPopulares(lista_series).get(i).getName();
            Image image = new Image(url);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(150.0);
            imageView.setFitWidth(175.0);
            Button button = new Button();
            button.setGraphic(imageView);
            button.setBackground(null);
            button.setBorder(null);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Ventana_valoracion ventana_valoracion = new Ventana_valoracion(url, nombre, actual.getUsuario());
                }
            });
            lista_boton_popu.add(button);
        }
        populares.setOrientation(Orientation.HORIZONTAL);
        populares.setBackground(null);
        populares.setEditable(false);
        populares.setBorder(null);
        populares.setItems(lista_boton_popu);

        for (int i = 0; i <= 20; i++) {
            String url = "https://image.tmdb.org/t/p/w500" + colocarMejorValoradas(lista_series).get(i).getPoster_path();
            String nombre = colocarMejorValoradas(lista_series).get(i).getName();
            Image image = new Image(url);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(150.0);
            imageView.setFitWidth(175.0);
            Button button = new Button();
            button.setGraphic(imageView);
            button.setBackground(null);
            button.setBorder(null);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Ventana_valoracion ventana_valoracion = new Ventana_valoracion(url, nombre, actual.getUsuario());
                }
            });
            lista_boton_tendencias.add(button);
        }
        tendencias.setOrientation(Orientation.HORIZONTAL);
        tendencias.setBackground(null);
        tendencias.setEditable(false);
        tendencias.setBorder(null);
        tendencias.setItems(lista_boton_tendencias);
        addButtonToTable();
        MenuItem cerrarSesion = new MenuItem("Cerrar sesion");
        MenuItem cambiarImg = new MenuItem("Cambiar imagen");
        MenuItem configuracion_Cuenta = new MenuItem("Configuracion");
        menu_personal.getItems().clear();
        menu_personal.getItems().add(cerrarSesion);
        menu_personal.getItems().add(cambiarImg);
        menu_personal.getItems().add(configuracion_Cuenta);

        EventHandler<ActionEvent> gestion_Menu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (e.getSource() == cerrarSesion) {
                    Logeo logeo = new Logeo();
                    tabladatos.getScene().getWindow().hide();

                } else if (e.getSource() == cambiarImg) {
                    Stage stage = (Stage) menu_personal.getScene().getWindow();
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open Resource File");
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
                    File selectedFile = fileChooser.showOpenDialog(stage);
                    if (selectedFile != null) {
                        Path src = Paths.get(selectedFile.getPath());
                        System.out.println(selectedFile.getPath());
                        System.out.println(src.getFileName());
                        Path dest = Paths.get("C:\\Users\\romim\\Desktop\\Proyectos\\JavaFX\\Login_fx\\src\\Resources");
                        System.out.println(dest.getParent());
                        /*Files.copy(src.toFile(), dest.toFile());*/

                    }

                    //copiar en resoruces y luego la llamo
                } else if (e.getSource() == configuracion_Cuenta) {
                    Configuracion_Ventana configuracion_ventana = new Configuracion_Ventana();
                }
            }
        };
        cerrarSesion.setOnAction(gestion_Menu);
        cambiarImg.setOnAction(gestion_Menu);
        configuracion_Cuenta.setOnAction(gestion_Menu);
    }

    private void addButtonToTable() {

        Callback<TableColumn<Serie, Void>, TableCell<Serie, Void>> cellFactory = new Callback<TableColumn<Serie, Void>, TableCell<Serie, Void>>() {

            @Override
            public TableCell<Serie, Void> call(TableColumn<Serie, Void> param) {
                final TableCell<Serie, Void> cell = new TableCell<Serie, Void>() {


                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Serie data = getTableView().getItems().get(getIndex());
                            String url = "https://image.tmdb.org/t/p/w500" + data.getPoster_path();
                            Ventana_valoracion ventana_valoracion = new Ventana_valoracion(url, data.getName(), actual.getUsuario());

                        });
                        btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                Serie data = getTableView().getItems().get(getIndex());
                                String url = "https://image.tmdb.org/t/p/w500" + data.getPoster_path();
                                Stage stage = (Stage) tabladatos.getScene().getWindow();
                                ventana_temporal = new Ventana_Temporal(data.getOverview(), data.getName(), new Image(url));
                            }
                        });
                        btn.setOnMouseExited(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                ventana_temporal.close();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Serie data = getTableView().getItems().get(getIndex());
                            String url = "https://image.tmdb.org/t/p/w500" + data.getPoster_path();
                            Image image = new Image(url);
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(350.0);
                            imageView.setFitWidth(350.0);
                            btn.setGraphic(imageView);
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        imagen_tabla.setCellFactory(cellFactory);
        tabladatos.getColumns().add(imagen_tabla);


    }

    private ArrayList<Serie> colocarPopulares(ArrayList<Serie> series) {
        Collections.sort(series, new Comparator<Serie>() {
            @Override
            public int compare(Serie o1, Serie o2) {
                return new Double(o2.getPopularity()).compareTo(new Double(o1.getPopularity()));
            }
        });
        ArrayList<Serie> seriesPopulares = series;
        return seriesPopulares;
    }

    private ArrayList<Serie> colocarMejorValoradas(ArrayList<Serie> series) {
        Collections.sort(series, new Comparator<Serie>() {
            @Override
            public int compare(Serie o1, Serie o2) {
                return new Double(o2.getVote_average()).compareTo(new Double(o1.getVote_average()));
            }
        });
        ArrayList<Serie> seriesValoradas = series;
        return seriesValoradas;
    }
}

