import java.util.*;

// Class to represent a character transformation and its associated cost
class Transformation {
    char original;
    char changed;
    int cost;

    Transformation(char original, char changed, int cost) {
        this.original = original;
        this.changed = changed;
        this.cost = cost;
    }
}

public class MinimumCostToConvertString {
    
    public int minimumCostToConvertString(String source, String target, char[] original, char[] changed, int[] cost) {
        int n = source.length();
        int m = original.length;

        // If source and target lengths are not equal, return -1 (though the problem guarantees equal lengths)
        if (source.length() != target.length()) return -1;

        // Build the transformation map
        Map<Character, List<Transformation>> transformations = new HashMap<>();
        for (int i = 0; i < m; i++) {
            transformations.putIfAbsent(original[i], new ArrayList<>());
            transformations.get(original[i]).add(new Transformation(original[i], changed[i], cost[i]));
        }

        // Function to find the minimum cost to transform a single character using BFS
        int bfs(char start, char end, Map<Character, List<Transformation>> transformations) {
            if (start == end) return 0;
            Queue<Character> queue = new LinkedList<>();
            Queue<Integer> costQueue = new LinkedList<>();
            Set<Character> visited = new HashSet<>();

            queue.offer(start);
            costQueue.offer(0);

            while (!queue.isEmpty()) {
                char current = queue.poll();
                int currentCost = costQueue.poll();

                if (current == end) return currentCost;

                if (!transformations.containsKey(current)) continue;

                for (Transformation trans : transformations.get(current)) {
                    if (!visited.contains(trans.changed)) {
                        visited.add(trans.changed);
                        queue.offer(trans.changed);
                        costQueue.offer(currentCost + trans.cost);
                    }
                }
            }
            return -1; // If transformation is not possible
        }

        int totalCost = 0;
        for (int i = 0; i < n; i++) {
            int costToTransform = bfs(source.charAt(i), target.charAt(i), transformations);
            if (costToTransform == -1) return -1; // If any transformation is not possible
            totalCost += costToTransform;
        }

        return totalCost;
    }

    // Main method to test the solution
    public static void main(String[] args) {
        MinimumCostToConvertString solution = new MinimumCostToConvertString();

        // Test case 1
        String source1 = "abcd";
        String target1 = "acbe";
        char[] original1 = {'a', 'b', 'c', 'c', 'e', 'd'};
        char[] changed1 = {'b', 'c', 'b', 'e', 'b', 'e'};
        int[] cost1 = {2, 5, 5, 1, 2, 20};
        System.out.println(solution.minimumCostToConvertString(source1, target1, original1, changed1, cost1)); // Output: 28

        // Test case 2
        String source2 = "aaaa";
        String target2 = "bbbb";
        char[] original2 = {'a', 'c'};
        char[] changed2 = {'c', 'b'};
        int[] cost2 = {1, 2};
        System.out.println(solution.minimumCostToConvertString(source2, target2, original2, changed2, cost2)); // Output: 12

        // Test case 3
        String source3 = "abcd";
        String target3 = "abce";
        char[] original3 = {'a'};
        char[] changed3 = {'e'};
        int[] cost3 = {10000};
        System.out.println(solution.minimumCostToConvertString(source3, target3, original3, changed3, cost3)); // Output: -1
    }
}

