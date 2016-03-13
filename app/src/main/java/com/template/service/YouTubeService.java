package com.template.service;

import android.databinding.ObservableArrayList;
import android.support.v4.util.Pair;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.template.BuildConfig;
import com.template.api.YouTubeSearchApi;
import com.template.api.YouTubeVideoListApi;
import com.template.entity.SearchResult;
import com.template.entity.YoutubeSearchResponse;
import com.template.entity.YoutubeVideosResponse;
import com.template.event.SearchYoutubeSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.OkHttpClient;
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
public class YouTubeService {
    private static final String END_POINT = "https://www.googleapis.com";
    private ObservableArrayList<SearchResult> results = new ObservableArrayList<>();

    public void search() {
        search("オルフェンズの涙");
    }

    private ObservableArrayList<SearchResult> fetchVideoListResponse(Pair<YoutubeSearchResponse, YoutubeVideosResponse> pair) {
        List<SearchResult> searchResults = pair.first.getSearchResults();
        ObservableArrayList<SearchResult> resultList = new ObservableArrayList<>();

        for (int i = 0; i < searchResults.size(); i++) {
            searchResults.get(i).contentDetails = pair.second.item.get(i).contentDetails;
            resultList.add(searchResults.get(i));
        }
        return resultList;
    }

    private String joinVideoIds(YoutubeSearchResponse youtubeSearchResponse) {
        return Observable.from(youtubeSearchResponse.getSearchResults())
                .map(searchResult -> searchResult.id.kind.equals("youtube#video")
                        ? searchResult.id.videoId
                        : searchResult.id.playlistId)
                .reduce((s, s2) -> s + (s.isEmpty() ? "" : ",") + s2)
                .toBlocking()
                .single();
    }

    public void search(String query) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(Level.BODY);
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
        YouTubeVideoListApi videoListApi = retrofit.create(YouTubeVideoListApi.class);


        api.getResult(query, BuildConfig.PARSE_YOUTUBE_BROWSER_API_KEY)
                .flatMap(youtubeSearchResponse -> Observable.combineLatest(
                        Observable.just(youtubeSearchResponse),
                        videoListApi.getResult(joinVideoIds(youtubeSearchResponse), BuildConfig.PARSE_YOUTUBE_BROWSER_API_KEY),
                        Pair::create
                        )
                )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::fetchVideoListResponse)
                .subscribe(new Subscriber<ObservableArrayList<SearchResult>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ObservableArrayList<SearchResult> searchResults) {
                        results.addAll(searchResults);
                        EventBus.getDefault().post(new SearchYoutubeSuccessEvent(results));
                    }
                });
    }

    public void clearResults(){
        results.clear();
    }
}
