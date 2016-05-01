package com.ataulm.greatreads.retrofit;

import com.novoda.sexp.SimpleEasyXmlParser;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class SimpleEasyXmlParserConverterFactory extends Converter.Factory {

    private final Streamers streamers;

    public static SimpleEasyXmlParserConverterFactory create(Streamers streamers) {
        return new SimpleEasyXmlParserConverterFactory(streamers);
    }

    private SimpleEasyXmlParserConverterFactory(Streamers streamers) {
        this.streamers = streamers;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (!(type instanceof Class)) {
            return null;
        }
        Class<?> cls = (Class<?>) type;
        return new SimpleEasyXmlParserResponseBodyConverter<>(cls, streamers);

    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        // TODO: don't really care for sending yet
        return null;
    }

    private static class SimpleEasyXmlParserResponseBodyConverter<T> implements Converter<ResponseBody, T> {

        private final Class<T> cls;
        private final Streamers streamers;

        SimpleEasyXmlParserResponseBodyConverter(Class<T> cls, Streamers streamers) {
            this.cls = cls;
            this.streamers = streamers;
        }

        @Override
        public T convert(ResponseBody responseBody) throws IOException {
            return SimpleEasyXmlParser.parse(responseBody.byteStream(), streamers.get(cls));
        }

    }

}
