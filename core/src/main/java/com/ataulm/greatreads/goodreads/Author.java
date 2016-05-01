package com.ataulm.greatreads.goodreads;

public class Author {

    public final int id;
    public final String name;

    public Author(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
