package com.template.ui.activity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.MediaController;

import com.template.MusicController;
import com.template.R;
import com.template.databinding.ActivityListBindingBinding;
import com.template.entity.Song;
import com.template.event.SongSelectEvent;
import com.template.service.MusicService;

import java.util.Collections;

public class ListBindingActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {

    private MusicController musicController;

    private ActivityListBindingBinding binding;

    private ObservableArrayList<Song> songList;
    private MusicService musicService;
    private boolean isMusicBounded;
    private Intent playIntent;
    private ServiceConnection musicConnection;
    private boolean isPlayerPaused = false;
    private boolean isPlaybackPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_binding);


        songList = getSongList();
        Collections.sort(songList, (a, b) -> a.getTitle().compareTo(b.getTitle()));
        binding.setSongs(songList);

        musicConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(final ComponentName name, final IBinder service) {
                MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
                musicService = binder.getService();
                musicService.setSongs(songList);
                isMusicBounded = true;
            }

            @Override
            public void onServiceDisconnected(final ComponentName name) {
                isMusicBounded = false;
            }
        };

        musicController = new MusicController(this);

        musicController.setPrevNextListeners(v -> playNext(), v -> playPrev());
        musicController.setMediaPlayer(this);
        musicController.setAnchorView(binding.recyclerview);
        musicController.setEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlayerPaused) {
            setController();
            isPlayerPaused = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPlayerPaused = true;
    }

    @Override
    protected void onStop() {
        musicController.hide();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicService = null;
        super.onDestroy();
    }

    private void setController() {

    }

    @org.greenrobot.eventbus.Subscribe
    public void songPicked(SongSelectEvent event) {
        musicService.setSongPosition(event.songId);
        musicService.playSong();
        if(isPlaybackPaused){
            setController();
            isPlaybackPaused = false;
        }
        musicController.show();
    }

    public ObservableArrayList<Song> getSongList() {
        //retrieve song info
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        ObservableArrayList<Song> songs = new ObservableArrayList<>();

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);

            int idColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);

            int artistColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songs.add(new Song(thisId, thisTitle, thisArtist));
            }
            while (musicCursor.moveToNext());
        }
        return songs;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_shuffle:
                //shuffle
                musicService.setShuffleMode();
                break;
            case R.id.action_end:
                stopService(playIntent);
                musicService = null;
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {
        isPlaybackPaused = true;
        musicService.pausePlayer();
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (musicService != null && isMusicBounded && musicService.isPng())
            return musicService.getPosn();
        else return 0;
    }

    @Override
    public void seekTo(final int pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    private void playNext(){
        musicService.playNext();
        if(isPlaybackPaused){
            setController();
            isPlaybackPaused=false;
        }
        musicController.show(0);
    }

    private void playPrev(){
        musicService.playPrev();
        if(isPlaybackPaused){
            setController();
            isPlaybackPaused=false;
        }
        musicController.show(0);
    }
}
