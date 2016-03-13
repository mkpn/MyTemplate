package com.template.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.template.R;
import com.template.databinding.YoutubeSearchResultItemBinding;
import com.template.entity.SearchResult;

/**
 * Created by makoto on 2016/02/15.
 */
public class YoutubeSearchResultListAdapter extends RecyclerView.Adapter<YoutubeSearchResultListAdapter.CustomViewHolder> {
    private ObservableArrayList<SearchResult> searchResults;
    private int viewCount;

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_search_result_item, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        viewCount = holder.getAdapterPosition();// position使うとLintに怒られる
        SearchResult searchResult = searchResults.get(position);
        YoutubeSearchResultItemBinding binding = holder.getBinding();
        binding.setSearchResult(searchResult);
        binding.executePendingBindings();
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

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private YoutubeSearchResultItemBinding mBinding;

        public CustomViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public YoutubeSearchResultItemBinding getBinding() {
            return mBinding;
        }
    }
}
