package com.ataulm.greatreads.goodreads;

public class Search {

    public final Results results;

    public Search(Results results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Search{" +
                "results=" + results +
                '}';
    }
}
