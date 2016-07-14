package com.template.service;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import com.template.entity.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by yoshida_makoto on 16/07/12.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    //media player
    private MediaPlayer player;
    //song list
    private ArrayList<Song> songs;
    //current position
    private int songPosition;

    private boolean isShuffleMode = false;
    private Random rand;

    private final IBinder musicBinder = new MusicBinder();

    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        return musicBinder;
    }

    @Override
    public boolean onUnbind(final Intent intent) {
        player.stop();
        player.release();
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        rand = new Random();
        songPosition = 0;
        initMusicPlayer();
    }

    public void setShuffleMode() {
        isShuffleMode = !isShuffleMode;
    }

    public void initMusicPlayer() {
        player = new MediaPlayer();
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    public void playNext() {
        if (isShuffleMode) {
            int newSong = songPosition;
            while (newSong == songPosition) {
                newSong = rand.nextInt(songs.size());
            }
            songPosition = newSong;
        } else {
            songPosition++;
            if (songPosition >= songs.size()) songPosition = 0;
        }
        playSong();
    }

    @Override
    public void onCompletion(final MediaPlayer mp) {
        if (player.getCurrentPosition() > 0) {
            mp.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(final MediaPlayer mp, final int what, final int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(final MediaPlayer mp) {
        mp.start();
    }

    public void setSongs(final ArrayList<Song> songs) {
        this.songs = songs;
    }

    public void setSongPosition(int songIndex) {
        songPosition = songIndex;
    }

    public void playSong() {
        player.reset();
        Song playSong = songs.get(songPosition);
        long currentSongId = playSong.getId();
        Uri trackUri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currentSongId);

        try {
            player.setDataSource(getApplicationContext(), trackUri);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }

        player.prepareAsync();
    }

    public void playPrev() {
        songPosition--;
        if (songPosition >= 0) songPosition = songs.size() - 1;
        playSong();
    }

    public int getPosn() {
        return player.getCurrentPosition();
    }

    public int getDur() {
        return player.getDuration();
    }

    public boolean isPng() {
        return player.isPlaying();
    }

    public void pausePlayer() {
        player.pause();
    }

    public void seek(int posn) {
        player.seekTo(posn);
    }

    public void go() {
        player.start();
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
