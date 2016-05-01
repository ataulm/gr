package com.ataulm.greatreads.goodreads;

import java.util.List;

public class Results {

    public final List<Work> works;

    public Results(List<Work> works) {
        this.works = works;
    }

    @Override
    public String toString() {
        return "Results{" +
                "works=" + works +
                '}';
    }
}
