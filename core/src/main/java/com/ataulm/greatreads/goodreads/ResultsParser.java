package com.ataulm.greatreads.goodreads;

import com.novoda.sax.Element;
import com.novoda.sax.EndElementListener;
import com.novoda.sexp.SimpleEasyXmlParser;
import com.novoda.sexp.finder.ElementFinder;
import com.novoda.sexp.finder.ElementFinderFactory;
import com.novoda.sexp.parser.ParseWatcher;
import com.novoda.sexp.parser.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultsParser implements Parser<Results> {

    private final ElementFinder<Work> workFinder;
    private final List<Work> workingList;

    public static ResultsParser newInstance() {
        ElementFinderFactory eff = SimpleEasyXmlParser.getElementFinderFactory();
        final List<Work> workingListLol = new ArrayList<>();
        return new ResultsParser(eff.getListElementFinder(WorkParser.newInstance(), new ParseWatcher<Work>() {
            @Override
            public void onParsed(Work item) {
                workingListLol.add(item);
            }
        }), Collections.unmodifiableList(workingListLol));
    }

    private ResultsParser(ElementFinder<Work> workFinder, List<Work> workingList) {
        this.workFinder = workFinder;
        this.workingList = workingList;
    }

    @Override
    public void parse(Element element, final ParseWatcher<Results> listener) {
        element.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                listener.onParsed(new Results(workingList));
            }
        });
        workFinder.find(element, "work");
    }

}
