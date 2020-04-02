package utils;

import dao.Routes;
import dao.Stations;
import domain.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This class finds the shortest path between all the source and destination stations
 */
public class ConstructRouteMapUtil {

    private static final int NO_PARENT = -1;

    /**
     * We have used dijkstra algorithm to find the shortest path here
     * @param adjacencyMatrix
     * @param startVertex
     * @param hm
     */
    private void calculateShortestPath(int[][] adjacencyMatrix, int startVertex, HashMap<String, HashMap<String, ArrayList<String>>> hm) {
        int nVertices = adjacencyMatrix[0].length;
        int[] shortestDistances = new int[nVertices];
        boolean[] added = new boolean[nVertices];

        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        shortestDistances[startVertex] = 0;
        int[] parents = new int[nVertices];
        parents[startVertex] = NO_PARENT;

        for (int i = 1; i < nVertices; i++) {
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }
            added[nearestVertex] = true;

            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];
                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                            edgeDistance;
                }
            }
        }
        addSolution(startVertex, shortestDistances, parents, hm);
    }

    private void addSolution(int startVertex, int[] distances, int[] parents,
                             HashMap<String, HashMap<String, ArrayList<String>>> hm) {
        int nVertices = distances.length;
        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
            if (vertexIndex != startVertex) {
                HashMap<String, ArrayList<String>> newEntry = hm.getOrDefault(String.valueOf(startVertex), new HashMap<>());
                newEntry.put(String.valueOf(vertexIndex), new ArrayList<>());
                hm.put(String.valueOf(startVertex), newEntry);
                addPath(vertexIndex, parents, hm, startVertex, vertexIndex);
            }
        }
    }

    private void addPath(int currentVertex, int[] parents, HashMap<String, HashMap<String, ArrayList<String>>> hm,
                         int source, int dest) {
        if (currentVertex == NO_PARENT)
            return;
        addPath(parents[currentVertex], parents, hm, source, dest);
        hm.get(String.valueOf(source)).get(String.valueOf(dest)).add(String.valueOf(currentVertex));
    }

    public HashMap<String, HashMap<String, ArrayList<String>>> constructCompleteRouteMap(Routes routes, Stations stations) {
        int[][] mat = constructMatrix(routes, stations);
        HashMap<String, HashMap<String, ArrayList<String>>> hm = new HashMap<>();
        for (String station : stations.getStations()) {
            calculateShortestPath(mat, stations.getStations().indexOf(station), hm);
        }
        return replaceReferencesOfRoute(hm, stations);
    }

    private HashMap<String, HashMap<String, ArrayList<String>>> replaceReferencesOfRoute(HashMap<String, HashMap<String, ArrayList<String>>> hm, Stations stations) {
        HashMap<String, HashMap<String, ArrayList<String>>> newMap = new HashMap<>();

        for (Map.Entry<String, HashMap<String, ArrayList<String>>> map : hm.entrySet()) {
            String key = stations.getStations().get(Integer.valueOf(map.getKey()));
            HashMap<String, ArrayList<String>> stationsMap = new HashMap<>();
            for (Map.Entry<String, ArrayList<String>> entry : map.getValue().entrySet()) {
                String key2 = stations.getStations().get(Integer.valueOf(entry.getKey()));
                ArrayList<String> toStations = new ArrayList<>();
                for (String toStation : entry.getValue()) {
                    toStations.add(stations.getStations().get(Integer.valueOf(toStation)));
                }
                stationsMap.put(key2, toStations);
            }
            newMap.put(key, stationsMap);
        }
        return newMap;
    }

    private int[][] constructMatrix(Routes routes, Stations stations) {
        Map<String, List<Route>> routeMap = routes.getRouteMap();
        List<String> stationList = stations.getStations();
        int size = stationList.size();
        int[][] mat = new int[size][size];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                String source = stationList.get(i);
                String dest = stationList.get(j);
                Optional<Route> optionalOfRoute = routeMap.get(source).stream()
                        .filter(x -> x.getToStation().equals(dest))
                        .findAny();
                if (optionalOfRoute.isPresent()) {
                    Route route = optionalOfRoute.get();
                    mat[i][j] = route.getCost();
                } else {
                    mat[i][j] = 0;
                }
            }
        }
        return mat;
    }
}
