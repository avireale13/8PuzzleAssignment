package com.example.demo;

public class SearchStats {
    // Tracks the number of nodes visited during the search
    private int nodesVisited;

    // Stores the total runtime of the search algorithm
    private long runtime;

    // Initializes the statistics to zero
    public SearchStats() {
        this.nodesVisited = 0;
        this.runtime = 0;
    }

    // Increments the count of nodes visited
    public void incrementNodesVisited() {
        nodesVisited++;
    }

    // Sets the total runtime of the search algorithm
    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    // Retrieves the number of nodes visited
    public int getNodesVisited() {
        return nodesVisited;
    }

    // Retrieves the total runtime in milliseconds
    public long getRuntime() {
        return runtime;
    }
}

