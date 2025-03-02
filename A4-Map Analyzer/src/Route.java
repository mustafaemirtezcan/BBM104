/**
 * The class represents a route in the original map.
 * It contains the distance from the start point, the road, and the first and second points of the road.
 */
public class Route {
    private int distanceFromStart;
    private Road road;
    private String firstPoint;
    private String secondPoint;

    /**
     * Constructs a Route object with the given parameters.
     *
     * @param distanceFromStart The distance from the start pointing of route object.
     * @param road              The road object.
     * @param firstPoint        The first point of the road.
     * @param secondPoint       The second point of the road.
     */
    public Route(int distanceFromStart, Road road, String firstPoint, String secondPoint) {
        this.distanceFromStart = distanceFromStart;
        this.road = road;
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;

    }

    //Getters and Setters.

    public Road getRoad() {
        return road;
    }

    public String getFirstPoint() {
        return firstPoint;
    }

    public String getSecondPoint() {
        return secondPoint;
    }

    public int getDistanceFromStart() {
        return distanceFromStart;
    }

}
