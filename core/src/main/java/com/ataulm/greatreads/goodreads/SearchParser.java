package com.ataulm.greatreads.goodreads;

import com.novoda.sax.Element;
import com.novoda.sax.EndElementListener;
import com.novoda.sexp.SimpleEasyXmlParser;
import com.novoda.sexp.finder.ElementFinder;
import com.novoda.sexp.finder.ElementFinderFactory;
import com.novoda.sexp.parser.ParseWatcher;
import com.novoda.sexp.parser.Parser;

public class SearchParser implements Parser<Search> {

    private final ElementFinder<Results> resultsFinder;

    public static SearchParser newInstance() {
        ElementFinderFactory eff = SimpleEasyXmlParser.getElementFinderFactory();
        return new SearchParser(eff.getTypeFinder(ResultsParser.newInstance()));
    }

    private SearchParser(ElementFinder<Results> resultsFinder) {
        this.resultsFinder = resultsFinder;
    }

    @Override
    public void parse(Element element, final ParseWatcher<Search> listener) {
        element.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                Results results = resultsFinder.getResult();
                listener.onParsed(new Search(results));
            }
        });

        resultsFinder.find(element, "results");
    }

}
