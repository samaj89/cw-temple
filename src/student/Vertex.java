package student;

import game.Node;

/**
 * A class that will aid wth implementation of escape(). Each instance of Vertex has three
 * fields: a pointer to a node that corresponds to a node in the graph, a pointer to the previous node on
 * the shortest path, and an integer representing the distance travelled from the source node to the given
 * vertex.
 *
 * @author Sam Jansen
 */
public class Vertex {
    private Node node;
    private Node previousNode;
    private Integer distance;

    public Vertex(Node node) {
        this.node = node;
        this.previousNode = null;
        this.distance = Integer.MAX_VALUE;
    }

    public Node getVertexNode() {
        return this.node;
    }

    public Node getPreviousNode() {
        return this.previousNode;
    }

    public void setPreviousNode(Node prev) {
        this.previousNode = prev;
    }

    public Integer getDistance() {
        return this.distance;
    }

    public void setDistance(Integer dist) {
        this.distance = dist;
    }
}
