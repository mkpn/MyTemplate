package com.template.service;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.template.api.WeatherApi;
import com.template.entity.PinpointLocations;
import com.template.entity.Weather;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by makoto on 2016/03/04.
 */
public class WeatherService {
    private final OkHttpClient client = new OkHttpClient();

    private static final String END_POINT = "http://weather.livedoor.com";

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(response.body().string());
            }
        });
    }

    public void getWeather() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        // RestAdapterを作成する
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(END_POINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        // 天気予報情報を取得する
        //http://weather.livedoor.com/area/forecast/200010
        WeatherApi api = retrofit.create(WeatherApi.class);

        api.getWeather("200010")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Weather>() {
                    @Override
                    public void onCompleted() {
                        Log.d("デバッグ", "onComplete! weather ");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Weather weather) {
                        Log.d("デバッグ", "on?Next weather ");
                        if (weather != null) {
                            for (PinpointLocations location : weather.getPinpointLocations()) {
                                Log.d("デバッグ", "name is " + location.getName());
                            }
                        }
                    }
                });
    }
}
