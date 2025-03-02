/**
 * The class represents a road in the original map.
 * It contains the name of the road, the distance of the road, and the ID of the road.
 */
public class Road {
    private String nameOfRoad;
    private int distanceOfRoad;
    private int idOfRoad;

    /**
     * Constructs a Road object with the given parameters.
     *
     * @param nameOfRoad     The name of the road.
     * @param distanceOfRoad The distance of the road.
     * @param idOfRoad       The ID of the road.
     */
    public Road(String nameOfRoad, int distanceOfRoad, int idOfRoad) {
        this.nameOfRoad = nameOfRoad;
        this.distanceOfRoad = distanceOfRoad;
        this.idOfRoad = idOfRoad;
    }

    //Getters and Setters
    public String getNameOfRoad() {
        return nameOfRoad;
    }

    public int getDistanceOfRoad() {
        return distanceOfRoad;
    }

    public int getIdOfRoad() {
        return idOfRoad;
    }

}
