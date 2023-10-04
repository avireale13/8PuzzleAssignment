package com.example.demo;

public class SearchStats {
    private int nodesVisited;
    private long runtime;

    public SearchStats() {
        this.nodesVisited = 0;
        this.runtime = 0;
    }

    public void incrementNodesVisited() {
        nodesVisited++;
    }

    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    public int getNodesVisited() {
        return nodesVisited;
    }

    public long getRuntime() {
        return runtime;
    }
}
