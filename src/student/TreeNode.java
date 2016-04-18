package student;

import game.NodeStatus;

import java.util.List;

/**
 * The TreeNode will be the basic component of a tree representing the paths
 * through a given maze, with each node representing one floor tile in the maze.
 *
 * TreeNode has an id matching that of the corresponding maze tile's id, a distance
 * representing the maze tile's distance to the target, a PriorityQueue of child TreeNodes
 * that represent the maze tile's neighbouring tiles (prioritised by distance from target),
 * and a parent TreeNode representing the maze tile traversed just before the current maze
 * tile.
 *
 * @author Sam Jansen
 */

public class TreeNode {
    private final long id;
    private final int distance;
    private PriorityQueue<TreeNode> children;
    private TreeNode parent;

    public TreeNode(long id, int distance) {
        this.id = id;
        this.distance = distance;
        this.children = new PriorityQueueImpl<>();
        this.parent = null;
    }

    public long getId() {
        return this.id;
    }

    public int getDistance() {
        return this.distance;
    }

    public TreeNode getParent() {
        return this.parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public PriorityQueue<TreeNode> getChildren() {
        return this.children;
    }

    public void addChildren(List<NodeStatus> childrenToAdd) {
        for (NodeStatus n : childrenToAdd) {
            TreeNode childToAdd = new TreeNode(n.getId(), n.getDistanceToTarget());
            childToAdd.setParent(this);
            this.children.add(childToAdd, (double) childToAdd.getDistance());
        }
    }
}
