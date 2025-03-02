import java.util.ArrayList;

/**
 * The class contains a method to analyze the results.
 */
public class Analysis {

    /**
     * Analyzes the results It finds ratioOfDistanceBetweenBarelyConnectedAndOriginalMap and ratioOfFastestRouteBetweenBarelyConnectedAndOriginalMap values. It writes them to output file.
     *
     * @param allRoads         The roads of the original map.
     * @param listOfRoadForBCM The list of roads for the barely connected map.
     * @param args             The command line arguments.
     */
    public static void analyze(String[] allRoads, ArrayList<Route> listOfRoadForBCM, String[] args) {
        double totalDistanceOfOriginalRoads = 0; // Total distance of original roads
        for (int i = 1; i < allRoads.length; i++) {
            int distanceOfRoad = Integer.parseInt(allRoads[i].split("\t")[2]);
            totalDistanceOfOriginalRoads += distanceOfRoad;
        }
        double totalDistanceOfBarelyConnectedMap = 0; // Total distance of barely connected map.
        for (Route route : listOfRoadForBCM) {
            totalDistanceOfBarelyConnectedMap += route.getRoad().getDistanceOfRoad();
        }
        double ratioOfDistanceBetweenBarelyConnectedAndOriginalMap = totalDistanceOfBarelyConnectedMap / totalDistanceOfOriginalRoads; // Ratio of distance between barely connected and original map.
        double ratioOfFastestRouteBetweenBarelyConnectedAndOriginalMap = (double) FastestRouteOperations.fastestRouteDistances.get(1) / (double) FastestRouteOperations.fastestRouteDistances.get(0); // Ratio of fastest route between barely connected and original map.
        FileOutput.writeToFile(args[1], String.format("Analysis:\nRatio of Construction Material Usage Between Barely Connected and Original Map: %.2f", ratioOfDistanceBetweenBarelyConnectedAndOriginalMap), true, true);
        FileOutput.writeToFile(args[1], String.format("Ratio of Fastest Route Between Barely Connected and Original Map: %.2f", ratioOfFastestRouteBetweenBarelyConnectedAndOriginalMap), true, false);
    }
}