package com.template;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.template.entity.Song;
import com.template.ui.adapter.ObservableSongListAdapter;

/**
 * Created by yoshida_makoto on 16/07/11.
 */
public class DataBindingAttributes {
    @BindingAdapter("songs")
    public static void setSongs(RecyclerView recyclerView, ObservableArrayList<Song> songs){
        if(recyclerView.getAdapter() == null){
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            ObservableSongListAdapter adapter = new ObservableSongListAdapter(songs);
            recyclerView.setAdapter(adapter);
            return;
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
