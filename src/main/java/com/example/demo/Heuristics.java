package com.example.demo;

public class Heuristics {

    public static int manhattanDistance(PuzzleState state) {
        int distance = 0; // Initialize the total distance

        // Iterate through each tile in the puzzle
        for (int row = 0; row < state.getSize(); row++) {
            for (int col = 0; col < state.getSize(); col++) {
                int value = state.getBoard()[row][col]; // Get the value of the current tile

                if (value != 0) { // Skip empty tile
                    int goalRow = (value - 1) / state.getSize(); // Calculate expected row
                    int goalCol = (value - 1) % state.getSize(); // Calculate expected column
                    distance += Math.abs(row - goalRow) + Math.abs(col - goalCol); // Calculate distance
                }
            }
        }

        return distance; // Return the total distance as the heuristic value
    }
}
