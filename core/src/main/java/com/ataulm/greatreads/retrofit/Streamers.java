package com.ataulm.greatreads.retrofit;

import com.novoda.sexp.Streamer;

import java.util.HashMap;
import java.util.Map;

public class Streamers {

    private final Map<Class<?>, Streamer<?>> streamers = new HashMap<>();

    public <T> Streamers put(Class<T> cls, Streamer<T> streamer) {
        streamers.put(cls, streamer);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> Streamer<T> get(Class<T> cls) {
        return (Streamer<T>) streamers.get(cls);
    }

}
