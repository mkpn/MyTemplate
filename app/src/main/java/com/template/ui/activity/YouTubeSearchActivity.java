/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.template.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.template.BuildConfig;
import com.template.R;
import com.template.event.YoutubeVideoItemClickEvent;
import com.template.ui.BaseActivity;
import com.template.databinding.YoutubeSearchActivityBinding;
import com.template.event.SearchYoutubeSuccessEvent;
import com.template.service.YouTubeService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

public class YouTubeSearchActivity extends BaseActivity implements YouTubePlayer.OnInitializedListener {

    private YoutubeSearchActivityBinding binding;
    @Inject
    YouTubeService youTubeService;
    private SearchView mSearchView;
    private String query = "";
    private YouTubePlayer youTubePlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.youtube_search_activity);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = binding.toolbar;
        toolbar.inflateMenu(R.menu.search_menu);
        Menu menu = toolbar.getMenu();
        mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        MenuItem item = menu.add("menu add");
        item.setIcon(R.drawable.ic_search_white_18dp);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setActionView(mSearchView);

        binding.toolbar.setTitle("詳細ページ");
        initializeYoutubeFragment();
    }

    private void initializeYoutubeFragment() {
        // フラグメントインスタンスを取得
        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);

        // フラグメントのプレーヤーを初期化する
        youTubePlayerFragment.initialize(BuildConfig.PARSE_YOUTUBE_SDK_API_KEY, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onSearchYoutubeSuccess(SearchYoutubeSuccessEvent event) {
        binding.setSearchResults(event.response);
    }

    @Subscribe
    public void onVideoItemClick(YoutubeVideoItemClickEvent event){
        if (youTubePlayer == null) return;
        youTubePlayer.loadVideo(event.videoId);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        youTubeService.clearResults();
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
            youTubeService.search(query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sample_actions, menu);
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.search_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        Log.d("デバッグ", "success!!!");
        this.youTubePlayer = youTubePlayer;
        youTubePlayer.play();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.d("デバッグ", "failure...");
    }
}
