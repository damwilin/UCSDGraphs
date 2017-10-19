package roadgraph;

import geography.*;

import java.util.ArrayList;
import java.util.List;

public class NodeGraph {
    private GeographicPoint location;
    private List<RoadSegment> roadSegments;


    public NodeGraph(GeographicPoint location) {
        this.location = location;
        roadSegments = new ArrayList<RoadSegment>();
    }

    /**
     * Return location for NodeGraph
     *
     * @return location
     */
    public GeographicPoint getLocation() {
        return location;
    }

    /**
     * Return list of RoadSegments.
     *
     * @return List<RoadSegment>
     */
    public List<RoadSegment> getRoadSegments() {
        return roadSegments;
    }

    /**
     * Return size of roadSegments list
     *
     * @return size int
     */
    public int getNumSegments() {
        return roadSegments.size();
    }

    /**
     * Create and add new RoadSegment to roadSegments list.
     *
     * @param to       localization of road end
     * @param roadName name of road
     * @param roadType type of road
     * @param length   length of road
     */
    public void AddRoadSegment(GeographicPoint to, String roadName, String roadType, double length) {
        List<GeographicPoint> geometry = new ArrayList<GeographicPoint>();
        geometry.add(location);
        geometry.add(to);
        RoadSegment newRS = new RoadSegment(location, to, geometry, roadName, roadType, length);
        roadSegments.add(newRS);
    }

    /**
     * Create list of neighbors from roadSegments list
     *
     * @return List of neighbors
     */
    public List<GeographicPoint> getNeighborsList() {
        List<GeographicPoint> neighbors = new ArrayList<GeographicPoint>();
        for (RoadSegment roadSegment : getRoadSegments()) {
            neighbors.add(roadSegment.getOtherPoint(location));
        }
        return neighbors;
    }
}
