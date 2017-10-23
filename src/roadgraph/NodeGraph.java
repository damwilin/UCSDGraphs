package roadgraph;

import geography.GeographicPoint;

import java.util.HashSet;
import java.util.Set;

public class NodeGraph implements Comparable<NodeGraph> {
    private GeographicPoint location;
    private HashSet<MapEdge> edges;
    private double distanceFromStart;
    private double DEFAULT_DISTANCE = Double.POSITIVE_INFINITY;


    public NodeGraph(GeographicPoint location) {
        this.location = location;
        edges = new HashSet<MapEdge>();
        distanceFromStart = DEFAULT_DISTANCE;
    }

    /**
     * Return location for NodeGraph
     *
     * @return location
     */
    public GeographicPoint getLocation() {
        return location;
    }

    public Set<MapEdge> getEdges() {
        return edges;
    }

    public Set<NodeGraph> getNeighbors() {
        Set<NodeGraph> neighborsSet = new HashSet<NodeGraph>();
        for (MapEdge currEdge : edges) {
            neighborsSet.add(currEdge.getOtherNode(this));
        }
        return neighborsSet;
    }

    public void addEdge(MapEdge nEdge) {
        edges.add(nEdge);
    }

    public double getDistanceFromStart() {
        return distanceFromStart;
    }

    public void setDistanceFromStart(double distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    public double getDistanceTo(NodeGraph o) {
        return this.getLocation().distance(o.getLocation());
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NodeGraph) || obj == null)
            return false;
        NodeGraph node = (NodeGraph) obj;
        return node.location.equals(this.location);
    }

    @Override
    public int compareTo(NodeGraph o) {
        return Double.compare(this.distanceFromStart, o.distanceFromStart);
    }
}
