package roadgraph;

import geography.GeographicPoint;

import java.util.HashSet;
import java.util.Set;

public class NodeGraph {
    private GeographicPoint location;
    private HashSet<MapEdge> edges;


    public NodeGraph(GeographicPoint location) {
        this.location = location;
        edges = new HashSet<MapEdge>();
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
}
