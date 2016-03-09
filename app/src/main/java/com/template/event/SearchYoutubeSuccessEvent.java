package com.template.event;

import android.databinding.ObservableArrayList;

import com.template.entity.SearchResult;

/**
 * Created by makoto on 2016/03/04.
 */
public class SearchYoutubeSuccessEvent {
    public ObservableArrayList<SearchResult> response;

    public SearchYoutubeSuccessEvent(ObservableArrayList<SearchResult> response) {
        this.response = response;
    }
}
