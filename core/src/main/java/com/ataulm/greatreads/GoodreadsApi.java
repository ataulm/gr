package com.ataulm.greatreads;

import com.ataulm.greatreads.goodreads.GoodreadsResponse;
import com.ataulm.greatreads.retrofit.SimpleEasyXmlParserConverterFactory;
import com.ataulm.greatreads.retrofit.Streamers;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;

public class GoodreadsApi {

    private final Api api;

    public static GoodreadsApi newInstance(OkHttpClient okHttpClient, String apiKey, String apiSecret) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.goodreads.com/")
                .addConverterFactory(createXmlConverterFactory())
                .callFactory(new AddGoodreadsApiKeyAndSecretCallFactory(okHttpClient, apiKey, apiSecret))
                .build();

        Api api = retrofit.create(Api.class);
        return new GoodreadsApi(api);
    }

    private static SimpleEasyXmlParserConverterFactory createXmlConverterFactory() {
        Streamers streamers = new Streamers()
                .put(GoodreadsResponse.class, GoodreadsResponseStreamer.newInstance());
        return SimpleEasyXmlParserConverterFactory.create(streamers);
    }

    private GoodreadsApi(Api api) {
        this.api = api;
    }

    public Observable<GoodreadsResponse> search(String query) {
        Call<GoodreadsResponse> call = api.searchFor(query);
        return observableFrom(call);
    }

    private static <T> Observable<T> observableFrom(final Call<T> call) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {

                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            makeCall(subscriber);
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                    }

                    private void makeCall(Subscriber<? super T> subscriber) throws IOException {
                        Response<T> response = call.execute();
                        if (response.isSuccessful()) {
                            subscriber.onNext(response.body());
                            subscriber.onCompleted();
                        } else {
                            HttpException httpException = new HttpException(response.code());
                            subscriber.onError(httpException);
                        }
                    }

                }
        );
    }

    private interface Api {

        @GET("search.xml")
        Call<GoodreadsResponse> searchFor(@Query("q") String query);

    }

}
