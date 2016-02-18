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

package com.template;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.bumptech.glide.Glide;
import com.template.databinding.ItemDetailActivityBinding;
import com.template.model.Cheeses;
import com.template.view.viewmodel.ItemDetailViewModel;

public class ItemDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    private ItemDetailActivityBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.item_detail_activity);

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBinding.collapsingToolbar.setTitle("詳細ページ");

        mBinding.setViewModel(new ItemDetailViewModel());
        loadBackdrop();
    }

    private void loadBackdrop() {
        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(mBinding.backdrop);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
}
