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

    public GeographicPoint getLocation() {
        return location;
    }

    public List<RoadSegment> getRoadSegments() {
        return roadSegments;
    }

}
