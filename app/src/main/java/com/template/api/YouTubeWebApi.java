package com.template.api;

import com.template.entity.YoutubeVideosResponse;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by makoto on 2016/03/04.
 *
 * https://www.googleapis.com/youtube/v3/search?part=snippet&q=foo&key={YOUR_API_KEY}
 */
public interface YouTubeWebApi {
    @GET("get_video_info")
    Observable<ResponseBody> getResult(@Query("video_id") final String id);
}
