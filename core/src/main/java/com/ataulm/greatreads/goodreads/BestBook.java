package com.ataulm.greatreads.goodreads;

import java.net.URI;

public class BestBook {

    public final int id;
    public final String title;
    public final Author author;

    public final URI smallImageUri;

    public BestBook(int id, String title, Author author, URI smallImageUri) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.smallImageUri = smallImageUri;
    }

    @Override
    public String toString() {
        return "BestBook{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", smallImageUri=" + smallImageUri +
                '}';
    }

}
