package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;

import java.util.*;

public class Controller {
    @FXML
    private Button buttonToHide;

    @FXML
    private Button buttonToShow;

    @FXML
    private MenuButton menu;

    @FXML
    private Button startNewGame;

    @FXML
    private Button solveButton;

    @FXML
    private Text runTime1;

    @FXML
    private Text nodeVisits2;

    @FXML
    private Text runTimeText;

    @FXML
    private Text nodesVisitedText;

    @FXML
    private GridPane puzzleGrid; // Assuming you have a GridPane for the puzzle

    private PuzzleState initialState;

    private SearchAlgorithm selectedAlgorithm;

    private enum SearchAlgorithm {
        BFS,
        UCS,
        BEST_FIRST,
        A_STAR
    }

    @FXML
    private void initialize() {
        // Clear existing items from the menu
        menu.getItems().clear();

        // Set up the algorithm choice menu items
        MenuItem bfsItem = new MenuItem("Breadth-First Search (BFS)");
        MenuItem ucsItem = new MenuItem("Uniform-Cost Search (UCS)");
        MenuItem bestFirstItem = new MenuItem("Best-First Search (BFS)");
        MenuItem aStarItem = new MenuItem("A* Algorithm");

        // Initialize initialState here
        initialState = PuzzleState.createRandomInitialState();

        // Set action handlers for algorithm menu items
        bfsItem.setOnAction(this::handleAlgorithmSelection);
        ucsItem.setOnAction(this::handleAlgorithmSelection);
        bestFirstItem.setOnAction(this::handleAlgorithmSelection);
        aStarItem.setOnAction(this::handleAlgorithmSelection);

        menu.getItems().addAll(bfsItem, ucsItem, bestFirstItem, aStarItem);
    }


    @FXML
    private void handleStartButton(ActionEvent event) {
        initialState = PuzzleState.createRandomInitialState();
        int[][] board = initialState.getBoard();
        fillGridWithPuzzleValues(board);
        hideButton(startNewGame);
    }

    @FXML
    private void handleAlgorithmSelection(ActionEvent event) {
        MenuItem selectedMenuItem = (MenuItem) event.getSource();
        String algorithmName = selectedMenuItem.getText();

        switch (algorithmName) {
            case "Breadth-First Search (BFS)":
                selectedAlgorithm = SearchAlgorithm.BFS;
                break;
            case "Uniform-Cost Search (UCS)":
                selectedAlgorithm = SearchAlgorithm.UCS;
                break;
            case "Best-First Search (BFS)":
                selectedAlgorithm = SearchAlgorithm.BEST_FIRST;
                break;
            case "A* Algorithm":
                selectedAlgorithm = SearchAlgorithm.A_STAR;
                break;
            default:
                selectedAlgorithm = null;
                break;
        }

        solveButton.setText("Solve using " + algorithmName);
        menu.setText(algorithmName);
    }

    @FXML
    private void handleSolveButton(ActionEvent event) {
        if (selectedAlgorithm != null && initialState != null) {
            SearchStats stats = solvePuzzle(selectedAlgorithm, initialState);
            updateUIWithStats(stats);
        }
    }

    private SearchStats solvePuzzle(SearchAlgorithm algorithm, PuzzleState initialState) {
        switch (algorithm) {
            case BFS:
                return BFS.breadthFirstSearch(initialState);
            // Implement handling for other algorithms (UCS, Best-First, A*) here
            default:
                return null;
        }
    }

    private void updateUIWithStats(SearchStats stats) {
        if (stats != null) {
            runTime1.setText("Runtime: " + stats.getRuntime() + " ms");
            nodeVisits2.setText("Nodes Visited: " + stats.getNodesVisited());
        } else {
            runTime1.setText("Runtime: N/A");
            nodeVisits2.setText("Nodes Visited: N/A");
        }
    }

    private void fillGridWithPuzzleValues(int[][] board) {
        puzzleGrid.getChildren().clear(); // Clear existing content

        int numRows = board.length;
        int numCols = board[0].length;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Integer value = board[row][col];
                String displayValue = (value != null) ? value.toString() : "";
                Text text = new Text(displayValue);
                puzzleGrid.add(text, col, row);
            }
        }
    }

    private void hideButton(Button button) {
        button.setVisible(false);
    }

    @FXML
    private void showButton(ActionEvent event) {
        buttonToShow.setVisible(true);
    }
}



