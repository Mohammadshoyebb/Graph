
// 1334. Find the City With the Smallest Number of Neighbors at a Threshold Distance
// Medium
// Topics
// Companies
// Hint
// There are n cities numbered from 0 to n-1. Given the array edges where edges[i] = [fromi, toi, weighti] represents a bidirectional and weighted edge between cities fromi and toi, and given the integer distanceThreshold.
// 
// Return the city with the smallest number of cities that are reachable through some path and whose distance is at most distanceThreshold. If there are multiple such cities, return the city with the greatest number.
// 
// Notice that the distance of a path connecting cities i and j is equal to the sum of the edges' weights along that path.
// 
// Example 1:
// 
// Input: n = 4, edges = [[0,1,3],[1,2,1],[1,3,4],[2,3,1]], distanceThreshold = 4
// Output: 3
// Explanation: The figure above describes the graph. 
// The neighboring cities at a distanceThreshold = 4 for each city are:
// City 0 -> [City 1, City 2] 
// City 1 -> [City 0, City 2, City 3] 
// City 2 -> [City 0, City 1, City 3] 
// City 3 -> [City 1, City 2] 
// Cities 0 and 3 have 2 neighboring cities at a distanceThreshold = 4, but we have to return city 3 since it has the greatest number.
// Example 2:
// 
// Input: n = 5, edges = [[0,1,2],[0,4,8],[1,2,3],[1,4,2],[2,3,1],[3,4,1]], distanceThreshold = 2
// Output: 0
// Explanation: The figure above describes the graph. 
// The neighboring cities at a distanceThreshold = 2 for each city are:
// City 0 -> [City 1] 
// City 1 -> [City 0, City 4] 
// City 2 -> [City 3, City 4] 
// City 3 -> [City 2, City 4]
// City 4 -> [City 1, City 2, City 3] 
// The city 0 has 1 neighboring city at a distanceThreshold = 2.
// 
// Constraints:
// 
// 2 <= n <= 100
// 1 <= edges.length <= n * (n - 1) / 2
// edges[i].length == 3
// 0 <= fromi < toi < n
// 1 <= weighti, distanceThreshold <= 10^4
// All pairs (fromi, toi) are distinct.

public class FindCityWithSmallestNumberOfNeighbors {

    public int findTheCity(int n, int[][] edges, int t) {
        int m = edges.length;
        int[][] d = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    d[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            if (edges[i][2] <= t) {
                d[edges[i][0]][edges[i][1]] = edges[i][2];
                d[edges[i][1]][edges[i][0]] = edges[i][2];
            }
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                if (d[i][k] == Integer.MAX_VALUE)
                    continue;
                for (int j = i; j < n; j++) {
                    if (d[k][j] < Integer.MAX_VALUE && d[i][j] > (d[i][k] + d[k][j])) {
                        d[i][j] = d[j][i] = d[i][k] + d[k][j];
                    }
                }
            }
        }
        int count = n;
        int ans = -1;
        for (int i = 0; i < n; i++) {
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (d[i][j] <= t) {
                    c++;
                }
            }
            if (c <= count) {
                count = c;
                ans = i;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        FindCityWithSmallestNumberOfNeighbors solution = new FindCityWithSmallestNumberOfNeighbors();
        
        int n1 = 4;
        int[][] edges1 = {{0,1,3},{1,2,1},{1,3,4},{2,3,1}};
        int distanceThreshold1 = 4;
        System.out.println(solution.findTheCity(n1, edges1, distanceThreshold1)); // Output: 3

        int n2 = 5;
        int[][] edges2 = {{0,1,2},{0,4,8},{1,2,3},{1,4,2},{2,3,1},{3,4,1}};
        int distanceThreshold2 = 2;
        System.out.println(solution.findTheCity(n2, edges2, distanceThreshold2)); // Output: 0
    }
}
//======================================Understandable approach(Algorithm hq) ========================================
/*
 import java.util.Arrays;

public class FindCityWithSmallestNumberOfNeighbors {

    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] dist = new int[n][n];

        // Initialize distances with a large number
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // Distance to itself is 0
        for (int i = 0; i < n; i++) {
            dist[i][i] = 0;
        }

        // Populate initial distances based on edges
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int weight = edge[2];
            dist[from][to] = weight;
            dist[to][from] = weight;
        }

        // Floyd-Warshall algorithm to find all pairs shortest paths
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }

        int minNeighborCount = Integer.MAX_VALUE;
        int city = -1;

        // Find the city with the smallest number of neighbors within distanceThreshold
        for (int i = 0; i < n; i++) {
            int neighborCount = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && dist[i][j] <= distanceThreshold) {
                    neighborCount++;
                }
            }
            if (neighborCount <= minNeighborCount) {
                minNeighborCount = neighborCount;
                city = i;
            }
        }

        return city;
    }

    public static void main(String[] args) {
        FindCityWithSmallestNumberOfNeighbors solution = new FindCityWithSmallestNumberOfNeighbors();
        
        int n1 = 4;
        int[][] edges1 = {{0,1,3},{1,2,1},{1,3,4},{2,3,1}};
        int distanceThreshold1 = 4;
        System.out.println(solution.findTheCity(n1, edges1, distanceThreshold1)); // Output: 3

        int n2 = 5;
        int[][] edges2 = {{0,1,2},{0,4,8},{1,2,3},{1,4,2},{2,3,1},{3,4,1}};
        int distanceThreshold2 = 2;
        System.out.println(solution.findTheCity(n2, edges2, distanceThreshold2)); // Output: 0
    }
}
 */