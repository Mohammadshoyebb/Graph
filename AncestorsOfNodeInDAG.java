/**
 * 2192. All Ancestors of a Node in a Directed Acyclic Graph
 * 
 * You are given a positive integer n representing the number of nodes of a Directed Acyclic Graph (DAG).
 * The nodes are numbered from 0 to n - 1 (inclusive).
 * 
 * You are also given a 2D integer array edges, where edges[i] = [fromi, toi] denotes that there is a
 * unidirectional edge from fromi to toi in the graph.
 * 
 * Return a list answer, where answer[i] is the list of ancestors of the ith node, sorted in ascending order.
 * 
 * A node u is an ancestor of another node v if u can reach v via a set of edges.
 * 
 * Example 1:
 * 
 * Input: n = 8, edgeList = [[0,3],[0,4],[1,3],[2,4],[2,7],[3,5],[3,6],[3,7],[4,6]]
 * Output: [[],[],[],[0,1],[0,2],[0,1,3],[0,1,2,3,4],[0,1,2,3]]
 * Explanation:
 * The above diagram represents the input graph.
 * - Nodes 0, 1, and 2 do not have any ancestors.
 * - Node 3 has two ancestors 0 and 1.
 * - Node 4 has two ancestors 0 and 2.
 * - Node 5 has three ancestors 0, 1, and 3.
 * - Node 6 has five ancestors 0, 1, 2, 3, and 4.
 * - Node 7 has four ancestors 0, 1, 2, and 3.
 * 
 * Example 2:
 * 
 * Input: n = 5, edgeList = [[0,1],[0,2],[0,3],[0,4],[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
 * Output: [[],[0],[0,1],[0,1,2],[0,1,2,3]]
 * Explanation:
 * The above diagram represents the input graph.
 * - Node 0 does not have any ancestor.
 * - Node 1 has one ancestor 0.
 * - Node 2 has two ancestors 0 and 1.
 * - Node 3 has three ancestors 0, 1, and 2.
 * - Node 4 has four ancestors 0, 1, 2, and 3.
 * 
 * Constraints:
 * 
 * 1 <= n <= 1000
 * 0 <= edges.length <= min(2000, n * (n - 1) / 2)
 * edges[i].length == 2
 * 0 <= fromi, toi <= n - 1
 * fromi != toi
 * There are no duplicate edges.
 * The graph is directed and acyclic.
 */

 import java.util.*;

 public class AncestorsOfNodeInDAG {
 
     // Topological Sort Approach
     public List<List<Integer>> getAncestorsTopoSort(int n, int[][] edges) {
         // Create adjacency list and in-degree array
         List<List<Integer>> adjList = new ArrayList<>();
         int[] inDegree = new int[n];
         for (int i = 0; i < n; i++) {
             adjList.add(new ArrayList<>());
         }
 
         // Populate adjacency list and in-degree array
         for (int[] edge : edges) {
             int from = edge[0];
             int to = edge[1];\
             adjList.get(from).add(to);
             inDegree[to]++;
         }
 
         // Queue for topological sort
         Queue<Integer> queue = new LinkedList<>();
         // Initialize the queue with all nodes having in-degree 0
         for (int i = 0; i < n; i++) {
             if (inDegree[i] == 0) {
                 queue.add(i);
             }
         }
 
         // List of sets to store ancestors
         List<Set<Integer>> ancestors = new ArrayList<>();
         for (int i = 0; i < n; i++) {
             ancestors.add(new HashSet<>());
         }
 
         // Perform topological sort and track ancestors
         while (!queue.isEmpty()) {
             int node = queue.poll();
             for (int neighbor : adjList.get(node)) {
                 // Add current node's ancestors to the neighbor's ancestors
                 ancestors.get(neighbor).addAll(ancestors.get(node));
                 // Add the current node itself as an ancestor
                 ancestors.get(neighbor).add(node);
                 // Decrement in-degree and add to queue if it becomes 0
                 inDegree[neighbor]--;
                 if (inDegree[neighbor] == 0) {
                     queue.add(neighbor);
                 }
             }
         }
 
         // Convert sets to sorted lists
         List<List<Integer>> result = new ArrayList<>();
         for (int i = 0; i < n; i++) {
             List<Integer> sortedAncestors = new ArrayList<>(ancestors.get(i));
             Collections.sort(sortedAncestors);
             result.add(sortedAncestors);
         }
 
         return result;
     }
 //=======================================================================================================

 
     // DFS Approach
     public List<List<Integer>> getAncestorsDFS(int n, int[][] edges) {
         List<List<Integer>> adjList = new ArrayList<>();
         for (int i = 0; i < n; i++) {
             adjList.add(new ArrayList<>());
         }
 
         for (int[] edge : edges) {
             adjList.get(edge[0]).add(edge[1]);
         }
 
         List<Set<Integer>> ancestors = new ArrayList<>();
         for (int i = 0; i < n; i++) {
             ancestors.add(new HashSet<>());
         }
 
         for (int i = 0; i < n; i++) {
             dfs(i, adjList, ancestors, new boolean[n]);
         }
 
         List<List<Integer>> result = new ArrayList<>();
         for (int i = 0; i < n; i++) {
             List<Integer> sortedAncestors = new ArrayList<>(ancestors.get(i));
             Collections.sort(sortedAncestors);
             result.add(sortedAncestors);
         }
 
         return result;
     }
 
     private void dfs(int node, List<List<Integer>> adjList, List<Set<Integer>> ancestors, boolean[] visited) {
         if (visited[node]) return;
         visited[node] = true;
 
         for (int neighbor : adjList.get(node)) {
             dfs(neighbor, adjList, ancestors, visited);
             ancestors.get(neighbor).add(node);
             ancestors.get(neighbor).addAll(ancestors.get(node));
         }
 
         visited[node] = false;
     }
 
     public static void main(String[] args) {
         AncestorsOfNodeInDAG solution = new AncestorsOfNodeInDAG();
         
         int n1 = 8;
         int[][] edges1 = { {0,3}, {0,4}, {1,3}, {2,4}, {2,7}, {3,5}, {3,6}, {3,7}, {4,6} };
         System.out.println("Topological Sort Approach:");
         System.out.println(solution.getAncestorsTopoSort(n1, edges1));
         System.out.println("DFS Approach:");
         System.out.println(solution.getAncestorsDFS(n1, edges1));
 
         int n2 = 5;
         int[][] edges2 = { {0,1}, {0,2}, {0,3}, {0,4}, {1,2}, {1,3}, {1,4}, {2,3}, {2,4}, {3,4} };
         System.out.println("Topological Sort Approach:");
         System.out.println(solution.getAncestorsTopoSort(n2, edges2));
         System.out.println("DFS Approach:");
         System.out.println(solution.getAncestorsDFS(n2, edges2));
     }
 }
 
