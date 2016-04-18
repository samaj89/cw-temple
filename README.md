# PiJ Coursework 4

This repository contains my solution to PiJ coursework assignment 4, "George Osborne and the Temple of Gloom!". It was
completed independently (no partner). The student package, which contains my code for the explore() and escape()
methods of the Explorer class, also contains two classes that I have created to aid with the task: TreeNode (for the
explore() method) and Vertex (for the escape() method). The below notes document my progression with the task and detail
limitations with my solution.

#### First pass at coding explore()

Various online videos on graph search algorithms suggested implementation using some sort of tree structure to keep
track of the path to the orb. As such, I first created a TreeNode class with private fields for id, distance from orb,
and pointers to the parent and children (neighbour) TreeNodes. Children are stored as a priority queue, with distance
from orb as the sorting mechanism.

Implementation of explore() then uses a depth-first search algorithm, building the tree by checking whether the current
node contains the orb, collecting the node's neighbours (removing any that have already been visited), adding neighbours
as children to the node, then visiting the first child in the priority queue of children (i.e. the closest to the orb).
If a given node has no unvisited neighbours (i.e. dead end), we retrace our steps to the first node that has unvisited
neighbours and continue, repeating until the orb is found.

This algorithm works well for small, simple maps, but there is a lot of inefficient travelling as the maps get larger
and more complex. For now, focus is on getting a working solution, but may revisit later to try to find a more efficient
solution.

#### First pass at coding escape()

The coursework specification says that getting to the exit should be prioritised over collecting gold, therefore my first
pass at coding escape() simply finds the shortest path from the start node to the exit, picking up any gold that happens
to be on that path.

More online research suggests using Djikstra's algorithm to find the shortest path between nodes. As this algorithm finds
the shortest path between the source node and all nodes in the graph if it runs to completion, my implementation stops
once the shortest path to the exit has been found so as to avoid unnecessary computation.

This implementation also required creating a Vertex class. There is one instance of this class for each Node in the graph,
and allows the recording of the shortest distance needed to reach this node, as well as the previous node along the
shortest path to the given node (this allows for reconstruction of the shortest path from source to exit once Djikstra's)
algorithm has completed.

As this solution finds the shortest path from source to exit, there is often plenty of unused time which could have been
used to collect gold lying away from this path. I may revisit later to see if there is a way of diverging from the
shortest path without running out of time.