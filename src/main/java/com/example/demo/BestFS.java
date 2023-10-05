package com.example.demo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class BestFS {
    public static class Node {
        PuzzleState state;
        Node parent;
        int cost;
        int heuristic;

        public Node(PuzzleState state, Node parent, int cost, int heuristic) {
            this.state = state;
            this.parent = parent;
            this.cost = cost;
            this.heuristic = heuristic;
        }
    }

    public static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node node1, Node node2) {
            // Use the heuristic value to compare nodes (no cost consideration)
            return Integer.compare(node1.heuristic, node2.heuristic);
        }
    }

    public static SearchStats bestFirstSearch(PuzzleState initialState) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(new NodeComparator());
        HashSet<PuzzleState> closedSet = new HashSet<>();

        // Create a SearchStats object to track statistics
        SearchStats stats = new SearchStats();

        Node startNode = new Node(initialState, null, 0, heuristic(initialState));
        openSet.add(startNode);

        long startTime = System.currentTimeMillis();

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            PuzzleState currentPuzzleState = currentNode.state;

            // Increment nodes visited in the SearchStats
            stats.incrementNodesVisited();

            if (currentPuzzleState.isGoalState()) {
                // Goal state found, set runtime and return SearchStats
                long endTime = System.currentTimeMillis();
                long runtime = endTime - startTime;
                stats.setRuntime(runtime);
                return stats;
            }

            closedSet.add(currentPuzzleState);

            for (PuzzleState successor : currentPuzzleState.generateSuccessors()) {
                if (closedSet.contains(successor)) {
                    continue; // Skip already explored states
                }

                int heuristic = heuristic(successor);

                Node successorNode = new Node(successor, currentNode, 0, heuristic);

                // Check if this successor state is already in the open set with a higher heuristic
                boolean inOpenSet = openSet.contains(successorNode);
                if (!inOpenSet || heuristic < successorNode.heuristic) {
                    openSet.add(successorNode);
                }


            }
        }

        // Goal state not reachable
        return stats;
    }

    // Define your heuristic function here (Manhattan distance)
    public static int heuristic(PuzzleState state) {
        return Heuristics.manhattanDistance(state);
    }
}
