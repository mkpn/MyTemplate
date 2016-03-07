package com.template.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.template.BuildConfig;
import com.template.api.YouTubeSearchApi;
import com.template.entity.YoutubeSearchResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by makoto on 2016/03/04.
 */
public class YouTubeService {
    private static final String END_POINT = "https://www.googleapis.com";

    public void search() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
//        logging.setLevel(Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!


        Gson gson = new GsonBuilder()
                .create();

        // RestAdapterを作成する
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(END_POINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        YouTubeSearchApi api = retrofit.create(YouTubeSearchApi.class);

        api.getResult("オルフェンズの涙", BuildConfig.PARSE_YOUTUBE_BROWSER_API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<YoutubeSearchResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(YoutubeSearchResponse youtubeSearchResponse) {
                    }
                });
    }
}
