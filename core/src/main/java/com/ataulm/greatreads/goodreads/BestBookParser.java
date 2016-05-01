package com.ataulm.greatreads.goodreads;

import com.novoda.sax.Element;
import com.novoda.sax.EndElementListener;
import com.novoda.sexp.SimpleEasyXmlParser;
import com.novoda.sexp.finder.ElementFinder;
import com.novoda.sexp.finder.ElementFinderFactory;
import com.novoda.sexp.parser.ParseWatcher;
import com.novoda.sexp.parser.Parser;

import java.net.URI;

public class BestBookParser implements Parser<BestBook> {

    private final ElementFinder<Integer> idFinder;
    private final ElementFinder<String> titleFinder;
    private final ElementFinder<Author> authorFinder;
    private final ElementFinder<String> smallImageUriFinder;

    public static BestBookParser newInstance() {
        ElementFinderFactory eff = SimpleEasyXmlParser.getElementFinderFactory();
        return new BestBookParser(
                eff.getIntegerFinder(),
                eff.getStringFinder(),
                eff.getTypeFinder(AuthorParser.newInstance()),
                eff.getStringFinder()
        );
    }

    private BestBookParser(
            ElementFinder<Integer> idFinder,
            ElementFinder<String> titleFinder,
            ElementFinder<Author> authorFinder,
            ElementFinder<String> smallImageUriFinder
    ) {
        this.idFinder = idFinder;
        this.titleFinder = titleFinder;
        this.authorFinder = authorFinder;
        this.smallImageUriFinder = smallImageUriFinder;
    }

    @Override
    public void parse(Element element, final ParseWatcher<BestBook> listener) {
        element.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                int id = idFinder.getResult();
                String title = titleFinder.getResult();
                Author author = authorFinder.getResult();
                URI smallImageUri = URI.create(smallImageUriFinder.getResult());

                listener.onParsed(new BestBook(id, title, author, smallImageUri));
            }
        });

        idFinder.find(element, "id");
        titleFinder.find(element, "title");
        authorFinder.find(element, "author");
    }

}
