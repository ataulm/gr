package com.ataulm.greatreads;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ataulm.greatreads.goodreads.GoodreadsResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyActivity extends AppCompatActivity {

    @BindView(R.id.response)
    TextView responseTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);

        responseTextView.setText("bro what's happening");

        final GoodreadsApi goodreadsApi = GoodreadsApi.newInstance(new OkHttpClient(), BuildConfig.GOODREADS_API_KEY, BuildConfig.GOODREADS_API_SECRET);
        Observable<GoodreadsResponse> search = goodreadsApi.search("harry potter");

        search.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(

                new Observer<GoodreadsResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        responseTextView.setText("error: " + e);
                        Log.e("!!!", "error", e);
                    }

                    @Override
                    public void onNext(GoodreadsResponse goodreadsResponse) {
                        responseTextView.setText(goodreadsResponse.toString());
                    }
                }
        );
    }

}
