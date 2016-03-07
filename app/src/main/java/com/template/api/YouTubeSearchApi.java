package com.template.api;

import com.template.entity.YoutubeSearchResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by makoto on 2016/03/04.
 *
 * https://www.googleapis.com/youtube/v3/search?part=snippet&q=foo&key={YOUR_API_KEY}
 */
public interface YouTubeSearchApi {
    @GET("/youtube/v3/search?part=snippet")
    Observable<YoutubeSearchResponse> getResult(@Query("q") final String searchQuery, @Query("key") final String key);
}
