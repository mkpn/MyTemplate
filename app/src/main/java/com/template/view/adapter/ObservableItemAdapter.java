package com.template.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.template.BR;
import com.template.R;
import com.template.databinding.ListItemBinding;
import com.template.entity.Item;
import com.template.entity.ObservableItems;

/**
 * Created by makoto on 2016/02/15.
 */
public class ObservableItemAdapter extends RecyclerView.Adapter<ObservableItemAdapter.ItemViewHolder> {
    private ObservableItems observableItemList;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = observableItemList.items.get(position);
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().executePendingBindings();
    }

    public ObservableItemAdapter(final ObservableItems itemList) {
        observableItemList = itemList;
    }

    @Override
    public int getItemCount() {
        return observableItemList.items == null ? 0 : observableItemList.items.size();
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
