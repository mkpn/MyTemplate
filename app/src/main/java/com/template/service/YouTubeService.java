package com.template.service;

import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.template.BuildConfig;
import com.template.Tuple3;
import com.template.Tuple4;
import com.template.api.YouTubeChannelsApi;
import com.template.api.YouTubePlayListsApi;
import com.template.api.YouTubeSearchApi;
import com.template.api.YouTubeVideoListApi;
import com.template.constants.YoutubeConstants;
import com.template.entity.SearchResult;
import com.template.entity.YoutubeChannelsResponse;
import com.template.entity.YoutubePlaylistsResponse;
import com.template.entity.YoutubeSearchResponse;
import com.template.entity.YoutubeVideosResponse;
import com.template.event.SearchYoutubeSuccessEvent;
import com.template.helper.CreateGsonDefinitionHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

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
public class YouTubeService {
    private static final String END_POINT = "https://www.googleapis.com";
    private ObservableArrayList<SearchResult> results = new ObservableArrayList<>();
    private Retrofit retrofit;

    @NonNull
    private String joinIds(String s, String s2) {
        return (TextUtils.isEmpty(s) ? "" : s) + (TextUtils.isEmpty(s) ? "" : ",") + s2;
    }

    private Observable<YoutubePlaylistsResponse> getPlayList(YoutubeSearchResponse youtubeSearchResponse) {
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
                .reduce(this::joinIds)
                .toBlocking()
                .single();

        YouTubeVideoListApi videoListApi = retrofit.create(YouTubeVideoListApi.class);

        if (TextUtils.isEmpty(videoIds)) return null;

        return videoListApi.getResult(videoIds, BuildConfig.PARSE_YOUTUBE_BROWSER_API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<YoutubeChannelsResponse> getChannels(YoutubeSearchResponse youtubeSearchResponse) {
        String channelIds = Observable.from(youtubeSearchResponse.getSearchResults())
                .map(searchResult -> searchResult.id.channelId)
                .reduce(this::joinIds)
                .toBlocking()
                .single();

        if (TextUtils.isEmpty(channelIds)) return null;

        YouTubeChannelsApi channelsApi = retrofit.create(YouTubeChannelsApi.class);

        return channelsApi.getResult(channelIds, BuildConfig.PARSE_YOUTUBE_BROWSER_API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void search(String query) {
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
                        getChannels(youtubeSearchResponse),
                        Tuple4::create
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

    private ObservableArrayList<SearchResult> fetchVideoListResponse(Tuple4<YoutubeSearchResponse, YoutubeVideosResponse, YoutubePlaylistsResponse, YoutubeChannelsResponse> tuple4) {

        YoutubeSearchResponse youtubeSearchResponse = tuple4.first;
        YoutubeVideosResponse youtubeVideosResponse = tuple4.second;
        YoutubePlaylistsResponse youtubePlaylistsResponse = tuple4.third;
        YoutubeChannelsResponse youtubeChannelsResponse = tuple4.forth;

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
                    break;
                case YoutubeConstants.YOUTUBE_CHANNEL:
                    searchResult.channel = youtubeChannelsResponse.items.get(0).snippet;
                    youtubeChannelsResponse.items.remove(0);
                    list.add(searchResult);
                    break;
            }
        }
        return list;
    }

    public void clearResults() {
        results.clear();
    }
}
