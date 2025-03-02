import java.util.ArrayList;
import java.util.Locale;

/**
 * Main class of the program.
 * This class contains the main method of the program.
 * It reads the input file, finds the fastest routes and solves the barely connected map.
 */
public class MapAnalyzer {

    /**
     * Main method of the program.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        FileOutput.writeToFile(args[1], "", false, false); //Initialize the output file.
        String[] allRoads = FileInput.readFile(args[0], false, false); //Read the input file.
        FastestRouteOperations.findTheFastestRoute(allRoads, false, args); //It finds the fastest route on original map and writes to output file.
        ArrayList<String> sortedPoints = BarelyConnectedMapOperations.sortPoints(allRoads); //Sort the points at the original map alphabetically.
        BarelyConnectedMapOperations.solveTheBarelyConnectedMap(allRoads, sortedPoints.get(0), sortedPoints, args); //It solves the barely connected map and writes to output file.
    }

}