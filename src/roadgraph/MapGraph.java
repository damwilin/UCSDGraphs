/**
 * @author UCSD MOOC development team and YOU
 * <p>
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between
 */
package roadgraph;


import geography.GeographicPoint;
import util.GraphLoader;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author UCSD MOOC development team and Damian Wiliński
 *         <p>
 *         A class which represents a graph of geographic locations
 *         Nodes in the graph are intersections between
 */
public class MapGraph {
    private List<NodeGraph> nodeList;


    /**
     * Create a new empty MapGraph
     */
    public MapGraph() {
        nodeList = new ArrayList<NodeGraph>();
    }

    public static void main(String[] args) {
        System.out.print("Making a new map...");
        MapGraph firstMap = new MapGraph();
        System.out.print("DONE. \nLoading the map...");
        GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
        System.out.println("DONE.");

        // You can use this method for testing.


		/* Here are some test cases you should try before you attempt
         * the Week 3 End of Week Quiz, EVEN IF you score 100% on the
		 * programming assignment.
		 */
        MapGraph simpleTestMap = new MapGraph();
        GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);

        GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
        GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);

        System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
        List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart, testEnd);
        List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart, testEnd);


        MapGraph testMap = new MapGraph();
        GraphLoader.loadRoadMap("data/maps/utc.map", testMap);

        // A very simple test using real data
        testStart = new GeographicPoint(32.869423, -117.220917);
        testEnd = new GeographicPoint(32.869255, -117.216927);
        System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
        testroute = testMap.dijkstra(testStart, testEnd);
        testroute2 = testMap.aStarSearch(testStart, testEnd);


        // A slightly more complex test using real data
        testStart = new GeographicPoint(32.8674388, -117.2190213);
        testEnd = new GeographicPoint(32.8697828, -117.2244506);
        System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
        testroute = testMap.dijkstra(testStart, testEnd);
        testroute2 = testMap.aStarSearch(testStart, testEnd);



		/* Use this code in Week 3 End of Week Quiz */
        /*MapGraph theMap = new MapGraph();
        System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);


		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/

    }

    /**
     * Get the number of vertices (road intersections) in the graph
     *
     * @return The number of vertices in the graph.
     */
    public int getNumVertices() {
        return nodeList.size();
    }

    /**
     * Return the intersections, which are the vertices in this graph.
     *
     * @return The vertices in this graph as GeographicPoints
     */
    public Set<GeographicPoint> getVertices() {
        Set<GeographicPoint> verticesSet = new HashSet<GeographicPoint>();
        for (NodeGraph currNode : nodeList) {
            verticesSet.add(currNode.getLocation());
        }
        return verticesSet;
    }

    /**
     * Get the number of road segments in the graph
     *
     * @return The number of edges in the graph.
     */
    public int getNumEdges() {
        int numEdges = 0;
        for (NodeGraph currNode : nodeList) {
            numEdges += currNode.getNumSegments();
        }
        return numEdges;
    }

    /**
     * Add a node corresponding to an intersection at a Geographic Point
     * If the location is already in the graph or null, this method does
     * not change the graph.
     *
     * @param location The location of the intersection
     * @return true if a node was added, false if it was not (the node
     * was already in the graph, or the parameter is null).
     */
    public boolean addVertex(GeographicPoint location) {
        if (nodeList.contains(location) || location == null)
            return false;
        nodeList.add(new NodeGraph(location));
        return true;
    }

    /**
     * Adds a directed edge to the graph from pt1 to pt2.
     * Precondition: Both GeographicPoints have already been added to the graph
     *
     * @param from     The starting point of the edge
     * @param to       The ending point of the edge
     * @param roadName The name of the road
     * @param roadType The type of the road
     * @param length   The length of the road, in km
     * @throws IllegalArgumentException If the points have not already been
     *                                  added as nodes to the graph, if any of the arguments is null,
     *                                  or if the length is less than 0.
     */
    public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
                        String roadType, double length) throws IllegalArgumentException {
        NodeGraph startNode = searchForNode(from);
        if (startNode != null)
            startNode.AddRoadSegment(to, roadName, roadType, length);

    }

    /**
     * Search for node from given location
     *
     * @param geographicPoint location to search for node.
     * @return
     */
    private NodeGraph searchForNode(GeographicPoint geographicPoint) {
        for (NodeGraph currNode : nodeList) {
            if (currNode.getLocation().equals(geographicPoint))
                return currNode;
        }
        return null;
    }

    /**
     * Find the path from start to goal using breadth first search
     *
     * @param start The starting location
     * @param goal  The goal location
     * @return The list of intersections that form the shortest (unweighted)
     * path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return bfs(start, goal, temp);
    }

    /**
     * Find the path from start to goal using breadth first search
     *
     * @param start        The starting location
     * @param goal         The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest (unweighted)
     * path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> bfs(GeographicPoint start,
                                     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {

        NodeGraph startNode = searchForNode(start);
        NodeGraph goalNode = searchForNode(goal);
        if (startNode == null || goalNode == null)
            return null;

        Queue<NodeGraph> queue = new LinkedList<NodeGraph>();
        HashSet<NodeGraph> visited = new HashSet<NodeGraph>();
        HashMap<NodeGraph, NodeGraph> parentMap = new HashMap<NodeGraph, NodeGraph>();
        boolean found = false;
        queue.add(startNode);

        while (!queue.isEmpty()) {
            NodeGraph currNode = queue.remove();
            if (currNode == goalNode) {
                found = true;
                break;
            }
            List<NodeGraph> neighbors = getNeighbors(currNode);
            ListIterator<NodeGraph> it = neighbors.listIterator(neighbors.size());
            while (it.hasPrevious()) {
                NodeGraph nextNode = it.previous();
                nodeSearched.accept(nextNode.getLocation());
                if (!visited.contains(nextNode)) {
                    visited.add(nextNode);
                    parentMap.put(nextNode, currNode);
                    queue.add(nextNode);
                }
            }
        }
        return reconstructPath(found, goalNode, startNode, parentMap);
    }

    /**
     * @param found     true, if goalNode is found.
     * @param goalNode  The goal node
     * @param startNode The starting node
     * @param parentMap to keep track of the path
     * @return the list of intersections forms shortest path
     */
    private LinkedList<GeographicPoint> reconstructPath(boolean found, NodeGraph goalNode, NodeGraph startNode, HashMap<NodeGraph, NodeGraph> parentMap) {
        LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
        if (!found) {
            System.out.println("No path exists");
            return path;
        }
        NodeGraph curr = goalNode;
        while (curr != startNode) {
            path.addFirst(curr.getLocation());
            curr = parentMap.get(curr);
        }
        path.addFirst(startNode.getLocation());
        return path;
    }

    /**
     * Create neighbors list for given node
     *
     * @param nodeGraph The given node
     * @return neighbors list
     */
    private List<NodeGraph> getNeighbors(NodeGraph nodeGraph) {
        List<NodeGraph> neigborsList = new ArrayList<NodeGraph>();
        for (GeographicPoint currGP : nodeGraph.getNeighborsList()) {
            NodeGraph currN = searchForNode(currGP);
            if (currN != null)
                neigborsList.add(currN);
        }
        return neigborsList;
    }

    /**
     * Find the path from start to goal using Dijkstra's algorithm
     *
     * @param start The starting location
     * @param goal  The goal location
     * @return The list of intersections that form the shortest path from
     * start to goal (including both start and goal).
     */
    public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        // You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return dijkstra(start, goal, temp);
    }

    /**
     * Find the path from start to goal using Dijkstra's algorithm
     *
     * @param start        The starting location
     * @param goal         The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest path from
     * start to goal (including both start and goal).
     */
    public List<GeographicPoint> dijkstra(GeographicPoint start,
                                          GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
        NodeGraph startNode = searchForNode(start);
        NodeGraph goalNode = searchForNode(goal);
        if (startNode == null || goalNode == null)
            return null;


        PriorityQueue<NodeGraph> priorityQueue = new PriorityQueue<NodeGraph>();
        HashSet<NodeGraph> visited = new HashSet<NodeGraph>();
        HashMap<NodeGraph, NodeGraph> parentMap = new HashMap<NodeGraph, NodeGraph>();
        boolean found = false;
        priorityQueue.add(startNode);
        while (!priorityQueue.isEmpty()) {
            NodeGraph currNode = priorityQueue.poll();
            if (!visited.contains(currNode)) {
                visited.add(currNode);
                if (currNode == goalNode) {
                    found = true;
                    break;
                }
                double distance = Double.POSITIVE_INFINITY;
                NodeGraph nearestNeighbor = null;
                for (NodeGraph currNeighbor : getNeighbors(currNode)) {
                    double currDistance = currNode.getLocation().distance(currNeighbor.getLocation());
                    if (!visited.contains(currNeighbor) && currDistance < distance) {
                        distance = currDistance;
                        nearestNeighbor = currNeighbor;
                    }
                }
                if (nearestNeighbor != null) {
                    priorityQueue.add(nearestNeighbor);
                    parentMap.put(currNode, nearestNeighbor);
                }
            }
        }


        // Hook for visualization.  See writeup.
        //nodeSearched.accept(next.getLocation());

        return null;
    }

    /**
     * Find the path from start to goal using A-Star search
     *
     * @param start The starting location
     * @param goal  The goal location
     * @return The list of intersections that form the shortest path from
     * start to goal (including both start and goal).
     */
    public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return aStarSearch(start, goal, temp);
    }

    /**
     * Find the path from start to goal using A-Star search
     *
     * @param start        The starting location
     * @param goal         The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest path from
     * start to goal (including both start and goal).
     */
    public List<GeographicPoint> aStarSearch(GeographicPoint start,
                                             GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
        // TODO: Implement this method in WEEK 4

        // Hook for visualization.  See writeup.
        //nodeSearched.accept(next.getLocation());

        return null;
    }

}
