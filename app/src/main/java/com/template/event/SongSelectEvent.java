package com.template.event;

/**
 * Created by yoshida_makoto on 16/07/12.
 */
public class SongSelectEvent {
    public int songPosition;

    public SongSelectEvent(final int songPosition) {
        this.songPosition = songPosition;
    }
}
