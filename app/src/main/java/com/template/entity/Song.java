package com.template.entity;

/**
 * Created by yoshida_makoto on 16/07/11.
 */
public class Song {
    private long id;
    private String title;
    private String artist;

    public Song(final long id, final String title, final String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(final String artist) {
        this.artist = artist;
    }
}
