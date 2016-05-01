package com.ataulm.greatreads.goodreads;

import com.novoda.sax.Element;
import com.novoda.sax.EndElementListener;
import com.novoda.sexp.SimpleEasyXmlParser;
import com.novoda.sexp.finder.ElementFinder;
import com.novoda.sexp.finder.ElementFinderFactory;
import com.novoda.sexp.parser.ParseWatcher;
import com.novoda.sexp.parser.Parser;

public class AuthorParser implements Parser<Author> {

    private final ElementFinder<Integer> idFinder;
    private final ElementFinder<String> nameFinder;

    public static AuthorParser newInstance() {
        ElementFinderFactory eff = SimpleEasyXmlParser.getElementFinderFactory();
        return new AuthorParser(eff.getIntegerFinder(), eff.getStringFinder());
    }

    private AuthorParser(ElementFinder<Integer> idFinder, ElementFinder<String> nameFinder) {
        this.idFinder = idFinder;
        this.nameFinder = nameFinder;
    }

    @Override
    public void parse(Element element, final ParseWatcher<Author> listener) {
        element.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                int id = idFinder.getResult();
                String name = nameFinder.getResult();

                listener.onParsed(new Author(id, name));
            }
        });

        idFinder.find(element, "id");
        nameFinder.find(element, "name");
    }

}
