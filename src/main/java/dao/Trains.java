package dao;

import domain.Train;

import java.util.ArrayList;
import java.util.List;

public class Trains {

    private static List<Train> trains;

    public Trains() {
        this.trains = new ArrayList<>();
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void add(Train train) {
        this.trains.add(train);
    }
}
