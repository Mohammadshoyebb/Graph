/*
 * 1579. Remove Max Number of Edges to Keep Graph Fully Traversable
Solved
Hard
Topics
Companies
Hint
Alice and Bob have an undirected graph of n nodes and three types of edges:

Type 1: Can be traversed by Alice only.
Type 2: Can be traversed by Bob only.
Type 3: Can be traversed by both Alice and Bob.
Given an array edges where edges[i] = [typei, ui, vi] represents a bidirectional edge of type typei between nodes ui and vi, find the maximum number of edges you can remove so that after removing the edges, the graph can still be fully traversed by both Alice and Bob. The graph is fully traversed by Alice and Bob if starting from any node, they can reach all other nodes.

Return the maximum number of edges you can remove, or return -1 if Alice and Bob cannot fully traverse the graph.

 

Example 1:



Input: n = 4, edges = [[3,1,2],[3,2,3],[1,1,3],[1,2,4],[1,1,2],[2,3,4]]
Output: 2
Explanation: If we remove the 2 edges [1,1,2] and [1,1,3]. The graph will still be fully traversable by Alice and Bob. Removing any additional edge will not make it so. So the maximum number of edges we can remove is 2.
Example 2:



Input: n = 4, edges = [[3,1,2],[3,2,3],[1,1,4],[2,1,4]]
Output: 0
Explanation: Notice that removing any edge will not make the graph fully traversable by Alice and Bob.
Example 3:



Input: n = 4, edges = [[3,2,3],[1,1,2],[2,3,4]]
Output: -1
Explanation: In the current graph, Alice cannot reach node 4 from the other nodes. Likewise, Bob cannot reach 1. Therefore it's impossible to make the graph fully traversable.
 

 

Constraints:

1 <= n <= 105
1 <= edges.length <= min(105, 3 * n * (n - 1) / 2)
edges[i].length == 3
1 <= typei <= 3
1 <= ui < vi <= n
All tuples (typei, ui, vi) are distinct.
 */



import java.util.*;

public class RemoveMaxEdgesToKeepGraphFullyTraversable {
    
    class UnionFind {
        private int[] parent, rank;
        private int count;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            count = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        public int find(int u) {
            if (parent[u] != u) {
                parent[u] = find(parent[u]);
            }
            return parent[u];
        }
        
        public boolean union(int u, int v) {
            int rootU = find(u);
            int rootV = find(v);
            if (rootU == rootV) {
                return false;
            }
            if (rank[rootU] > rank[rootV]) {
                parent[rootV] = rootU;
            } else if (rank[rootU] < rank[rootV]) {
                parent[rootU] = rootV;
            } else {
                parent[rootV] = rootU;
                rank[rootU]++;
            }
            count--;
            return true;
        }
        
        public int getCount() {
            return count;
        }
    }

    public int maxNumEdgesToRemove(int n, int[][] edges) {
        UnionFind aliceUF = new UnionFind(n);
        UnionFind bobUF = new UnionFind(n);
        int edgesUsed = 0;

        // Type 3 edges first
        for (int[] edge : edges) {
            if (edge[0] == 3) {
                if (aliceUF.union(edge[1] - 1, edge[2] - 1)) {
                    bobUF.union(edge[1] - 1, edge[2] - 1);
                    edgesUsed++;
                }
            }
        }

        // Type 1 edges for Alice
        for (int[] edge : edges) {
            if (edge[0] == 1) {
                if (aliceUF.union(edge[1] - 1, edge[2] - 1)) {
                    edgesUsed++;
                }
            }
        }

        // Type 2 edges for Bob
        for (int[] edge : edges) {
            if (edge[0] == 2) {
                if (bobUF.union(edge[1] - 1, edge[2] - 1)) {
                    edgesUsed++;
                }
            }
        }

        // Check if both graphs are fully traversable
        if (aliceUF.getCount() > 1 || bobUF.getCount() > 1) {
            return -1;
        }

        return edges.length - edgesUsed;
    }

    public static void main(String[] args) {
        RemoveMaxEdgesToKeepGraphFullyTraversable solution = new RemoveMaxEdgesToKeepGraphFullyTraversable();
        
        int n1 = 4;
        int[][] edges1 = { {3,1,2}, {3,2,3}, {1,1,3}, {1,2,4}, {1,1,2}, {2,3,4} };
        System.out.println(solution.maxNumEdgesToRemove(n1, edges1)); // Output: 2

        int n2 = 4;
        int[][] edges2 = { {3,1,2}, {3,2,3}, {1,1,4}, {2,1,4} };
        System.out.println(solution.maxNumEdgesToRemove(n2, edges2)); // Output: 0

        int n3 = 4;
        int[][] edges3 = { {3,2,3}, {1,1,2}, {2,3,4} };
        System.out.println(solution.maxNumEdgesToRemove(n3, edges3)); // Output: -1
    }
}
