package com.template.api;

import com.template.entity.YoutubeVideosResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by makoto on 2016/03/04.
 *
 * https://www.googleapis.com/youtube/v3/search?part=snippet&q=foo&key={YOUR_API_KEY}
 */
public interface YouTubeVideoListApi {
    @GET("/youtube/v3/videos?part=contentDetails")
    Observable<YoutubeVideosResponse> getResult(@Query("id") final String ids, @Query("key") final String key);
}
