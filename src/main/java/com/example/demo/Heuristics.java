package com.example.demo;

public class Heuristics {

    // Heuristic 1: Manhattan Distance
    public static int manhattanDistance(PuzzleState state) {
        int distance = 0;
        int[][] board = state.getBoard();
        int size = state.getSize();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int value = board[row][col];
                if (value != 0) { // Skip the empty tile
                    int goalRow = (value - 1) / size;
                    int goalCol = (value - 1) % size;
                    distance += Math.abs(row - goalRow) + Math.abs(col - goalCol);
                }
            }
        }

        return distance;
    }

}
