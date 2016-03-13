package com.template.util;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.template.entity.SearchResult;
import com.template.view.adapter.YoutubeSearchResultListAdapter;

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
}
