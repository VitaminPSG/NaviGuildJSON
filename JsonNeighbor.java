public class JsonNeighbor {
    private int stopId;
    private int distance;

    public JsonNeighbor(int stopId, int distance) {
        this.stopId = stopId;
        this.distance = distance;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
