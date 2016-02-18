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

package com.template.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.template.BR;
import com.template.ItemDetailActivity;
import com.template.R;
import com.template.databinding.FragmentListBinding;
import com.template.databinding.ListItemBinding;
import com.template.model.Cheeses;
import com.template.model.Item;

import java.util.ArrayList;

public class MutableListFragment extends Fragment {

    private FragmentListBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        return mBinding.recyclerview;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupRecyclerView(mBinding.recyclerview);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        ArrayList<Item> itemList = new ArrayList<>();
        for (String sCheeseString : Cheeses.sCheeseStrings) {
            Item item = new Item(sCheeseString, "lastname");
            itemList.add(item);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new InnerItemAdapter(itemList));
    }

    public static class InnerItemAdapter extends RecyclerView.Adapter<InnerItemAdapter.ItemViewHolder> {
        private ArrayList<Item> mItemList;

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ItemViewHolder holder, int position) {
            Item item = mItemList.get(position);
            holder.getBinding().setVariable(BR.item, item);
            holder.getBinding().executePendingBindings();
            holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        public InnerItemAdapter(final ArrayList<Item> itemList) {
            mItemList = itemList;
        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            private ListItemBinding mBinding;

            public ItemViewHolder(View itemView) {
                super(itemView);
                mBinding = DataBindingUtil.bind(itemView);
            }

            public ListItemBinding getBinding() {
                return mBinding;
            }
        }
    }
}
