package controller;

import dao.Deliveries;
import dao.Routes;
import dao.Stations;
import dao.Trains;
import domain.MovementRequest;
import domain.Route;
import domain.Train;
import org.junit.jupiter.api.Test;

class MovementControllerTest {

    private MovementController controller = new MovementController();

    @Test
    void test_happy_flow() {
        Stations stations = new Stations();
        Routes routes = new Routes();
        Deliveries deliveries = new Deliveries();
        Trains trains = new Trains();
        stations.add("A");
        stations.add("B");
        stations.add("C");
        routes.add(new Route("A", "B", 3));
        routes.add(new Route("B", "A", 3));
        routes.add(new Route("B", "C", 1));
        routes.add(new Route("C", "B", 1));
        deliveries.add(new MovementRequest("D1", "A", "C", 5));
        trains.add(new Train("T1", "B", 6));
        controller.process(routes, stations, deliveries, trains);
    }

    @Test
    void test_happy_flow_with_multi_delivery() {
        Stations stations = new Stations();
        Routes routes = new Routes();
        Deliveries deliveries = new Deliveries();
        Trains trains = new Trains();
        stations.add("A");
        stations.add("B");
        stations.add("C");
        routes.add(new Route("A", "B", 3));
        routes.add(new Route("B", "A", 3));
        routes.add(new Route("B", "C", 1));
        routes.add(new Route("C", "B", 1));
        deliveries.add(new MovementRequest("D1", "A", "C", 5));
        deliveries.add(new MovementRequest("D2", "B", "A", 5));
        trains.add(new Train("T1", "B", 6));
        controller.process(routes, stations, deliveries, trains);
    }

    @Test
    void test_happy_flow_with_more_nodes_multi_delivery() {
        Stations stations = new Stations();
        Routes routes = new Routes();
        Deliveries deliveries = new Deliveries();
        Trains trains = new Trains();
        stations.add("A");
        stations.add("B");
        stations.add("C");
        stations.add("D");
        routes.add(new Route("A", "B", 3));
        routes.add(new Route("B", "A", 3));
        routes.add(new Route("B", "C", 1));
        routes.add(new Route("C", "B", 1));
        routes.add(new Route("B", "D", 4));
        routes.add(new Route("D", "B", 4));
        routes.add(new Route("D", "C", 3));
        routes.add(new Route("C", "D", 3));

        deliveries.add(new MovementRequest("D1", "A", "C", 5));
        deliveries.add(new MovementRequest("D2", "A", "D", 5));
        trains.add(new Train("T1", "B", 6));
        controller.process(routes, stations, deliveries, trains);
    }

    @Test
    void test_happy_multi_train() {
        Stations stations = new Stations();
        Routes routes = new Routes();
        Deliveries deliveries = new Deliveries();
        Trains trains = new Trains();
        stations.add("A");
        stations.add("B");
        stations.add("C");
        routes.add(new Route("A", "B", 3));
        routes.add(new Route("B", "A", 3));
        routes.add(new Route("B", "C", 1));
        routes.add(new Route("C", "B", 1));
        trains.add(new Train("T1", "B", 6));
        trains.add(new Train("T2", "A", 4));
        trains.add(new Train("T3", "A", 8));
        trains.add(new Train("T4", "C", 6));
        deliveries.add(new MovementRequest("D1", "A", "C", 5));
        controller.process(routes, stations, deliveries, trains);
    }

    @Test
    void test_happy_multi_train_multi_delivery() {
        Stations stations = new Stations();
        Routes routes = new Routes();
        Deliveries deliveries = new Deliveries();
        Trains trains = new Trains();
        stations.add("A");
        stations.add("B");
        stations.add("C");
        routes.add(new Route("A", "B", 3));
        routes.add(new Route("B", "A", 3));
        routes.add(new Route("B", "C", 1));
        routes.add(new Route("C", "B", 1));
        trains.add(new Train("T1", "B", 6));
        trains.add(new Train("T2", "A", 4));
        trains.add(new Train("T3", "A", 6));
        trains.add(new Train("T4", "C", 8));
        deliveries.add(new MovementRequest("D1", "A", "C", 5));
        deliveries.add(new MovementRequest("D2", "C", "A", 8));
        deliveries.add(new MovementRequest("D3", "B", "A", 4));
        controller.process(routes, stations, deliveries, trains);
    }
}