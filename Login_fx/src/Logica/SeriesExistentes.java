package Logica;

import java.io.Serializable;
import java.util.ArrayList;

public class SeriesExistentes implements Serializable {
    ArrayList<Serie> series;

    public SeriesExistentes(ArrayList<Serie> series) {
        this.series = series;
    }

    public ArrayList<Serie> getSeries() {
        return series;
    }

    public void setSeries(ArrayList<Serie> series) {
        this.series = series;
    }
}
