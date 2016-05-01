package com.ataulm.greatreads;

import com.ataulm.greatreads.goodreads.GoodreadsResponse;
import com.ataulm.greatreads.goodreads.GoodreadsResponseParser;
import com.novoda.sax.RootElement;
import com.novoda.sexp.RootTag;
import com.novoda.sexp.SimpleEasyXmlParser;
import com.novoda.sexp.Streamer;
import com.novoda.sexp.finder.ElementFinder;
import com.novoda.sexp.finder.ElementFinderFactory;

class GoodreadsResponseStreamer implements Streamer<GoodreadsResponse> {

    private final ElementFinder<GoodreadsResponse> elementFinder;

    public static GoodreadsResponseStreamer newInstance() {
        ElementFinderFactory eff = SimpleEasyXmlParser.getElementFinderFactory();
        ElementFinder<GoodreadsResponse> typeFinder = eff.getTypeFinder(GoodreadsResponseParser.newInstance());
        return new GoodreadsResponseStreamer(typeFinder);
    }

    private GoodreadsResponseStreamer(ElementFinder<GoodreadsResponse> elementFinder) {
        this.elementFinder = elementFinder;
    }

    @Override
    public RootTag getRootTag() {
        return RootTag.create("GoodreadsResponse");
    }

    @Override
    public void stream(RootElement rootElement) {
        elementFinder.find(rootElement, "");
    }

    @Override
    public GoodreadsResponse getStreamResult() {
        return elementFinder.getResult();
    }

}
