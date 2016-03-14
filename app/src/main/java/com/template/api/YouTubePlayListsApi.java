package com.template.api;

import com.template.entity.YoutubePlaylistsResponse;
import com.template.entity.YoutubeVideosResponse;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by makoto on 2016/03/04.
 *
 */
public interface YouTubePlayListsApi {
    @GET("/youtube/v3/playlists?part=contentDetails")
    Observable<YoutubePlaylistsResponse> getResult(@Query("id") final String ids, @Query("key") final String key);
}
