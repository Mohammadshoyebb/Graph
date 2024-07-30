/**
 * Dijkstra Algorithm
 * 
 * Given a weighted, undirected, and connected graph of V vertices and an adjacency list adj 
 * where adj[i] is a list of lists containing two integers where the first integer of each list j 
 * denotes there is an edge between i and j, and the second integer corresponds to the weight of that edge. 
 * You are given the source vertex S and you have to find the shortest distance of all the vertices 
 * from the source vertex S. You have to return a list of integers denoting the shortest distance 
 * between each node and Source vertex S.
 * 
 * Note: The Graph doesn't contain any negative weight cycle.
 * 
 * Example 1:
 * Input:
 * V = 2
 * adj [] = {{{1, 9}}, {{0, 9}}}
 * S = 0
 * Output:
 * 0 9
 * Explanation:
 * The source vertex is 0. Hence, the shortest 
 * distance of node 0 is 0 and the shortest 
 * distance from node 1 is 9.
 * 
 * Example 2:
 * Input:
 * V = 3, E = 3
 * adj = {{{1, 1}, {2, 6}}, {{2, 3}, {0, 1}}, {{1, 3}, {0, 6}}}
 * S = 2
 * Output:
 * 4 3 0
 * Explanation:
 * For nodes 2 to 0, we can follow the path-
 * 2-1-0. This has a distance of 1+3 = 4,
 * whereas the path 2-0 has a distance of 6. So,
 * the Shortest path from 2 to 0 is 4.
 * The shortest distance from 0 to 1 is 1 .
 * 
 * Your Task:
 * You don't need to read input or print anything. Your task is to complete the function dijkstra()  
 * which takes the number of vertices V and an adjacency list adj as input parameters and Source vertex S 
 * returns a list of integers, where ith integer denotes the shortest distance of the ith node from the Source node. 
 * Here adj[i] contains a list of lists containing two integers where the first integer j denotes that there is an edge 
 * between i and j and the second integer w denotes that the weight between edge i and j is w.
 * 
 * Expected Time Complexity: O(V^2).
 * Expected Auxiliary Space: O(V^2).
 * 
 * Constraints:
 * 1 ≤ V ≤ 1000
 * 0 ≤ adj[i][j] ≤ 1000
 * 1 ≤ adj.size() ≤ [ (V*(V - 1)) / 2 ]
 * 0 ≤ S < V
 */

 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Comparator;
 import java.util.PriorityQueue;
 
 public class DijkstraAlgorithm {
     // Function to find the shortest distance of all the vertices from the source vertex S.
     static int[] dijkstra(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj, int S) {
         int dist[] = new int[V];
         
         // Initializing distances to infinity.
         Arrays.fill(dist, Integer.MAX_VALUE);
         dist[S] = 0;
 
         // Priority queue to store vertices and their distances.
         PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
         pq.offer(new int[]{S, 0});
         
         while (!pq.isEmpty()) {
             int[] element = pq.poll();
             int node = element[0];
             int distance = element[1];
             
             // Exploring neighbors of the current node.
             for (ArrayList<Integer> neighbour : adj.get(node)) {
                 int newDist = distance + neighbour.get(1);
                 // If a shorter path to the neighbor is found.
                 if (newDist < dist[neighbour.get(0)]) {
                     dist[neighbour.get(0)] = newDist;
                     pq.offer(new int[]{neighbour.get(0), newDist});
                 }
             }
         }
         return dist;
     }
 
     public static void main(String[] args) {
         // Example 1
         int V1 = 2;
         ArrayList<ArrayList<ArrayList<Integer>>> adj1 = new ArrayList<>();
         adj1.add(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(1, 9)))));
         adj1.add(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(0, 9)))));
         int S1 = 0;
         System.out.println(Arrays.toString(dijkstra(V1, adj1, S1))); // Output: [0, 9]
 
         // Example 2
         int V2 = 3;
         ArrayList<ArrayList<ArrayList<Integer>>> adj2 = new ArrayList<>();
         adj2.add(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(1, 1)), new ArrayList<>(Arrays.asList(2, 6)))));
         adj2.add(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(2, 3)), new ArrayList<>(Arrays.asList(0, 1)))));
         adj2.add(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(1, 3)), new ArrayList<>(Arrays.asList(0, 6)))));
         int S2 = 2;
         System.out.println(Arrays.toString(dijkstra(V2, adj2, S2))); // Output: [4, 3, 0]
     }
 }
 
