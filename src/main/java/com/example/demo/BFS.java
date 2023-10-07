package com.example.demo;

import java.util.*;

public class BFS {
    public static SearchStats breadthFirstSearch(PuzzleState initialState) {
        // Create a queue for breadth-first search
        Queue<PuzzleState> queue = new LinkedList<>();

        // Set to keep track of visited puzzle states
        HashSet<PuzzleState> visited = new HashSet<>();

        // Create a SearchStats object to track statistics
        SearchStats stats = new SearchStats();

        // Initialize the queue with the initial state and mark it as visited
        queue.add(initialState);
        visited.add(initialState);

        // Record the start time for measuring runtime
        long startTime = System.currentTimeMillis();

        while (!queue.isEmpty()) {
            // Get the current state from the front of the queue
            PuzzleState currentState = queue.poll();

            // Increment nodes visited in the SearchStats
            stats.incrementNodesVisited();

            if (currentState.isGoalState()) {
                // Goal state found, set runtime and return SearchStats
                long endTime = System.currentTimeMillis();
                long runtime = endTime - startTime;
                stats.setRuntime(runtime);

                // Print the solution path
                printSolutionPath(currentState);
                System.out.println("---------------");
                return stats;
            }

            // Generate successors (neighboring states) for the current state
            for (PuzzleState successor : currentState.generateSuccessors()) {
                if (!visited.contains(successor)) {
                    // Add successor to the queue and mark it as visited
                    queue.add(successor);
                    visited.add(successor);
                }
            }

            // Print the solution path after processing each state
            printSolutionPath(currentState);
        }

        // Goal state not reachable
        return stats;
    }

    // Method to print the solution path
    static void printSolutionPath(PuzzleState goalState) {
        // Create a list to store the path from goal state to initial state
        List<PuzzleState> path = new ArrayList<>();
        PuzzleState currentState = goalState;

        // Trace back from the goal state to the initial state
        while (currentState != null) {
            path.add(currentState);
            currentState = currentState.getParent();
        }

        // Reverse the path to print from initial to goal state
        Collections.reverse(path);

        int move = 0;
        for (PuzzleState state : path) {
            state.printBoard();
            System.out.println();
            move++;
        }
    }
}



