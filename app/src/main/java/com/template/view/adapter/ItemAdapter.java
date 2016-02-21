package com.template.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.template.BR;
import com.template.R;
import com.template.databinding.ListItemBinding;
import com.template.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makoto on 2016/02/15.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> mItemList;

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

    public ItemAdapter(final List<Item> itemList) {
        mItemList = itemList;
    }

    @Override
    public int getItemCount() {
        Log.d("デバッグ", "size is " + mItemList.size());
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
