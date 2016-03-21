package com.template.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.template.api.YouTubeWebApi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static okhttp3.logging.HttpLoggingInterceptor.Level;

/**
 * Created by makoto on 2016/03/04.
 */
public class ContentsLoadService {
    public static final String HTTPS_WWW_YOUTUBE_COM = "https://www.youtube.com/";
    public static final String TARGET_PARAM = "url_encoded_fmt_stream_map";
    private Retrofit retrofit;

    public void loadYoutubeVideo(String query) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(Level.BASIC);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Gson gson = new GsonBuilder()
                .create();

        // RestAdapterを作成する
        retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(HTTPS_WWW_YOUTUBE_COM)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        YouTubeWebApi api = retrofit.create(YouTubeWebApi.class);
        api.getResult("ClP8oHdRygE")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            Observable.from(responseBody.string().split("&"))
                                    .filter(s2 -> s2.split("=")[0].equals(TARGET_PARAM))
                                    .subscribe(s -> {
                                        for (String s1 : s.split("=")) {
                                            try {
                                                Log.d("デバッグ before ", s1);
                                                String decode = java.net.URLDecoder.decode(s1, "UTF-8").replace("+", "%20");
                                                Log.d("デバッグ after ", decode);
                                                String decode2 = java.net.URLDecoder.decode(decode, "UTF-8").replace("+", "%20");
                                                Log.d("デバッグ re decoded ", decode2); // もう一回でコードしないと終わんないかも･･･

                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
