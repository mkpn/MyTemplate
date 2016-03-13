package com.template.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by makoto on 2016/03/10.
 */
public class SearchResult {
    @Expose
    public String kind;
    @Expose
    public String etag;
    @Expose
    public Id id;

    public YoutubeVideosResponse.Item.ContentDetails contentDetails;

    public class Id {
        @Expose
        public String kind;
        @Expose
        public String videoId = "";
        @Expose
        public String playlistId = "";
    }

    @Expose
    public Snippet snippet;

    public class Snippet {
        @Expose
        public String publishedAt;

        @Expose
        public String channelId;

        @Expose
        public String title;

        @Expose
        public String description;

        @Expose
        public Thumbnails thumbnails;

        public class Thumbnails {
            @Expose
            @SerializedName("default")
            public Default defaultThumb;

            public class Default {
                @Expose
                public String url;

                @Expose
                public int width;

                @Expose
                public int height;
            }

            @Expose
            public Medium medium;

            public class Medium {
                @Expose
                public String url;
                @Expose
                public int width;
                @Expose
                public int height;
            }

            @Expose
            public High high;

            public class High {
                @Expose
                public String url;
                @Expose
                public int width;
                @Expose
                public int height;
            }
        }

        @Expose
        public String channelTitle;
        @Expose
        public String liveBroadcastContent;

    }
}
