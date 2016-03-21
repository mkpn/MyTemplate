package com.template.api;

import com.template.entity.YoutubeChannelsResponse;
import com.template.entity.YoutubePlaylistsResponse;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by makoto on 2016/03/04.
 *
 */
public interface YouTubeChannelsApi {
    @GET("youtube/v3/channels?part=snippet")
    Observable<YoutubeChannelsResponse> getResult(@Query("id") final String ids, @Query("key") final String key);
}
