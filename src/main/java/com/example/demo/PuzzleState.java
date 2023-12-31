package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PuzzleState {
    private int[][] board;
    private int size;

    public PuzzleState(int[][] board) {
        // Initialize the puzzle state with the given board
        this.board = board;
        this.size = board.length;
    }

    public int[][] getBoard() {
        // Get the puzzle board
        return board;
    }

    public int getSize() {
        // Get the size of the puzzle
        return size;
    }

    public boolean isGoalState() {
        // Check if the current state is the goal state
        int[][] expectedGoal = {
                {1, 2, 3},
                {8, 0, 4},
                {7, 6, 5}
        };

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] != expectedGoal[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }

    public List<PuzzleState> generateSuccessors() {
        // Generate successor states by moving the empty tile
        List<PuzzleState> successors = new ArrayList<>();
        int emptyRow = -1;
        int emptyCol = -1;

        // Find the position of the empty tile (0)
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 0) {
                    emptyRow = row;
                    emptyCol = col;
                    break;
                }
            }
        }

        // Define the possible move directions
        int[][] moves = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        // Generate successor states by moving the empty tile
        for (int[] move : moves) {
            int newRow = emptyRow + move[0];
            int newCol = emptyCol + move[1];

            // Check if the move is within bounds
            if (isValidMove(newRow, newCol)) {
                // Create a copy of the current board
                int[][] newBoard = cloneBoard();

                // Swap the empty tile (0) and the adjacent tile
                int temp = newBoard[emptyRow][emptyCol];
                newBoard[emptyRow][emptyCol] = newBoard[newRow][newCol];
                newBoard[newRow][newCol] = temp;

                // Create a new PuzzleState for the successor and add it to the list
                successors.add(new PuzzleState(newBoard));
            }
        }

        return successors;
    }

    private boolean isValidMove(int row, int col) {
        // Check if a move to the specified row and column is valid
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    private int[][] cloneBoard() {
        // Clone the current puzzle board
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, size);
        }
        return copy;
    }

    public static PuzzleState createRandomInitialState() {
        // Create a random initial puzzle state
        int size = 3; // Assuming a 3x3 puzzle
        int[][] board = new int[size][size];
        int[] values = new int[size * size];

        for (int i = 0; i < size * size; i++) {
            values[i] = i;
        }

        shuffleArray(values);

        int index = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (index < values.length) {
                    board[row][col] = values[index++];
                }
            }
        }

        return new PuzzleState(board);
    }

    private static void shuffleArray(int[] array) {
        // Shuffle an array of integers randomly
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    @Override
    public boolean equals(Object o) {
        // Compare two PuzzleState objects for equality
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuzzleState that = (PuzzleState) o;
        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        // Generate a hash code for the PuzzleState
        return Arrays.deepHashCode(board);
    }

    public void printBoard() {
        // Print the current state of the puzzle board
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.print("| " + board[row][col] + " ");
            }
            System.out.println("|"); // End the row with a |
        }
    }

    private PuzzleState parent; // Add this field

    // Add getter and setter for the parent field
    public PuzzleState getParent() {
        // Get the parent PuzzleState
        return parent;
    }

    public void setParent(PuzzleState parent) {
        // Set the parent PuzzleState
        this.parent = parent;
    }
}




