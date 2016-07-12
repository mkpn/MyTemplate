package com.template.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.template.R;
import com.template.constants.YoutubeConstants;
import com.template.databinding.YoutubeSearchResultItemBinding;
import com.template.entity.SearchResult;
import com.template.event.YoutubeVideoItemClickEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by makoto on 2016/02/15.
 */
public class YoutubeSearchResultListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ObservableArrayList<SearchResult> searchResults;
    private int viewCount;

    @Override
    public int getItemViewType(int position) {
        SearchResult searchResult = searchResults.get(position);
        switch (searchResult.id.kind) {
            case YoutubeConstants.YOUTUBE_VIDEO:
                return 1;
            case YoutubeConstants.YOUTUBE_PLAYLIST:
                return 2;
            case YoutubeConstants.YOUTUBE_CHANNEL:
                return 3;
            default:
                return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_search_result_item, parent, false);
                return new YoutubeVideoViewHolder(v);
            case 2:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_search_result_item, parent, false);
                return new YoutubePlaylistViewHolder(v1);
            case 3:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_search_result_item, parent, false);
                return new YoutubeChannelViewHolder(v2);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof YoutubeVideoViewHolder) {
            bindYoutubeVideoViewHolder((YoutubeVideoViewHolder) holder, position);
        } else if (holder instanceof  YoutubePlaylistViewHolder){
            bindPlaylistViewHolder((YoutubePlaylistViewHolder) holder,position);
        } else {
            bindChannelViewHolder((YoutubeChannelViewHolder) holder,position);
        }
    }

    public void bindYoutubeVideoViewHolder(YoutubeVideoViewHolder holder, int position) {
        viewCount = holder.getAdapterPosition();// position使うとLintに怒られる
        SearchResult searchResult = searchResults.get(position);
        YoutubeSearchResultItemBinding binding = holder.getBinding();
        binding.setSearchResult(searchResult);
        binding.executePendingBindings();
        binding.getRoot().setOnClickListener(v -> {
            EventBus.getDefault().post(new YoutubeVideoItemClickEvent(searchResult.id.videoId));
        });
    }

    public void bindPlaylistViewHolder(YoutubePlaylistViewHolder holder, int position) {
        viewCount = holder.getAdapterPosition();// position使うとLintに怒られる
        SearchResult searchResult = searchResults.get(position);
        YoutubeSearchResultItemBinding binding = holder.getBinding();
        binding.setSearchResult(searchResult);
        binding.executePendingBindings();
    }

    public void bindChannelViewHolder(YoutubeChannelViewHolder holder, int position) {
        viewCount = holder.getAdapterPosition();// position使うとLintに怒られる
        SearchResult searchResult = searchResults.get(position);
        YoutubeSearchResultItemBinding binding = holder.getBinding();
//        binding.setSearchResult(searchResult);
//        binding.executePendingBindings();
    }

    public YoutubeSearchResultListAdapter(final ObservableArrayList<SearchResult> itemList) {
        searchResults = itemList;
    }

    public int getViewCount() {
        return viewCount;
    }

    @Override
    public int getItemCount() {
        return searchResults == null ? 0 : searchResults.size();
    }

    public ObservableArrayList<SearchResult> getDataList() {
        return searchResults;
    }

    public static class YoutubeVideoViewHolder extends RecyclerView.ViewHolder {
        private YoutubeSearchResultItemBinding mBinding;

        public YoutubeVideoViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public YoutubeSearchResultItemBinding getBinding() {
            return mBinding;
        }
    }

    public static class YoutubePlaylistViewHolder extends RecyclerView.ViewHolder {
        private YoutubeSearchResultItemBinding mBinding;

        public YoutubePlaylistViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public YoutubeSearchResultItemBinding getBinding() {
            return mBinding;
        }
    }

    public static class YoutubeChannelViewHolder extends RecyclerView.ViewHolder {
        private YoutubeSearchResultItemBinding mBinding;

        public YoutubeChannelViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public YoutubeSearchResultItemBinding getBinding() {
            return mBinding;
        }
    }
}
