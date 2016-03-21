package com.template.ui.viewmodel;

import android.databinding.ObservableArrayList;

import com.template.entity.SearchResult;

/**
 * Created by makoto on 2016/02/15.
 */
public class YoutubeSearchViewModel {

    private ObservableArrayList<SearchResult> searchResults;

    public YoutubeSearchViewModel(ObservableArrayList<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }


}
