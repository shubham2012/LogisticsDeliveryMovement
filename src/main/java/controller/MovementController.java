package controller;

import dao.Deliveries;
import dao.Routes;
import dao.Stations;
import dao.Trains;
import domain.MovementRequest;
import domain.Route;
import domain.Train;
import org.apache.commons.lang3.tuple.Pair;
import utils.ConstructRouteMapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;

public class MovementController {

    public static void main(String[] args) {
        MovementController controller = new MovementController();
        Stations stations = new Stations();
        Routes routes = new Routes();
        Scanner scanner = new Scanner(System.in);
        Deliveries deliveries = new Deliveries();
        Trains trains = new Trains();

        readInputFromCommandLine(stations, routes, scanner, deliveries, trains);
        controller.process(routes, stations, deliveries, trains);
    }

    /**
     * Reading input from command line and constructing objects and then add it to DAO
     *
     * @param stations
     * @param routes
     * @param scanner
     * @param deliveries
     * @param trains
     */
    private static void readInputFromCommandLine(Stations stations, Routes routes, Scanner scanner, Deliveries deliveries, Trains trains) {
        Integer numberOfStations = Integer.valueOf(scanner.nextLine());
        for (int i = 0; i < numberOfStations; i++) {
            stations.add(scanner.nextLine());
        }
        Integer numberOfRoutes = Integer.valueOf(scanner.nextLine());
        for (int i = 0; i < numberOfRoutes; i++) {
            String[] split = scanner.nextLine().split(",");
            routes.add(new Route(split[0], split[1], Integer.valueOf(split[2])));
            routes.add(new Route(split[1], split[0], Integer.valueOf(split[2])));
        }
        Integer numberOfDeliveries = Integer.valueOf(scanner.nextLine());
        for (int i = 0; i < numberOfDeliveries; i++) {
            String[] split = scanner.nextLine().split(",");
            deliveries.add(new MovementRequest(split[0], split[1], split[2], Integer.valueOf(split[3])));
        }
        Integer numberOfTrains = Integer.valueOf(scanner.nextLine());
        for (int i = 0; i < numberOfTrains; i++) {
            String[] split = scanner.nextLine().split(",");
            trains.add(new Train(split[0], split[1], Integer.valueOf(split[2])));
        }
    }

    /**
     * Process composed requests for movement. This is the main method with business logic to process the request
     *
     * @param routes
     * @param stations
     * @param deliveries
     * @param trains
     */
    public void process(Routes routes, Stations stations, Deliveries deliveries, Trains trains) {
        int time = 0;
        boolean trainWasAtSource = false;
        ConstructRouteMapUtil constructRouteMapUtil = new ConstructRouteMapUtil();
        HashMap<String, HashMap<String, ArrayList<String>>> completeRoute =
                constructRouteMapUtil.constructCompleteRouteMap(routes, stations);
        for (MovementRequest movementRequest : deliveries.getDelivery()) {
            Train train = findTrain(movementRequest.getFrom(), movementRequest.getWight(), trains, routes, completeRoute);
            if (Objects.isNull(train)) {
                System.out.println("No trains available at this time");
                return;
            }
            ArrayList<String> sourceToDestPath = completeRoute.get(movementRequest.getFrom()).get(movementRequest.getTo());
            if (train.getCurrentStation() != movementRequest.getFrom()) {
                ArrayList<String> trainPath = completeRoute.get(train.getCurrentStation()).get(movementRequest.getFrom());
                train.setMoving(true);
                train.setAvailableCapacity(train.getCapacity() - movementRequest.getWight());
                time = moveTrainFromSourceToDest(trainPath, train, routes, time);
            } else {
                trainWasAtSource = true;
            }
            if (trainWasAtSource) {
                train.setMoving(true);
                train.setAvailableCapacity(train.getCapacity() - movementRequest.getWight());
            }
            System.out.println(String.format("current time -> %d | train %s Loaded %s",
                    time, train.getName(), movementRequest.getDelivery()));
            time = moveTrainFromSourceToDest(sourceToDestPath, train, routes, time);
            System.out.println(String.format("current time -> %d | train %s Unloaded %s",
                    time, train.getName(), movementRequest.getDelivery()));
            train.setCurrentStation(movementRequest.getTo());
            train.setAvailableCapacity(train.getAvailableCapacity() + movementRequest.getWight());
            train.setMoving(false);
            System.out.println();
        }
    }

    /**
     * Move train from given source to destination
     *
     * @param path
     * @param train
     * @param routes
     * @param time
     * @return
     */
    private int moveTrainFromSourceToDest(ArrayList<String> path, Train train, Routes routes, int time) {
        int currTime = 1;
        ListIterator<String> it = path.listIterator();
        String source = it.next();
        Map<Integer, Pair<String, String>> costSDMap = new LinkedHashMap<>();
        while (it.hasNext()) {
            String dest = it.next();
            costSDMap.put(routes.getCostFromSourceToDest(source, dest), Pair.of(source, dest));
            source = dest;
        }
        for (Map.Entry<Integer, Pair<String, String>> map : costSDMap.entrySet()) {
            Integer cost = map.getKey();
            Pair<String, String> value = map.getValue();
            for (int i = 0; i < cost; i++) {
                System.out.println(String.format("current time -> %d | train %s is moving %s -> %s (%s)",
                        time, train.getName(), value.getLeft(), value.getRight(), "" + currTime + "/" + cost));
                time++;
            }

            System.out.println(String.format("current time -> %d | train %s arrived at %s",
                    time, train.getName(), value.getRight()));
        }
        // here dest getting updated in the above code and dest so last value will be destination in source so
        // set current station as source
        train.setCurrentStation(source);
        return time;
    }

    /**
     * find nearest/ best cost train available to move the delivery and return the train
     *
     * @param source
     * @param deliverySize
     * @param trains
     * @param routes
     * @param completeRoute
     * @return
     */
    private Train findTrain(String source, int deliverySize, Trains trains, Routes routes,
                            HashMap<String, HashMap<String, ArrayList<String>>> completeRoute) {
        Optional<Train> optionalOfTrainAtSource = trains.getTrains().stream()
                .filter(x -> x.getCurrentStation() == source)
                .filter(x -> x.getCapacity() >= deliverySize && x.isMoving() == false)
                .findFirst();
        if (optionalOfTrainAtSource.isPresent()) {
            return optionalOfTrainAtSource.get();
        }
        TreeMap<Integer, Train> trainsWithCost = new TreeMap<>();
        for (Train train : trains.getTrains()) {
            if (train.isMoving() == false && train.getCapacity() >= deliverySize) {
                ArrayList<String> path = completeRoute.get(train.getCurrentStation()).get(source);
                int completeRouteCost = routes.getCompleteRouteCost(path);
                trainsWithCost.put(completeRouteCost, train);
            }
        }
        if (!trainsWithCost.isEmpty())
            return trainsWithCost.firstEntry().getValue();
        else return null;
    }

}
