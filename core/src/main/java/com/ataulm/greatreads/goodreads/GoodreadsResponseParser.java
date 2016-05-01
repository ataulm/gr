package com.ataulm.greatreads.goodreads;

import com.novoda.sax.Element;
import com.novoda.sax.EndElementListener;
import com.novoda.sexp.SimpleEasyXmlParser;
import com.novoda.sexp.finder.ElementFinder;
import com.novoda.sexp.finder.ElementFinderFactory;
import com.novoda.sexp.parser.ParseWatcher;
import com.novoda.sexp.parser.Parser;

public class GoodreadsResponseParser implements Parser<GoodreadsResponse> {

    private final ElementFinder<Search> searchFinder;

    public static GoodreadsResponseParser newInstance() {
        ElementFinderFactory eff = SimpleEasyXmlParser.getElementFinderFactory();
        return new GoodreadsResponseParser(eff.getTypeFinder(SearchParser.newInstance()));
    }

    private GoodreadsResponseParser(ElementFinder<Search> searchFinder) {
        this.searchFinder = searchFinder;
    }

    @Override
    public void parse(Element element, final ParseWatcher<GoodreadsResponse> listener) {
        element.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                Search search = searchFinder.getResult();
                listener.onParsed(new GoodreadsResponse(search));
            }
        });

        searchFinder.find(element, "results");
    }

}
