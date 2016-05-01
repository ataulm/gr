package com.ataulm.greatreads.goodreads;

public class Work {

    public final int id;
    public final int booksCount;
    public final String averageRating;
    public final BestBook bestBook;

    public Work(int id, int booksCount, String averageRating, BestBook bestBook) {
        this.id = id;
        this.booksCount = booksCount;
        this.averageRating = averageRating;
        this.bestBook = bestBook;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", booksCount=" + booksCount +
                ", averageRating='" + averageRating + '\'' +
                ", bestBook=" + bestBook +
                '}';
    }

}
