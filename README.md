# PiJ Coursework 4

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