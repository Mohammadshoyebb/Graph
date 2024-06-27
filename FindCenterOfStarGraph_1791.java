/**
 * 1791. Find Center of Star Graph
 * Problem:
 * There is an undirected star graph consisting of n nodes labeled from 1 to n.
 * A star graph is a graph where there is one center node and exactly n - 1 edges that connect
 * the center node with every other node.
 *
 * You are given a 2D integer array edges where each edges[i] = [ui, vi] indicates that there
 * is an edge between the nodes ui and vi. Return the center of the given star graph.
 *
 * Example 1:
 * Input: edges = [[1,2],[2,3],[4,2]]
 * Output: 2
 * Explanation: As shown in the figure above, node 2 is connected to every other node, so 2 is the center.
 *
 * Example 2:
 * Input: edges = [[1,2],[5,1],[1,3],[1,4]]
 * Output: 1
 *
 * Constraints:
 * - 3 <= n <= 10^5
 * - edges.length == n - 1
 * - edges[i].length == 2
 * - 1 <= ui, vi <= n
 * - ui != vi
 * - The given edges represent a valid star graph.
 */

 public class FindCenterOfStarGraph_1791 {
    public int findCenter(int[][] edges) {
        // Method 1: Using degree counting approach
        int[] degree = new int[edges.length + 2]; // n nodes labeled from 1 to n, so use n+1 size array

        // Iterate through each edge and update the degrees of the nodes
        for (int[] edge : edges) {
            degree[edge[0]]++; // Increment degree of node ui
            degree[edge[1]]++; // Increment degree of node vi
        }

        // Find the node with degree n-1 (the center node in a star graph)
        for (int i = 1; i <= edges.length + 1; i++) {
            if (degree[i] == edges.length) {
                return i;
            }
        }

        return -1; // In case no center node is found (though according to problem statement, it should always exist)
    }

    public int findCenterOptimized(int[][] edges) {
        // Method 2: Optimized approach
        int[] pair1 = edges[0];
        int[] pair2 = edges[1];

        if (pair1[0] == pair2[0] || pair1[0] == pair2[1]) {
            return pair1[0];
        } else {
            return pair1[1];
        }
    }

    public static void main(String[] args) {
        // Test cases
        FindCenterOfStarGraph_1791 solution = new FindCenterOfStarGraph_1791();

        int[][] edges1 = {{1, 2}, {2, 3}, {4, 2}};
        int center1 = solution.findCenter(edges1);
        System.out.println("Center of graph 1: " + center1); // Output should be 2

        int[][] edges2 = {{1, 2}, {5, 1}, {1, 3}, {1, 4}};
        int center2 = solution.findCenter(edges2);
        System.out.println("Center of graph 2: " + center2); // Output should be 1

        int centerOptimized1 = solution.findCenterOptimized(edges1);
        int centerOptimized2 = solution.findCenterOptimized(edges2);
        System.out.println("Optimized center of graph 1: " + centerOptimized1); // Output should be 2
        System.out.println("Optimized center of graph 2: " + centerOptimized2); // Output should be 1
    }
}
