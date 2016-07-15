package com.template.util;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.template.entity.SearchResult;
import com.template.entity.Song;
import com.template.ui.adapter.ObservableSongListAdapter;
import com.template.ui.adapter.YoutubeSearchResultListAdapter;

/**
 * Created by makoto on 2016/03/09.
 */
public class BindingAttributesUtil {
    @BindingAdapter("thumbnailUrl")
    public static void setThumbnail(ImageView imageView, String thumbnailUrl){
        Glide.with(imageView.getContext()).load(thumbnailUrl).into(imageView);
    }


    @BindingAdapter("searchResults")
    public static void setSearchResults(RecyclerView recyclerView, ObservableArrayList<SearchResult> searchResults) {
        if(searchResults == null) return;
        if (recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            YoutubeSearchResultListAdapter adapter = new YoutubeSearchResultListAdapter(searchResults);
            recyclerView.setAdapter(adapter);
            return;
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

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
