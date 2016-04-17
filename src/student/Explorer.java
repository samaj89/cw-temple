package student;

import game.*;

import java.util.*;

public class Explorer {

    /**
     * Explore the cavern, trying to find the orb in as few steps as possible.
     * Once you find the orb, you must return from the function in order to pick
     * it up. If you continue to move after finding the orb rather
     * than returning, it will not count.
     * If you return from this function while not standing on top of the orb,
     * it will count as a failure.
     * <p>
     * There is no limit to how many steps you can take, but you will receive
     * a score bonus multiplier for finding the orb in fewer steps.
     * <p>
     * At every step, you only know your current tile's ID and the ID of all
     * open neighbor tiles, as well as the distance to the orb at each of these tiles
     * (ignoring walls and obstacles).
     * <p>
     * To get information about the current state, use functions
     * getCurrentLocation(),
     * getNeighbours(), and
     * getDistanceToTarget()
     * in ExplorationState.
     * You know you are standing on the orb when getDistanceToTarget() is 0.
     * <p>
     * Use function moveTo(long id) in ExplorationState to move to a neighboring
     * tile by its ID. Doing this will change state to reflect your new position.
     * <p>
     * A suggested first implementation that will always find the orb, but likely won't
     * receive a large bonus multiplier, is a depth-first search.
     *
     * @param state the information available at the current state
     */
    public void explore(ExplorationState state) {
        Set<Long> visited = new LinkedHashSet<>();
        TreeNode tree = new TreeNode(state.getCurrentLocation(), state.getDistanceToTarget());
        TreeNode current = tree;
        boolean orbFound = false;
        Collection<NodeStatus> neighbours;

        while (!orbFound) {
            visited.add(current.getId());
            if (current.getDistance() == 0) {
                orbFound = true;
            } else {
                neighbours = state.getNeighbours();
                List<NodeStatus> neighboursToAdd = new ArrayList<>();
                for (NodeStatus n : neighbours) {
                    if (!visited.contains(n.getId())) {
                        neighboursToAdd.add(n);
                    }
                }
                if (neighboursToAdd.size() == 0) {
                    current = current.getParent();
                    state.moveTo(current.getId());
                    current.getChildren().poll();
                } else {
                    current.addChildren(neighboursToAdd);
                    current = current.getChildren().peek();
                    state.moveTo(current.getId());
                }
            }
        }
    }

    /**
     * Escape from the cavern before the ceiling collapses, trying to collect as much
     * gold as possible along the way. Your solution must ALWAYS escape before time runs
     * out, and this should be prioritized above collecting gold.
     * <p>
     * You now have access to the entire underlying graph, which can be accessed through EscapeState.
     * getCurrentNode() and getExit() will return you Node objects of interest, and getVertices()
     * will return a collection of all nodes on the graph.
     * <p>
     * Note that time is measured entirely in the number of steps taken, and for each step
     * the time remaining is decremented by the weight of the edge taken. You can use
     * getTimeRemaining() to get the time still remaining, pickUpGold() to pick up any gold
     * on your current tile (this will fail if no such gold exists), and moveTo() to move
     * to a destination node adjacent to your current node.
     * <p>
     * You must return from this function while standing at the exit. Failing to do so before time
     * runs out or returning from the wrong location will be considered a failed run.
     * <p>
     * You will always have enough time to escape using the shortest path from the starting
     * position to the exit, although this will not collect much gold.
     *
     * @param state the information available at the current state
     */
    public void escape(EscapeState state) {
        List<Vertex> paths = findShortestPaths(state, state.getExit());
        Stack<Node> pathToExit = getPathToDestination(state, paths, state.getExit());
        followPathToDestination(state, pathToExit);
    }

    /**
     * Private method representing an implementation of Djikstra's algorithm for
     * finding the shortest path between nodes in a graph, starting from the current
     * location of the sprite after picking up the orb. Will stop once the shortest
     * path to the exit has been found.
     *
     * @param state the game state containing the current maze being attempted
     * @return a list of Vertex objects, each of which holds a node of the maze,
     * the shortest distance required to reach that node, and the previous node in
     * the path to the given node. Will include a vertex containing the exit node
     * if there is one.
     */
    private List<Vertex> findShortestPaths(EscapeState state, Node destination) {
        Collection<Node> allNodes = state.getVertices();
        PriorityQueue<Vertex> unsettledPQ = new PriorityQueueImpl<>();
        List<Vertex> unsettledList = new ArrayList<>();
        List<Vertex> settled = new ArrayList<>();
        List<Node> settledNodes = new ArrayList<>();
        Node source = state.getCurrentNode();
        Vertex sourceVertex = new Vertex(source);
        sourceVertex.setDistance(0);
        unsettledPQ.add(sourceVertex, (double) sourceVertex.getDistance());
        unsettledList.add(sourceVertex);
        for (Node n : allNodes) {
            if (!n.equals(source)) {
                Vertex v = new Vertex(n);
                unsettledList.add(v);
                unsettledPQ.add(v, (double) v.getDistance());
            }
        }
        while (unsettledPQ.size() != 0) {
            Vertex eval = unsettledPQ.poll();
            settled.add(eval);
            settledNodes.add(eval.getVertexNode());
            if (eval.getVertexNode().equals(destination)) {
                return settled;
            }
            int distSoFar = eval.getDistance();
            for (Edge e : eval.getVertexNode().getExits()) {
                Node otherNode = e.getOther(eval.getVertexNode());
                Vertex otherVertex = null;
                if (!settledNodes.contains(otherNode)) {
                    int possNewDist = distSoFar + e.length();
                    for (Vertex u : unsettledList) {
                        if (u.getVertexNode().equals(otherNode)) {
                            otherVertex = u;
                        }
                    }
                    if (possNewDist < otherVertex.getDistance()) {
                        otherVertex.setDistance(possNewDist);
                        otherVertex.setPreviousNode(eval.getVertexNode());
                        unsettledPQ.updatePriority(otherVertex, possNewDist);
                        for (Vertex u : unsettledList) {
                            if (u.getVertexNode().equals(otherNode)) {
                                u = otherVertex;
                            }
                        }
                    }
                }
            }
        }
        return settled;
    }

    /**
     *
     * @param state the game state containing the current maze being attempted
     * @param allPaths a list of Vertex objects, each of which record the shortest path
     *                 to the vertex's node.
     * @return a stack of nodes representing the path from the source node to the exit.
     */
    private Stack<Node> getPathToDestination(EscapeState state, List<Vertex> allPaths, Node destination) {
        Vertex nextVertex = null;
        for (Vertex v : allPaths) {
            if (v.getVertexNode().equals(destination)) {
                nextVertex = v;
            }
        }
        Stack<Node> pathToDest = new Stack<>();
        pathToDest.push(nextVertex.getVertexNode());
        Node previousNode = nextVertex.getPreviousNode();
        while(!previousNode.equals(state.getCurrentNode())) {
            pathToDest.push(previousNode);
            for (Vertex v : allPaths) {
                if (v.getVertexNode().equals(previousNode)) {
                    nextVertex = v;
                }
            }
            previousNode = nextVertex.getPreviousNode();
        }
        return pathToDest;
    }

    /**
     * Private method that feeds the sprite instructions on how to move through
     * the maze in order to reach the exit.
     *
     * @param state the game state containing the current maze being attempted
     * @param path a stack of nodes representing the path from the source node to
     *             the exit
     */
    private void followPathToDestination(EscapeState state, Stack<Node> path) {
        do {
            tryToPickUpGold(state);
            state.moveTo(path.pop());
        } while (!path.isEmpty());
    }

    /**
     * Private method that attempts to pick up gold from a tile. There are no
     * ill effects if the attempt is unsuccessful (i.e. if there was never any gold
     * on the tile or if the gold has already been picked up.
     *
     * @param state the game state containing the current maze being attempted
     */
    private void tryToPickUpGold(EscapeState state) {
        try {
            state.pickUpGold();
        } catch (IllegalStateException ex) {
            //do nothing
        }
    }
}
