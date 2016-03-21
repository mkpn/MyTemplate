package com.template.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by makoto on 2016/03/17.
 */
public class YoutubeChannelsResponse {
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
    public List<Channel> items;
    // TODO 単数形になってるか確認すること

    public class Channel {
        @Expose
        public String kind;
        @Expose
        public String etag;
        @Expose
        public String id;
        @Expose
        public Snippet snippet;
        public class Snippet {
            @Expose
            public String title;
            @Expose
            public String description;
            @Expose
            public String publishedAt;
            @Expose
            public Thumbnails thumbnails;
            public class Thumbnails {
                @Expose
                @SerializedName("default")
                public Default defaultThumb;

                public class Default {
                    @Expose
                    public String url;
                }
                @Expose
                public Medium medium;
                public class Medium {
                    @Expose
                    public String url;
                }
                @Expose
                public High high;
                public class High {
                    @Expose
                    public String url;
                }
            }
            @Expose
            public Localized localized;
            public class Localized {
                @Expose
                public String title;
                @Expose
                public String description;
            }
        }
    }
}
