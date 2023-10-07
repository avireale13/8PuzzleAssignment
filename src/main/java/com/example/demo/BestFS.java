package com.example.demo;

import java.util.*;

public class BestFS {
    // Nested class to represent a node in the search tree
    public static class Node {
        PuzzleState state; // Current puzzle state
        Node parent;       // Parent node in the search tree
        int cost;          // Cost to reach this node (not used in Best-First Search)
        int heuristic;     // Heuristic value of the state

        // Constructor to initialize the node
        public Node(PuzzleState state, Node parent, int cost, int heuristic) {
            this.state = state;
            this.parent = parent;
            this.cost = cost; // Note: Cost is not used in Best-First Search
            this.heuristic = heuristic; // Heuristic value guides the search
        }
    }

    // Nested class to implement a custom comparator for nodes
    public static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node node1, Node node2) {
            // Compare nodes based on their heuristic values
            return Integer.compare(node1.heuristic, node2.heuristic);
        }
    }

    // Method to perform Best-First Search
    public static SearchStats bestFirstSearch(PuzzleState initialState) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(new NodeComparator());
        HashSet<PuzzleState> closedSet = new HashSet<>();

        // Create a SearchStats object to track statistics
        SearchStats stats = new SearchStats();

        // Create the initial node with the start state and heuristic value
        Node startNode = new Node(initialState, null, 0, heuristic(initialState));
        openSet.add(startNode); // Add the initial node to the open set

        long startTime = System.currentTimeMillis();

        // Start the search loop
        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll(); // Get the node with the lowest heuristic value
            PuzzleState currentPuzzleState = currentNode.state;

            // Increment nodes visited in the SearchStats
            stats.incrementNodesVisited();

            // Check if the current state is the goal state
            if (currentPuzzleState.isGoalState()) {
                // Goal state found, set runtime and return SearchStats
                long endTime = System.currentTimeMillis();
                long runtime = endTime - startTime;
                stats.setRuntime(runtime);

                // Print the solution path and return the statistics
                printSolutionPath(currentNode);
                System.out.println("---------------");
                return stats;
            }

            // Mark the current state as explored
            closedSet.add(currentPuzzleState);

            // Generate successors of the current state
            for (PuzzleState successor : currentPuzzleState.generateSuccessors()) {
                if (closedSet.contains(successor)) {
                    continue; // Skip already explored states
                }

                // Calculate the heuristic value for the successor
                int heuristic = heuristic(successor);

                // Create a node for the successor with a cost of 0 (not used) and the heuristic
                Node successorNode = new Node(successor, currentNode, 0, heuristic);

                // Check if this successor state is already in the open set with a higher heuristic
                boolean inOpenSet = openSet.contains(successorNode);
                if (!inOpenSet || heuristic < successorNode.heuristic) {
                    openSet.add(successorNode); // Add the successor node to the open set
                }
            }
        }

        // Goal state not reachable
        return stats;
    }

    // Method to calculate the heuristic value using Manhattan distance
    public static int heuristic(PuzzleState state) {
        return Heuristics.manhattanDistance(state);
    }

    // Method to print the solution path from the goal state to the initial state
    private static void printSolutionPath(Node node) {
        Stack<Node> path = new Stack<>();
        while (node != null) {
            path.push(node);
            node = node.parent;
        }

        int move = 0;
        while (!path.isEmpty()) {
            Node currentNode = path.pop();
            currentNode.state.printBoard();
            System.out.println();
            move++;
        }
    }
}

