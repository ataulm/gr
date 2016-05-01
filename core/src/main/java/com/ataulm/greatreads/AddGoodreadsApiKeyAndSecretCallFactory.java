package com.ataulm.greatreads;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

class AddGoodreadsApiKeyAndSecretCallFactory implements okhttp3.Call.Factory {

    private final OkHttpClient okHttpClient;
    private final String apiKey;
    private final String apiSecret;

    AddGoodreadsApiKeyAndSecretCallFactory(OkHttpClient okHttpClient, String apiKey, String apiSecret) {
        this.okHttpClient = okHttpClient;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    @Override
    public okhttp3.Call newCall(Request request) {
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("key", apiKey)
                .addQueryParameter("secret", apiSecret)
                .build();

        Request updated = request.newBuilder()
                .url(url)
                .build();

        return okHttpClient.newCall(updated);
    }

}
