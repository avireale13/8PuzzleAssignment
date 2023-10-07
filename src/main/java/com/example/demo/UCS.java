package com.example.demo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class UCS {
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

    public static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node node1, Node node2) {
            return Integer.compare(node1.cost, node2.cost);
        }
    }

    public static SearchStats uniformCostSearch(PuzzleState initialState) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(new NodeComparator());
        HashSet<PuzzleState> closedSet = new HashSet<>();

        // Create a SearchStats object to track statistics
        SearchStats stats = new SearchStats();

        Node startNode = new Node(initialState, null, 0);
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
                printSolutionPath(currentNode); // Print the solution path
                System.out.println("---------------");
                return stats;
            }

            closedSet.add(currentPuzzleState);

            for (PuzzleState successor : currentPuzzleState.generateSuccessors()) {
                if (closedSet.contains(successor)) {
                    continue; // Skip already explored states
                }

                int cost = currentNode.cost + 1;

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

