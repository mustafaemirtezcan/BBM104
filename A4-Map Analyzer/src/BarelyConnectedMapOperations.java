import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;

/**
 * The BarelyConnectedMapOperations class contains methods for operations related to the Barely Connected Map.
 * It includes methods for sorting points, calculating distance from start, writing the Barely Connected Map,
 * finding and writing the fastest route on the Barely Connected Map, and solving the Barely Connected Map.
 */
public class BarelyConnectedMapOperations {
    // Variables with required data types.
    public static ArrayList<Route> routesOfBCM = new ArrayList<>();
    public static ArrayList<Route> exploredRoutesOfBCM = new ArrayList<>();
    public static ArrayList<String> exploredPointsOfBCM = new ArrayList<>();
    public static HashMap<String, Route> barelyConnectedMap = new HashMap<>();

    /**
     * It sorts all points in the original map alphabetically.
     *
     * @param allRoads An array of strings where each string represents a road from input.
     * @return Alphabetically  sorted list of points in the road network.
     */
    public static ArrayList<String> sortPoints(String[] allRoads) {
        // It iterates over all roads, splits the road information to get the points, and adds them to a list if they are not already present.
        ArrayList<String> points = new ArrayList<>();
        for (int i = 1; i < allRoads.length; i++) {
            for (int j = 0; j < 2; j++) {
                String point = allRoads[i].split("\t")[j];
                if (!points.contains(point)) {
                    points.add(point);
                }
            }
        }
        Collections.sort(points);//Finally, it sorts the list of points and returns it.
        return points;
    }

    /**
     * It calculates the distance from the start point to a given point.
     *
     * @param firstPoint  The first point of the road.
     * @param secondPoint The second point of the road.
     * @param road        The road object.
     * @return The distance from the start point to the given point.
     */
    public static int distanceFromStart(String firstPoint, String secondPoint, Road road) {
        //  It checks if the given points are in the barelyConnectedMap and returns the distance accordingly.
        if (barelyConnectedMap.containsKey(firstPoint)) {
            return barelyConnectedMap.get(firstPoint).getDistanceFromStart() + road.getDistanceOfRoad();
        } else if (barelyConnectedMap.containsKey(secondPoint)) {
            return barelyConnectedMap.get(secondPoint).getDistanceFromStart() + road.getDistanceOfRoad();
        } else {
            return road.getDistanceOfRoad(); //If it is a road connected to the starting point.
        }
    }

    /**
     * It writes the information of barely connected map to the output file.
     *
     * @param allRoads    An array of strings where each string represents a road from input.
     * @param startPoint  The starting point from original map to find the fastest route on barely connected map.
     * @param finishPoint The finishing point from original map to find the fastest route on barely connected map.
     * @param args        The command line arguments for writing.
     */
    public static void writeBarelyConnectedMap(String[] allRoads, String startPoint, String finishPoint, String[] args) {
        FileOutput.writeToFile(args[1], "Roads of Barely Connected Map is:", true, true);
        ArrayList<Route> listOfRoadForBCM = new ArrayList<>(); // It creates a list of roads for barely connected map.
        listOfRoadForBCM.addAll(barelyConnectedMap.values()); // It adds all values of barelyConnectedMap to the list.
        listOfRoadForBCM.sort(Comparator.comparingInt((Route route) -> route.getRoad().getDistanceOfRoad())
                .thenComparingInt(route -> route.getRoad().getIdOfRoad()));
        for (Route route : listOfRoadForBCM) {  // It iterates over the list of roads and writes the information of the road to the output file.
            Road road = route.getRoad();
            String roadInfo = road.getNameOfRoad() + "\t" + road.getDistanceOfRoad() + "\t" + road.getIdOfRoad();
            FileOutput.writeToFile(args[1], roadInfo, true, true);
        }
        findAndWriteTheFastestRouteOnBarelyConnectedMap(listOfRoadForBCM, startPoint, finishPoint, args); // It finds and writes the fastest route on barely connected map.
        Analysis.analyze(allRoads, listOfRoadForBCM, args); // It analyzes all results.
    }

    /**
     * It finds and writes the fastest route on barely connected map.
     *
     * @param listOfRoadForBCM The list of roads for barely connected map.
     * @param startPoint       The starting point from original map to find the fastest route on barely connected map.
     * @param finishPoint      The finishing point from original map to find the fastest route on barely connected map.
     * @param args             The command line arguments for writing.
     */
    public static void findAndWriteTheFastestRouteOnBarelyConnectedMap(ArrayList<Route> listOfRoadForBCM, String startPoint, String finishPoint, String[] args) {
        String[] barelyConnectedMapArray = new String[listOfRoadForBCM.size() + 1];
        barelyConnectedMapArray[0] = startPoint + "\t" + finishPoint;
        for (int i = 0; i < listOfRoadForBCM.size(); i++) { // It iterates over the list of roads and adds the information of the road to the array for  the input style of the findTheFastestRoute method .
            Road road = listOfRoadForBCM.get(i).getRoad();
            String roadInfo = road.getNameOfRoad() + "\t" + road.getDistanceOfRoad() + "\t" + road.getIdOfRoad();
            barelyConnectedMapArray[i + 1] = roadInfo;
        }
        FastestRouteOperations.findTheFastestRoute(barelyConnectedMapArray, true, args); // It finds the fastest route on barely connected map.
    }

    /**
     * It solves the barely connected map.
     *
     * @param allRoads     An array of strings where each string represents a road from input.
     * @param currentPoint The current point to find the next point.
     * @param points       The list of points in the original map.
     * @param args         The command line arguments for writing.
     */
    public static void solveTheBarelyConnectedMap(String[] allRoads, String currentPoint, ArrayList<String> points, String[] args) {
        String startPoint = allRoads[0].split("\t")[0];
        String finishPoint = allRoads[0].split("\t")[1];
        do {
            outerLoop:
            for (int i = 1; i < allRoads.length; i++) {  // It iterates over all roads and finds the roads connected to the current point.
                String[] roadInfo = allRoads[i].split("\t");
                String nameOfRoad = roadInfo[0] + "\t" + roadInfo[1];
                int distanceOfRoad = Integer.parseInt(roadInfo[2]);
                int idOfRoad = Integer.parseInt(roadInfo[3]);
                Road road = new Road(nameOfRoad, distanceOfRoad, idOfRoad);
                String firstPoint = roadInfo[0];
                String secondPoint = roadInfo[1];
                if (currentPoint.equals(firstPoint) || currentPoint.equals(secondPoint)) {
                    String nextPoint = firstPoint.equals(currentPoint) ? secondPoint : firstPoint;
                    Route route = new Route(distanceFromStart(firstPoint, secondPoint, road), road, currentPoint, nextPoint);
                    for (Route exploredRoute : exploredRoutesOfBCM) { // It checks if the route is already explored.
                        if (exploredRoute.getRoad().getIdOfRoad() == route.getRoad().getIdOfRoad()) {
                            continue outerLoop;
                        }
                    }
                    if (exploredPointsOfBCM.contains(currentPoint) && exploredPointsOfBCM.contains(nextPoint)) { // It checks  if both 2 points are discovered to prevent cycles from occurring.
                        continue;
                    }
                    routesOfBCM.add(route); // If the route is not explored, it adds the route connected with current point to the list of routes.
                    exploredRoutesOfBCM.add(route);
                }
            }
            exploredPointsOfBCM.add(currentPoint);
            routesOfBCM.sort(Comparator.comparingInt((Route route) -> route.getRoad().getDistanceOfRoad()) // It sorts by distance of road first, then by ID.
                    .thenComparingInt(route -> route.getRoad().getIdOfRoad()));
            String nextPoint = exploredPointsOfBCM.contains(routesOfBCM.get(0).getFirstPoint()) ? routesOfBCM.get(0).getSecondPoint() : routesOfBCM.get(0).getFirstPoint(); // It finds the next point on road to explore.
            if (barelyConnectedMap.containsKey(nextPoint)) {  // It checks if the next point is already in barelyConnectedMap and updates the distance if necessary.
                if (barelyConnectedMap.get(nextPoint).getRoad().getDistanceOfRoad() > routesOfBCM.get(0).getRoad().getDistanceOfRoad()) {
                    barelyConnectedMap.replace(nextPoint, routesOfBCM.get(0));
                }
            } else { // If the next point is not in barelyConnectedMap, it adds the next point to barelyConnectedMap.
                barelyConnectedMap.put(nextPoint, routesOfBCM.get(0));
            }
            routesOfBCM.remove(0); // It removes the explored route from the list of routes.
            currentPoint = nextPoint;
        } while (!(barelyConnectedMap.size() == points.size() - 1)); // It continues until all points are explored.
        writeBarelyConnectedMap(allRoads, startPoint, finishPoint, args); // It writes the barely connected map to the output file.

    }
}
