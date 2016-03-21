package com.template.event;

/**
 * Created by makoto on 2016/03/21.
 */
public class YoutubeVideoItemClickEvent {
    public String videoId;

    public YoutubeVideoItemClickEvent(final String videoId) {
        this.videoId = videoId;
    }
}
