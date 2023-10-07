package com.example.demo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class UCS {
    // Definition of a node used in the uniform cost search
    public static class Node {
        PuzzleState state;
        Node parent;
        int cost;

        public Node(PuzzleState state, Node parent, int cost) {
            this.state = state;
            this.parent = parent;
            this.cost = cost;
        }
    }

    // Comparator for comparing nodes based on cost
    public static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node node1, Node node2) {
            return Integer.compare(node1.cost, node2.cost);
        }
    }

    // Implementation of Uniform Cost Search
    public static SearchStats uniformCostSearch(PuzzleState initialState) {
        // Priority queue to store nodes with the lowest cost at the front
        PriorityQueue<Node> openSet = new PriorityQueue<>(new NodeComparator());

        // HashSet to keep track of visited states
        HashSet<PuzzleState> closedSet = new HashSet<>();

        // Create a SearchStats object to track statistics
        SearchStats stats = new SearchStats();

        // Create the initial node with the provided puzzle state
        Node startNode = new Node(initialState, null, 0);
        openSet.add(startNode);

        // Record the start time for measuring runtime
        long startTime = System.currentTimeMillis();

        while (!openSet.isEmpty()) {
            // Get the node with the lowest cost from the priority queue
            Node currentNode = openSet.poll();
            PuzzleState currentPuzzleState = currentNode.state;

            // Increment nodes visited in the SearchStats
            stats.incrementNodesVisited();

            if (currentPuzzleState.isGoalState()) {
                // Goal state found, set runtime and return SearchStats
                long endTime = System.currentTimeMillis();
                long runtime = endTime - startTime;
                stats.setRuntime(runtime);

                // Print the solution path
                printSolutionPath(currentNode);
                System.out.println("---------------");
                return stats;
            }

            // Add the current state to the closed set to avoid revisiting
            closedSet.add(currentPuzzleState);

            for (PuzzleState successor : currentPuzzleState.generateSuccessors()) {
                if (closedSet.contains(successor)) {
                    continue; // Skip already explored states
                }

                // Calculate the cost to reach this successor state
                int cost = currentNode.cost + 1;

                // Create a node for the successor
                Node successorNode = new Node(successor, currentNode, cost);

                // Check if this successor state is already in the open set with a lower cost
                boolean inOpenSet = openSet.contains(successorNode);
                if (!inOpenSet || cost < successorNode.cost) {
                    openSet.add(successorNode);
                }
            }
        }

        // Goal state not reachable
        return stats;
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


