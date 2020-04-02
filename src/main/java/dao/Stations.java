package dao;

import java.util.ArrayList;
import java.util.List;

public class Stations {

    private static List<String> stations;

    public Stations() {
        this.stations = new ArrayList<>();
    }

    public void add(String station){
        if (!stations.contains(station)) {
            this.stations.add(station);
        }
    }

    public List<String> getStations() {
        return stations;
    }
}
