package dao;

import domain.MovementRequest;

import java.util.ArrayList;
import java.util.List;

public class Deliveries {
    private static List<MovementRequest> delivery;

    public Deliveries() {
        delivery = new ArrayList<>();
    }

    public List<MovementRequest> getDelivery() {
        return delivery;
    }

    public void add(MovementRequest request){
        delivery.add(request);
    }
}
