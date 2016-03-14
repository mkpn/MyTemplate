package com.template.service;

import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.template.BuildConfig;
import com.template.Tuple3;
import com.template.api.YouTubePlayListsApi;
import com.template.api.YouTubeSearchApi;
import com.template.api.YouTubeVideoListApi;
import com.template.constants.YoutubeConstants;
import com.template.entity.SearchResult;
import com.template.entity.YoutubePlaylistsResponse;
import com.template.entity.YoutubeSearchResponse;
import com.template.entity.YoutubeVideosResponse;
import com.template.event.SearchYoutubeSuccessEvent;

import org.greenrobot.eventbus.EventBus;

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
    private Retrofit retrofit;

    private ObservableArrayList<SearchResult> fetchVideoListResponse(Pair<YoutubeVideosResponse, YoutubePlaylistsResponse> pair) {
//        List<SearchResult> searchResults = pair.first.getSearchResults();
//        ObservableArrayList<SearchResult> resultList = new ObservableArrayList<>();
//
//        for (int i = 0; i < searchResults.size(); i++) {
//            searchResults.get(i).contentDetails = pair.second.items.get(i).contentDetails;
//            resultList.add(searchResults.get(i));
//        }
//        return resultList;

        return null;
    }

    private String joinVideoIds(YoutubeSearchResponse youtubeSearchResponse) {
        return Observable.from(youtubeSearchResponse.getSearchResults())
                .map(searchResult -> searchResult.id.kind.equals("youtube#video")
                        ? searchResult.id.videoId
                        : searchResult.id.playlistId)
                .reduce(this::joinIds)
                .toBlocking()
                .single();
    }

    @NonNull
    private String joinIds(String s, String s2) {
        return (TextUtils.isEmpty(s) ? "" : s) + (TextUtils.isEmpty(s) ? "" : ",") + s2;
    }

    public Observable<YoutubePlaylistsResponse> getPlayList(YoutubeSearchResponse youtubeSearchResponse) {
        String playlistIds = Observable.from(youtubeSearchResponse.getSearchResults())
                .map(searchResult -> searchResult.id.playlistId)
                .reduce(this::joinIds)
                .toBlocking()
                .single();

        if (TextUtils.isEmpty(playlistIds)) return null;

        YouTubePlayListsApi playListsApi = retrofit.create(YouTubePlayListsApi.class);

        return playListsApi.getResult(playlistIds, BuildConfig.PARSE_YOUTUBE_BROWSER_API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<YoutubeVideosResponse> getVideos(YoutubeSearchResponse youtubeSearchResponse) {
        String videoIds = Observable.from(youtubeSearchResponse.getSearchResults())
                .map(searchResult -> searchResult.id.videoId)
                .reduce((s, s2) -> s + (s.isEmpty() ? "" : ",") + s2)
                .toBlocking()
                .single();

        YouTubeVideoListApi videoListApi = retrofit.create(YouTubeVideoListApi.class);

        if (TextUtils.isEmpty(videoIds)) return null;

        return videoListApi.getResult(videoIds, BuildConfig.PARSE_YOUTUBE_BROWSER_API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private String joinChannelId(YoutubeSearchResponse youtubeSearchResponse) {
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
        retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(END_POINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        YouTubeSearchApi api = retrofit.create(YouTubeSearchApi.class);
        api.getResult(query, BuildConfig.PARSE_YOUTUBE_BROWSER_API_KEY)
                .flatMap(youtubeSearchResponse -> Observable.combineLatest(
                        Observable.just(youtubeSearchResponse),
                        getVideos(youtubeSearchResponse),
                        getPlayList(youtubeSearchResponse),
                        Tuple3::create
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
                    public void onNext(ObservableArrayList observableArrayList) {
                        results.addAll(observableArrayList);
                        EventBus.getDefault().post(new SearchYoutubeSuccessEvent(results));
                    }
                });
    }

    private ObservableArrayList<SearchResult> fetchVideoListResponse(Tuple3<YoutubeSearchResponse,
            YoutubeVideosResponse, YoutubePlaylistsResponse> tuple3) {

        YoutubeSearchResponse youtubeSearchResponse = tuple3.first;
        YoutubeVideosResponse youtubeVideosResponse = tuple3.second;
        YoutubePlaylistsResponse youtubePlaylistsResponse = tuple3.third;

        ObservableArrayList<SearchResult> list = new ObservableArrayList<>();
        for (SearchResult searchResult : youtubeSearchResponse.getSearchResults()) {
            switch (searchResult.id.kind) {
                case YoutubeConstants.YOUTUBE_VIDEO:
                    searchResult.contentDetails = youtubeVideosResponse.items.get(0).contentDetails;
                    youtubeVideosResponse.items.remove(0);
                    list.add(searchResult);
                    break;
                case YoutubeConstants.YOUTUBE_PLAYLIST:
                    searchResult.playList = youtubePlaylistsResponse.items.get(0).contentDetails;
                    youtubePlaylistsResponse.items.remove(0);
                    list.add(searchResult);
            }
        }
        return list;
    }

    public void clearResults() {
        results.clear();
    }
}
