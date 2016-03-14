package com.template.entity;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by makoto on 2016/03/15.
 */
public class YoutubePlaylistsResponse {
    @Expose
    public String kind;
    @Expose
    public String etag;
    @Expose
    public PageInfo pageInfo;
    public class PageInfo {
        @Expose
        public int totalResults;
        @Expose
        public int resultsPerPage;
    }
    @Expose
    public List<YoutubePlaylist> items;
    // TODO 単数形になってるか確認すること
    public class YoutubePlaylist {
        @Expose
        public String kind;
        @Expose
        public String etag;
        @Expose
        public String id;
        @Expose
        public ContentDetails contentDetails;
        public class ContentDetails {
            @Expose
            public int itemCount;
        }
    }
}