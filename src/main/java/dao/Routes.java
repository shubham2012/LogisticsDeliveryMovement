package dao;

import domain.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Routes {

    private static Map<String, List<Route>> routeMap;

    public Routes() {
        this.routeMap = new HashMap<>();
    }

    public Map<String, List<Route>> getRouteMap() {
        return routeMap;
    }

    /**
     * Add Route to DAO
     * @param route
     */
    public void add(Route route) {
        List<Route> routes = routeMap.get(route.getFromStation());
        if (Objects.isNull(routes)) {
            ArrayList<Route> list = new ArrayList<>();
            list.add(route);
            routeMap.put(route.getFromStation(), list);
        } else {
            List<Route> allRoutes = routeMap.get(route.getFromStation());
            allRoutes.add(route);
            routeMap.put(route.getFromStation(), allRoutes);
        }
    }

    /**
     * Calculate the cost of path between two connected stations
     * @param source
     * @param dest
     * @return
     */
    public int getCostFromSourceToDest(String source, String dest){
        Optional<Route> any = routeMap.get(source).stream()
                .filter(x -> x.getToStation().equals(dest))
                .findAny();
        if (any.isPresent())
            return any.get().getCost();
        else
            return 0;
    }

    /**
     * Calculate the complete cost of entire path from source to given destination
     * @param path
     * @return
     */
    public int getCompleteRouteCost(ArrayList<String> path){
        ListIterator<String> it = path.listIterator();
        String source = it.next();
        int cost = 0;
        while (it.hasNext()) {
            String dest = it.next();
            cost += getCostFromSourceToDest(source, dest);
            source = dest;
        }
        return cost;
    }
}
