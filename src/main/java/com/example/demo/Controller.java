package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

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
    private Button newGame;

    @FXML
    private Button quit;

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
        newGame.setVisible(false);
        quit.setVisible(false);
        runTimeText.setVisible(false);
        nodesVisitedText.setVisible(false);
        solveButton.setVisible(false);
        menu.setVisible(false);

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
        runTimeText.setVisible(true);
        nodesVisitedText.setVisible(true);
        solveButton.setVisible(true);
        menu.setVisible(true);
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
        newGame.setVisible(true);
        quit.setVisible(true);
    }

    @FXML
    private void handleNewGame(ActionEvent event) {
        initialState = PuzzleState.createRandomInitialState();
        int[][] board = initialState.getBoard();
        fillGridWithPuzzleValues(board);
        hideButton(startNewGame);
    }

    @FXML
    private void handleNewGameButton(ActionEvent event) {
        handleNewGame(event);
    }

    @FXML
    private void handleQuitButton(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    private SearchStats solvePuzzle(SearchAlgorithm algorithm, PuzzleState initialState) {
        switch (algorithm) {
            case BFS:
                return BFS.breadthFirstSearch(initialState);
            case UCS:
                return UCS.uniformCostSearch(initialState);
            case BEST_FIRST:
                return BestFS.bestFirstSearch(initialState);
            case A_STAR:
                return ASTAR.aStarSearch(initialState);
            default:
                return null;
        }
    }

    private void updateUIWithStats(SearchStats stats) {
        if (stats != null) {
            runTime1.setText(stats.getRuntime() + " ms");
            nodeVisits2.setText(String.valueOf(stats.getNodesVisited()));
        } else {
            runTime1.setText("N/A");
            nodeVisits2.setText("N/A");
        }
    }

    private void fillGridWithPuzzleValues(int[][] board) {

        int numRows = board.length;
        int numCols = board[0].length;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Integer value = board[row][col];
                String displayValue = (value != null) ? value.toString() : "";

                // Create a Text node with the displayValue
                Text text = new Text(displayValue);

                // Set alignment to CENTER for the Text node
                text.setTextAlignment(TextAlignment.CENTER);

                // Add the Text node to a StackPane for alignment
                StackPane stackPane = new StackPane(text);
                stackPane.setAlignment(Pos.CENTER);

                // Add the StackPane to the GridPane with proper row and column
                puzzleGrid.add(stackPane, col, row);

                // Set alignment for the Text node using the custom method
                TextAlignmentUtils.setAlignment(text, Pos.CENTER);
            }
        }
    }

    public class TextAlignmentUtils {
        public static void setAlignment(Text text, Pos alignment) {
            StackPane parentPane = (StackPane) text.getParent();
            parentPane.setAlignment(alignment);
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



