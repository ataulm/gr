package com.ataulm.greatreads;

import com.ataulm.greatreads.goodreads.GoodreadsResponse;
import com.ataulm.greatreads.goodreads.Search;
import com.ataulm.greatreads.goodreads.SearchParser;
import com.novoda.sax.Element;
import com.novoda.sax.RootElement;
import com.novoda.sexp.RootTag;
import com.novoda.sexp.SimpleEasyXmlParser;
import com.novoda.sexp.Streamer;
import com.novoda.sexp.finder.ElementFinder;
import com.novoda.sexp.finder.ElementFinderFactory;

class GoodreadsResponseStreamer implements Streamer<GoodreadsResponse> {

    private final GoodreadsResponseParser parser;

    public static GoodreadsResponseStreamer newInstance() {
        return new GoodreadsResponseStreamer(GoodreadsResponseParser.newInstance());
    }

    private GoodreadsResponseStreamer(GoodreadsResponseParser parser) {
        this.parser = parser;
    }

    @Override
    public RootTag getRootTag() {
        return RootTag.create("GoodreadsResponse");
    }

    @Override
    public void stream(RootElement rootElement) {
        parser.parse(rootElement);
    }

    @Override
    public GoodreadsResponse getStreamResult() {
        return parser.getResult();
    }

    private static class GoodreadsResponseParser {

        private final ElementFinder<Search> searchFinder;

        public static GoodreadsResponseParser newInstance() {
            ElementFinderFactory eff = SimpleEasyXmlParser.getElementFinderFactory();
            return new GoodreadsResponseParser(eff.getTypeFinder(SearchParser.newInstance()));
        }

        private GoodreadsResponseParser(ElementFinder<Search> searchFinder) {
            this.searchFinder = searchFinder;
        }

        public void parse(Element element) {
            searchFinder.find(element, "search");
        }

        public GoodreadsResponse getResult() {
            return new GoodreadsResponse(searchFinder.getResult());
        }

    }

}
