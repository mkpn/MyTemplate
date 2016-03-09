package com.template.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by makoto on 2016/03/10.
 */
public class YoutubeVideosResponse {
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
    @SerializedName("items")
    public List<Item> item;

    public class Item {
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
            public String duration;
            @Expose
            public String dimension;
            @Expose
            public String definition;
            @Expose
            public String caption;
            @Expose
            public boolean licensedContent;
        }
    }
}
