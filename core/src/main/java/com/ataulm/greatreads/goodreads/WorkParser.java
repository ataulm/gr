package com.ataulm.greatreads.goodreads;

import com.novoda.sax.Element;
import com.novoda.sax.EndElementListener;
import com.novoda.sexp.SimpleEasyXmlParser;
import com.novoda.sexp.finder.ElementFinder;
import com.novoda.sexp.finder.ElementFinderFactory;
import com.novoda.sexp.parser.ParseWatcher;
import com.novoda.sexp.parser.Parser;

public class WorkParser implements Parser<Work> {

    private final ElementFinder<Integer> idFinder;
    private final ElementFinder<Integer> booksCountFinder;
    private final ElementFinder<String> averageRatingFinder;
    private final ElementFinder<BestBook> bestBookFinder;

    public static WorkParser newInstance() {
        ElementFinderFactory eff = SimpleEasyXmlParser.getElementFinderFactory();
        return new WorkParser(
                eff.getIntegerFinder(),
                eff.getIntegerFinder(),
                eff.getStringFinder(),
                eff.getTypeFinder(BestBookParser.newInstance())
        );
    }

    private WorkParser(
            ElementFinder<Integer> idFinder,
            ElementFinder<Integer> booksCountFinder,
            ElementFinder<String> averageRatingFinder,
            ElementFinder<BestBook> bestBookFinder
    ) {
        this.idFinder = idFinder;
        this.booksCountFinder = booksCountFinder;
        this.averageRatingFinder = averageRatingFinder;
        this.bestBookFinder = bestBookFinder;
    }

    @Override
    public void parse(Element element, final ParseWatcher<Work> listener) {
        element.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                int id = idFinder.getResult();
                int booksCount = booksCountFinder.getResult();
                String averageRating = averageRatingFinder.getResult();
                BestBook bestBook = bestBookFinder.getResult();

                listener.onParsed(new Work(id, booksCount, averageRating, bestBook));
            }
        });

        idFinder.find(element, "id");
        booksCountFinder.find(element, "books_count");
        averageRatingFinder.find(element, "average_rating");
        bestBookFinder.find(element, "best_book");
    }

}
