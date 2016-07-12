package com.template.event;

/**
 * Created by yoshida_makoto on 16/07/12.
 */
public class SongSelectEvent {
    public int songId;

    public SongSelectEvent(final int songId) {
        this.songId = songId;
    }
}
