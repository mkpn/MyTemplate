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

package com.template.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.template.R;
import com.template.databinding.SimpleClickListenerBindingActivityBinding;
import com.template.entity.Item;

public class SimpleClickListenerBindingActivity extends AppCompatActivity {

    private SimpleClickListenerBindingActivityBinding mBinding;
    public Item item;
    public View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            item.string.set("変更されました！");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.simple_click_listener_binding_activity);
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("SimpleExample");

        item = new Item();
        item.string.set("Hello World");
        mBinding.setActivity(this);
    }
}
