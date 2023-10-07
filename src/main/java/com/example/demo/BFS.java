package com.example.demo;

import java.util.*;

public class BFS {
    public static SearchStats breadthFirstSearch(PuzzleState initialState) {
        Queue<PuzzleState> queue = new LinkedList<>();
        HashSet<PuzzleState> visited = new HashSet<>();

        // Create a SearchStats object to track statistics
        SearchStats stats = new SearchStats();

        queue.add(initialState);
        visited.add(initialState);

        long startTime = System.currentTimeMillis();

        while (!queue.isEmpty()) {
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

            for (PuzzleState successor : currentState.generateSuccessors()) {
                if (!visited.contains(successor)) {
                    queue.add(successor);
                    visited.add(successor);
                }
            }
            printSolutionPath(currentState);
        }

        // Goal state not reachable
        return stats;
    }

    static void printSolutionPath(PuzzleState goalState) {
        List<PuzzleState> path = new ArrayList<>();
        PuzzleState currentState = goalState;

        while (currentState != null) {
            path.add(currentState);
            currentState = currentState.getParent();
        }

        Collections.reverse(path);

        int move = 0;
        for (PuzzleState state : path) {
            state.printBoard();
            System.out.println();
            move++;
        }
    }
}


