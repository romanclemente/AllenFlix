package Logica;


import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

/*DROP TABLE usuarios;
        DROP TABLE series_usuarios;
        CREATE TABLE `Series` (
        `Name` varchar(50) NOT NULL,
        `Original_name` varchar(50) NOT NULL,
        `First_air_date` varchar(50) NOT NULL,
        `Backdrop_path` varchar(50) NOT NULL,
        `Original_language` varchar(50) NOT NULL,
        `Poster_path` varchar(50) NOT NULL,
        `Overview` text NOT NULL,
        `Genre_ids` int(50) NOT NULL,
        `Popularity` double NOT NULL,
        `Vote_average` double NOT NULL,
        `Origin_country` varchar(10) NOT NULL,
        `Vote_count` int(10) NOT NULL,
        `id` int(10) NOT NULL,
        PRIMARY KEY(`Name`)
        );
        CREATE TABLE `Usuarios` (
        `Correo_electronico` varchar(30) NOT NULL,
        `Nombre_Usuario` varchar(30) NOT NULL,
        `Contraseña` varchar(30) NOT NULL,
        `Telefono` varchar(30) NOT NULL,
        `Recibir_info` tinyint(1) NOT NULL,
        PRIMARY KEY (`Correo_electronico`)
        );

        CREATE TABLE Series_Usuarios (
        `Correo_electronico` varchar(30) NOT NULL,
        `Name` varchar(50) NOT NULL,

        PRIMARY KEY(`Correo_electronico`, `Name`),
        INDEX (`Correo_electronico`),
        INDEX (`Name`),

        FOREIGN KEY (`Correo_electronico`)
        REFERENCES usuarios(`Correo_electronico`)
        ON UPDATE CASCADE ON DELETE RESTRICT,

        FOREIGN KEY (`Name`)
        REFERENCES Series(`Name`)
        )*/
public class Series {
    private Connection conexion = null;
    private ArrayList<Serie> series;

    public Series() throws SQLException {
        series = new ArrayList<>();
        conexion = DriverManager.getConnection("jdbc:mysql://localhost/allenflix", "root", "");
        rellenarSeries();
    }

    private void rellenarSeries() {
        for (int j = 1; j <= 200; j++) {
            String linkUrl = "https://api.themoviedb.org/3/tv/popular?api_key=4ef66e12cddbb8fe9d4fd03ac9632f6e&language=en-US&page=" + j;
            URL url = null;
            try {
                url = new URL(linkUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader lector = new BufferedReader
                        (new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String linea;
                while ((linea = lector.readLine()) != null) {
                    builder.append(linea);
                }
                JSONObject jsonObject = new JSONObject(builder.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    Gson gson = new Gson();
                    Serie s = gson.fromJson(object.toString(), Serie.class);
                    Create(s);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int Create(Serie e) {
        int filas = 0;
        try {
            String sql = "INSERT INTO `series`(`Name`, `Original_name`, `First_air_date`, `Backdrop_path`, `Original_language`, `Poster_path`," +
                    " `Overview`, `Genre_ids`, `Popularity`, `Vote_average`,`Origin_country`, `Vote_count`, `id`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement insert = conexion.prepareStatement(sql);
            insert.setString(1, e.getOriginal_name());
            insert.setString(2, e.getName());
            insert.setString(3, e.getFirst_air_date());
            insert.setString(4, e.getBackdrop_path());
            insert.setString(5, e.getOriginal_language());
            insert.setString(6, e.getPoster_path());
            insert.setString(7, e.getOverview());
            try {
                insert.setInt(8, e.getGenre_ids()[0]);
            } catch (Exception ex) {
                insert.setInt(8, 0);
            }
            insert.setDouble(9, e.getPopularity());
            insert.setDouble(10, e.getVote_average());
            try {
                insert.setString(11, e.getOrigin_country()[0]);
            } catch (Exception ex) {
                insert.setString(11, "Without information");
            }
            insert.setInt(12, e.getVote_count());
            insert.setInt(13, e.getId());
            filas = insert.executeUpdate();
        } catch (SQLException ex) {

        }
        return filas;
    }

    public ArrayList<Serie> Read() {


        Serie p = null;
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement("SELECT * FROM series");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String Original_name = rs.getString("Original_name");
                String Name = rs.getString("Name");
                String First_air_date = rs.getString("First_air_date");
                String Backdrop_path = rs.getString("Backdrop_path");
                String Original_language = rs.getString("Original_language");
                String Poster_path = rs.getString("Poster_path");
                String Overview = rs.getString("Overview");
                int[] Genre_ids = {rs.getInt("Genre_ids")};
                double Popularity = rs.getDouble("Popularity");
                double Vote_average = rs.getDouble("Vote_average");
                String[] Origin_country = {rs.getString("Origin_country")};
                int Vote_count = rs.getInt("Vote_count");
                int Id = rs.getInt("Id");
                p = new Serie(Original_name, Name, First_air_date, Backdrop_path, Original_language, Poster_path, Overview, Genre_ids, Popularity, Vote_average, Origin_country, Vote_count, Id);
                series.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.series;
    }
}
