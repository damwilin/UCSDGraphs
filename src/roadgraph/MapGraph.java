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
    private HashMap<GeographicPoint, NodeGraph> pointNodeMap;
    private HashSet<MapEdge> edges;


    /**
     * Create a new empty MapGraph
     */
    public MapGraph() {
        pointNodeMap = new HashMap<GeographicPoint, NodeGraph>();
        edges = new HashSet<MapEdge>();
    }

    public static void main(String[] args) {
        /*
        System.out.print("Making a new map...");
        MapGraph firstMap = new MapGraph();
        System.out.print("DONE. \nLoading the map...");
        GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
        System.out.println("DONE.");
*/
        // You can use this method for testing.


		/* Here are some test cases you should try before you attempt
         * the Week 3 End of Week Quiz, EVEN IF you score 100% on the
		 * programming assignment.
		 */

/*
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


*/
        /* Use this code in Week 3 End of Week Quiz */

        MapGraph theMap = new MapGraph();
        System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);


		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

    }

    /**
     * Get the number of vertices (road intersections) in the graph
     *
     * @return The number of vertices in the graph.
     */
    public int getNumVertices() {
        return pointNodeMap.values().size();
    }

    /**
     * Return the intersections, which are the vertices in this graph.
     *
     * @return The vertices in this graph as GeographicPoints
     */
    public Set<GeographicPoint> getVertices() {
        return pointNodeMap.keySet();
    }

    /**
     * Get the number of road segments in the graph
     *
     * @return The number of edges in the graph.
     */
    public int getNumEdges() {
        return edges.size();
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
        if (location == null) {
            return false;
        }
        NodeGraph n = pointNodeMap.get(location);
        if (n == null) {
            n = new NodeGraph(location);
            pointNodeMap.put(location, n);
            return true;
        } else {
            return false;
        }
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
        NodeGraph n1 = pointNodeMap.get(from);
        NodeGraph n2 = pointNodeMap.get(to);

        // check nodes are valid
        if (n1 == null)
            throw new NullPointerException("addEdge: pt1:" + from + "is not in graph");
        if (n2 == null)
            throw new NullPointerException("addEdge: pt2:" + to + "is not in graph");

        MapEdge edge = new MapEdge(roadName, roadType, n1, n2, length);
        edges.add(edge);
        n1.addEdge(edge);
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
        //Check validity of inputs
        if (start == null || goal == null)
            throw new NullPointerException("Cannot find route from or to null node");
        NodeGraph startNode = pointNodeMap.get(start);
        NodeGraph goalNode = pointNodeMap.get(goal);
        if (startNode == null || goalNode == null) {
            throw new NullPointerException("Start or End node doesn't exist");
        }

        //Setup BFS
        setAllDefaultDistance();
        HashMap<NodeGraph, NodeGraph> parentMap = new HashMap<NodeGraph, NodeGraph>();
        Queue<NodeGraph> toExplore = new LinkedList<NodeGraph>();
        HashSet<NodeGraph> visited = new HashSet<NodeGraph>();
        toExplore.add(startNode);
        NodeGraph next = null;

        while (!toExplore.isEmpty()) {
            next = toExplore.remove();
            //hook for visualization
            nodeSearched.accept(next.getLocation());
            System.out.println(next);

            if (next.equals(goalNode))
                break;

            for (NodeGraph currNeighbor : next.getNeighbors()) {
                if (!visited.contains(currNeighbor)) {
                    visited.add(currNeighbor);
                    parentMap.put(currNeighbor, next);
                    toExplore.add(currNeighbor);
                }
            }
        }
        if (!next.equals(goalNode)) {
            System.out.println("No path found from " + start + " to " + goal);
            return null;
        }
        //Reconstruct the parent path
        return reconstructPath(parentMap, startNode, goalNode);
    }

    private List<GeographicPoint> reconstructPath(HashMap<NodeGraph, NodeGraph> parentMap, NodeGraph startNode, NodeGraph goalNode) {
        LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
        NodeGraph current = goalNode;

        while (!current.equals(startNode)) {
            path.addFirst(current.getLocation());
            current = parentMap.get(current);
        }
        //add start
        path.addFirst(startNode.getLocation());
        return path;
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

        //Check validity of inputs
        if (start == null || goal == null)
            throw new NullPointerException("Cannot find route from or to null node");
        NodeGraph startNode = pointNodeMap.get(start);
        NodeGraph goalNode = pointNodeMap.get(goal);
        if (startNode == null || goalNode == null) {
            throw new NullPointerException("Start or End node doesn't exist");
        }

        //Setup dijkstra
        int visitedNodes = 0;
        setAllDefaultDistance();
        PriorityQueue<NodeGraph> priorityQueue = new PriorityQueue<NodeGraph>();
        HashSet<NodeGraph> visited = new HashSet<NodeGraph>();
        HashMap<NodeGraph, NodeGraph> parentMap = new HashMap<NodeGraph, NodeGraph>();
        startNode.setDistanceFromStart(0);
        priorityQueue.add(startNode);
        NodeGraph currNode = null;

        while (!priorityQueue.isEmpty()) {
            currNode = priorityQueue.poll();
            if (currNode != null) {
                System.out.println("Dijkstra:");
                System.out.println(currNode);
                visitedNodes++;
            }
            nodeSearched.accept(currNode.getLocation());
            if (!visited.contains(currNode)) {
                visited.add(currNode);
                if (currNode.equals(goalNode))
                    break;
                for (NodeGraph currNeighbor : currNode.getNeighbors()) {
                    if (!visited.contains(currNeighbor)) {
                        double distanceFromCurr = currNeighbor.getDistanceTo(currNode);
                        double distanceFromStart = distanceFromCurr + currNode.getDistanceFromStart();
                        if (distanceFromStart < currNeighbor.getDistanceFromStart()) {
                            currNeighbor.setDistanceFromStart(distanceFromStart);
                            if (!priorityQueue.contains(currNeighbor))
                                priorityQueue.add(currNeighbor);
                            parentMap.put(currNeighbor, currNode);
                        }
                    }
                }
            }

        }
        System.out.println("Nodes visited: " + visitedNodes);
        return reconstructPath(parentMap, startNode, goalNode);
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
        //Check validity of inputs
        if (start == null || goal == null)
            throw new NullPointerException("Cannot find route from or to null node");
        NodeGraph startNode = pointNodeMap.get(start);
        NodeGraph goalNode = pointNodeMap.get(goal);
        if (startNode == null || goalNode == null) {
            throw new NullPointerException("Start or End node doesn't exist");
        }

        //Setup dijkstra
        int nodesVisited = 0;
        setAllDefaultDistance();
        PriorityQueue<NodeGraph> priorityQueue = new PriorityQueue<NodeGraph>();
        HashSet<NodeGraph> visited = new HashSet<NodeGraph>();
        HashMap<NodeGraph, NodeGraph> parentMap = new HashMap<NodeGraph, NodeGraph>();
        startNode.setDistanceFromStart(0);
        priorityQueue.add(startNode);
        NodeGraph currNode = null;

        while (!priorityQueue.isEmpty()) {
            currNode = priorityQueue.poll();
            nodeSearched.accept(currNode.getLocation());
            System.out.println("A*:");
            System.out.println(currNode);
            nodesVisited++;
            if (!visited.contains(currNode)) {
                visited.add(currNode);
                if (currNode.equals(goalNode))
                    break;
                for (NodeGraph currNeighbor : currNode.getNeighbors()) {
                    if (!visited.contains(currNeighbor)) {
                        double distanceFromCurr = currNeighbor.getDistanceTo(currNode);
                        double distanceFromGoal = currNeighbor.getDistanceTo(goalNode);
                        double distanceFromStart = distanceFromCurr + distanceFromGoal;
                        if (distanceFromStart < currNeighbor.getDistanceFromStart()) {
                            currNeighbor.setDistanceFromStart(distanceFromStart);
                            if (!priorityQueue.contains(currNeighbor))
                                priorityQueue.add(currNeighbor);
                            parentMap.put(currNeighbor, currNode);
                        }
                    }
                }
            }

        }
        System.out.println("Nodes visited: " + nodesVisited);
        return reconstructPath(parentMap, startNode, goalNode);
    }

    private void setAllDefaultDistance() {
        for (GeographicPoint gP : pointNodeMap.keySet()) {
            pointNodeMap.get(gP).setDefaultDistance();
        }
    }

}
