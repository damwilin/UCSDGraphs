package roadgraph;

/**
 * Created by Damian on 10/23/2017.
 */
public class MapEdge implements Comparable<MapEdge> {
    private String roadName;
    private String roadType;
    private NodeGraph start;
    private NodeGraph end;
    private double length;

    public MapEdge(String roadName, String roadType, NodeGraph start, NodeGraph end) {
        this.roadName = roadName;
        this.roadType = roadType;
        this.start = start;
        this.end = end;
    }


    public MapEdge(String roadName, String roadType, NodeGraph start, NodeGraph end, double length) {
        this.roadName = roadName;
        this.roadType = roadType;
        this.start = start;
        this.end = end;
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    NodeGraph getOtherNode(NodeGraph node) {
        if (node.equals(start))
            return end;
        else if (node.equals(end))
            return start;
        throw new IllegalArgumentException("This node is not in the edge");
    }

    @Override
    public int compareTo(MapEdge o) {
        return Double.compare(this.getLength(), o.getLength());
    }
}
