/**
 * 2976. Minimum Cost to Convert String I
 * Solved

 * Problem Statement:
 * 
 * You are given two 0-indexed strings source and target, both of length n and consisting of lowercase English letters.
 * You are also given two 0-indexed character arrays original and changed, and an integer array cost, where cost[i] represents
 * the cost of changing the character original[i] to the character changed[i].
 * 
 * You start with the string source. In one operation, you can pick a character x from the string and change it to the character y 
 * at a cost of z if there exists any index j such that cost[j] == z, original[j] == x, and changed[j] == y.
 * 
 * Return the minimum cost to convert the string source to the string target using any number of operations. If it is impossible
 * to convert source to target, return -1.
 * 
 * Note that there may exist indices i, j such that original[j] == original[i] and changed[j] == changed[i].
 * 
 * Example 1:
 * Input: source = "abcd", target = "acbe", original = ["a","b","c","c","e","d"], changed = ["b","c","b","e","b","e"], cost = [2,5,5,1,2,20]
 * Output: 28
 * Explanation: To convert the string "abcd" to string "acbe":
 * - Change value at index 1 from 'b' to 'c' at a cost of 5.
 * - Change value at index 2 from 'c' to 'e' at a cost of 1.
 * - Change value at index 2 from 'e' to 'b' at a cost of 2.
 * - Change value at index 3 from 'd' to 'e' at a cost of 20.
 * The total cost incurred is 5 + 1 + 2 + 20 = 28.
 * It can be shown that this is the minimum possible cost.
 * 
 * Example 2:
 * Input: source = "aaaa", target = "bbbb", original = ["a","c"], changed = ["c","b"], cost = [1,2]
 * Output: 12
 * Explanation: To change the character 'a' to 'b' change the character 'a' to 'c' at a cost of 1, followed by changing the character 'c' to 'b' at a cost of 2, for a total cost of 1 + 2 = 3. To change all occurrences of 'a' to 'b', a total cost of 3 * 4 = 12 is incurred.
 * 
 * Example 3:
 * Input: source = "abcd", target = "abce", original = ["a"], changed = ["e"], cost = [10000]
 * Output: -1
 * Explanation: It is impossible to convert source to target because the value at index 3 cannot be changed from 'd' to 'e'.
 * 
 * Constraints:
 * 1 <= source.length == target.length <= 105
 * source, target consist of lowercase English letters.
 * 1 <= cost.length == original.length == changed.length <= 2000
 * original[i], changed[i] are lowercase English letters.
 * 1 <= cost[i] <= 106
 * original[i] != changed[i]
 */

 import java.util.Arrays;

public class MinimumConversionCost {
     public long calculateMinimumCost(String source, String target, char[] original, char[] changed, int[] conversionCost) {
         // Initialize the distance matrix with a large value (simulating infinity)
         int[][] distanceMatrix = new int[26][26];
         for (int i = 0; i < 26; i++) {
             Arrays.fill(distanceMatrix[i], Integer.MAX_VALUE);
             distanceMatrix[i][i] = 0; // Distance from a character to itself is zero
         }
 
         // Populate the distance matrix with the given conversion costs
         for (int i = 0; i < conversionCost.length; i++) {
             distanceMatrix[original[i] - 'a'][changed[i] - 'a'] = Math.min(distanceMatrix[original[i] - 'a'][changed[i] - 'a'], conversionCost[i]);
         }
 
         // Floyd-Warshall algorithm to find the shortest path between all pairs of characters
         for (int k = 0; k < 26; k++) {
             for (int i = 0; i < 26; i++) {
                 if (distanceMatrix[i][k] < Integer.MAX_VALUE) {
                     for (int j = 0; j < 26; j++) {
                         if (distanceMatrix[k][j] < Integer.MAX_VALUE) {
                             distanceMatrix[i][j] = Math.min(distanceMatrix[i][j], distanceMatrix[i][k] + distanceMatrix[k][j]);
                         }
                     }
                 }
             }
         }
 
         // Calculate the total minimum cost to convert source to target
         long totalCost = 0L;
         for (int i = 0; i < source.length(); i++) {
             int sourceCharIndex = source.charAt(i) - 'a';
             int targetCharIndex = target.charAt(i) - 'a';
             if (distanceMatrix[sourceCharIndex][targetCharIndex] == Integer.MAX_VALUE) {
                 return -1L; // Return -1 if conversion is impossible
             } else {
                 totalCost += (long) distanceMatrix[sourceCharIndex][targetCharIndex];
             }
         }
         return totalCost;
     }
 
     public static void main(String[] args) {
         MinimumConversionCost converter = new MinimumConversionCost();
         
         // Example 1
         String source1 = "abcd";
         String target1 = "acbe";
         char[] original1 = {'a', 'b', 'c', 'c', 'e', 'd'};
         char[] changed1 = {'b', 'c', 'b', 'e', 'b', 'e'};
         int[] cost1 = {2, 5, 5, 1, 2, 20};
         System.out.println("Example 1 Output: " + converter.calculateMinimumCost(source1, target1, original1, changed1, cost1));
         
         // Example 2
         String source2 = "aaaa";
         String target2 = "bbbb";
         char[] original2 = {'a', 'c'};
         char[] changed2 = {'c', 'b'};
         int[] cost2 = {1, 2};
         System.out.println("Example 2 Output: " + converter.calculateMinimumCost(source2, target2, original2, changed2, cost2));
         
         // Example 3
         String source3 = "abcd";
         String target3 = "abce";
         char[] original3 = {'a'};
         char[] changed3 = {'e'};
         int[] cost3 = {10000};
         System.out.println("Example 3 Output: " + converter.calculateMinimumCost(source3, target3, original3, changed3, cost3));
     }
 }

 //=====================Simple to understand but less optimized ========================================
 /*
  * import java.util.Arrays;

class Solution {
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        // Initialize the distance matrix with a large value (simulating infinity)
        int[][] dis = new int[26][26];
        for (int i = 0; i < 26; i++) {
            Arrays.fill(dis[i], Integer.MAX_VALUE);
            dis[i][i] = 0; // Distance from a character to itself is zero
        }

        // Populate the distance matrix with the given conversion costs
        for (int i = 0; i < cost.length; i++) {
            dis[original[i] - 'a'][changed[i] - 'a'] = Math.min(dis[original[i] - 'a'][changed[i] - 'a'], cost[i]);
        }

        // Floyd-Warshall algorithm to find the shortest path between all pairs of characters
        for (int k = 0; k < 26; k++) {
            for (int i = 0; i < 26; i++) {
                if (dis[i][k] < Integer.MAX_VALUE) {
                    for (int j = 0; j < 26; j++) {
                        if (dis[k][j] < Integer.MAX_VALUE) {
                            dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[k][j]);
                        }
                    }
                }
            }
        }

        // Calculate the total minimum cost to convert source to target
        long ans = 0L;
        for (int i = 0; i < source.length(); i++) {
            int c1 = source.charAt(i) - 'a';
            int c2 = target.charAt(i) - 'a';
            if (dis[c1][c2] == Integer.MAX_VALUE) {
                return -1L; // Return -1 if conversion is impossible
            } else {
                ans += (long) dis[c1][c2];
            }
        }
        return ans;
    }
}

  */
 