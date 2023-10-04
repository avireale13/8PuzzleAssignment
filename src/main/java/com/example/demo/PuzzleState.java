package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PuzzleState {
    private int[][] board;
    private int size;
    private int goalValue;

    public PuzzleState(int[][] board) {
        this.board = board;
        this.size = board.length;
        this.goalValue = 1;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getSize() {
        return size;
    }

    public boolean isGoalState() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] != goalValue) {
                    return false;
                }
                goalValue = (goalValue % (size * size)) + 1;
            }
        }
        return true;
    }

    public List<PuzzleState> generateSuccessors() {
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
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    private int[][] cloneBoard() {
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, size);
        }
        return copy;
    }

    public static PuzzleState createRandomInitialState() {
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
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    // ... (other methods)
}



