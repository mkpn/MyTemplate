package com.template.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.template.BR;
import com.template.R;
import com.template.databinding.ListItemBinding;
import com.template.entity.Item;

import java.util.ArrayList;

/**
 * Created by makoto on 2016/02/15.
 */
public class BetterItemAdapter extends RecyclerView.Adapter<BetterItemAdapter.ItemViewHolder> {
    private ArrayList<Item> mItemList;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = mItemList.get(position);
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().executePendingBindings();
    }

    public BetterItemAdapter(final ArrayList<Item> itemList) {
        mItemList = itemList;
    }

    public void add(Item item){
        mItemList.add(item);
        notifyDataSetChanged();
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
