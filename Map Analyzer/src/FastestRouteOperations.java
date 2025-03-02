import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * The FastestRouteOperations class contains methods for operations related to finding the fastest route in the original map.
 * It includes methods for writing the shortest way, calculating distance from start, and finding the fastest route.
 */
public class FastestRouteOperations {
    // Variables with required data types.

    public static ArrayList<Route> routesOfFR = new ArrayList<>();
    public static HashMap<String, Route> shortestWay = new HashMap<>();
    public static ArrayList<Route> exploredRoutesOfFR = new ArrayList<>();
    public static ArrayList<String> exploredPointsOfFR = new ArrayList<>();
    public static ArrayList<Integer> fastestRouteDistances = new ArrayList<>();

    /**
     * This method writes the shortest way from the start point to the finish point.
     * It calculates the fastest route distance and writes it to a file.
     * It also writes the path of the fastest route to the file.
     *
     * @param finishPoint          The finishing point of the original map.
     * @param startPoint           The starting point of the original map.
     * @param isBarelyConnectedMap A boolean indicating if the map is barely connected.
     * @param args                 The command line arguments.
     */
    public static void writeShortestWay(String finishPoint, String startPoint, boolean isBarelyConnectedMap, String[] args) {
        if (!isBarelyConnectedMap) { // If the map is not barely connected, it writes according to its output style.
            int fastestRouteDistanceOnOriginalMap = shortestWay.get(finishPoint).getDistanceFromStart();
            fastestRouteDistances.add(fastestRouteDistanceOnOriginalMap);
            FileOutput.writeToFile(args[1], "Fastest Route from " + startPoint + " to " + finishPoint + " (" + fastestRouteDistanceOnOriginalMap + " KM):", true, true);
        } else { // If the map is barely connected, it writes according to its output style.
            int fastestRouteDistanceOnBarelyConnectedMap = shortestWay.get(finishPoint).getDistanceFromStart();
            fastestRouteDistances.add(fastestRouteDistanceOnBarelyConnectedMap);
            FileOutput.writeToFile(args[1], "Fastest Route from " + startPoint + " to " + finishPoint + " on Barely Connected Map (" + fastestRouteDistanceOnBarelyConnectedMap + " KM):", true, true);
        }
        ArrayList<Road> path = new ArrayList<>();
        while (!finishPoint.equals(startPoint)) { // It iterates over the shortest way and adds the roads for create the path of the fastest route.
            path.add(shortestWay.get(finishPoint).getRoad());
            String previousPoint = (shortestWay.get(finishPoint).getFirstPoint().equals(finishPoint)) ? shortestWay.get(finishPoint).getSecondPoint() : shortestWay.get(finishPoint).getFirstPoint();
            finishPoint = previousPoint;
        }
        Collections.reverse(path); // It reverses the path to write from start to finish.
        for (Road road : path) {
            FileOutput.writeToFile(args[1], road.getNameOfRoad() + "\t" + road.getDistanceOfRoad() + "\t" + road.getIdOfRoad(), true, true);
        }
    }


    /**
     * This method calculates the distance from the start point to a given point.
     *
     * @param firstPoint  The first point of the road.
     * @param secondPoint The second point of the road.
     * @param road        The road object.
     * @return The distance from the start point to the given point.
     */
    public static int distanceFromStart(String firstPoint, String secondPoint, Road road) {
        //  It checks if the given points are in the shortestWay and returns the distance accordingly.
        if (shortestWay.containsKey(firstPoint)) {
            return shortestWay.get(firstPoint).getDistanceFromStart() + road.getDistanceOfRoad();
        } else if (shortestWay.containsKey(secondPoint)) {
            return shortestWay.get(secondPoint).getDistanceFromStart() + road.getDistanceOfRoad();
        } else {
            return road.getDistanceOfRoad(); //If it is a road connected to the starting point.
        }
    }

    /**
     * This method finds the fastest route from the starting point to the finishing point.
     * It uses Dijkstra's algorithm to find the shortest path.
     * It writes the shortest way to the output file.
     *
     * @param allRoads             An array of strings where each string represents a road from input.
     * @param isBarelyConnectedMap A boolean indicating if the map is barely connected.
     * @param args                 The command line arguments for writing.
     */
    public static void findTheFastestRoute(String[] allRoads, boolean isBarelyConnectedMap, String[] args) {
        // It clears the lists and maps to find a new the fastest route.
        routesOfFR.clear();
        exploredRoutesOfFR.clear();
        exploredPointsOfFR.clear();
        shortestWay.clear();
        String startPoint = allRoads[0].split("\t")[0];
        String finishPoint = allRoads[0].split("\t")[1];
        String currentPoint = startPoint;
        do {
            ArrayList<Route> tempRoutes = new ArrayList<>(); // It creates a temporary list of routes connected current point.
            outerLoop:
            for (int i = 1; i < allRoads.length; i++) { // It iterates over the roads to find the roads connected to the current point.
                String[] roadInfo = allRoads[i].split("\t");
                String nameOfRoad = roadInfo[0] + "\t" + roadInfo[1];
                int distanceOfRoad = Integer.parseInt(roadInfo[2]);
                int idOfRoad = Integer.parseInt(roadInfo[3]);
                Road road = new Road(nameOfRoad, distanceOfRoad, idOfRoad); // It creates a road object.
                String firstPoint = roadInfo[0];
                String secondPoint = roadInfo[1];
                if (currentPoint.equals(firstPoint) || currentPoint.equals(secondPoint)) {
                    String nextPoint = firstPoint.equals(currentPoint) ? secondPoint : firstPoint;
                    Route route = new Route(distanceFromStart(firstPoint, secondPoint, road), road, currentPoint, nextPoint); // It creates a route object.
                    for (Route exploredRoute : exploredRoutesOfFR) { // It checks if the route is already explored.
                        if (exploredRoute.getRoad().getIdOfRoad() == route.getRoad().getIdOfRoad()) {
                            continue outerLoop;
                        }
                    }
                    tempRoutes.add(route); // If the route is not explored, it adds the route connected with current point to the temp list of routes.
                }
            }
            tempRoutes.sort(Comparator.comparingInt(Route::getDistanceFromStart) // It sorts by distance of starting point first, then by ID.
                    .thenComparing(route -> route.getRoad().getIdOfRoad()));
            for (Route tempRoute : tempRoutes) {   // It adds the routes connected with current point to the general list of routes.
                routesOfFR.add(tempRoute);
                exploredRoutesOfFR.add(tempRoute);
            }
            exploredPointsOfFR.add(currentPoint); // It adds the current point to the list of explored points.
            routesOfFR.sort(Comparator.comparingInt(Route::getDistanceFromStart)); // It sorts the list of routes by distance from starting point of routes.

            String nextPoint = exploredPointsOfFR.contains(routesOfFR.get(0).getFirstPoint()) ? routesOfFR.get(0).getSecondPoint() : routesOfFR.get(0).getFirstPoint();
            if (shortestWay.containsKey(nextPoint)) { // It checks if the next point is already in shortestWay Hash Map and updates the distance if necessary.
                if (shortestWay.get(nextPoint).getDistanceFromStart() > routesOfFR.get(0).getDistanceFromStart()) {
                    shortestWay.replace(nextPoint, routesOfFR.get(0));
                }
            } else {
                shortestWay.put(nextPoint, routesOfFR.get(0)); // If the next point is not in shortestWay, it adds the next point with its route object value to shortestWay.
            }
            routesOfFR.remove(0); // It removes the explored route from the list of routes.
            currentPoint = nextPoint;
        } while (!currentPoint.equals(finishPoint)); // It continues until the finishing point is reached.
        writeShortestWay(finishPoint, startPoint, isBarelyConnectedMap, args); // It writes the shortest way to the output file.
    }
}
