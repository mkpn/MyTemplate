package com.template.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.template.BR;
import com.template.R;
import com.template.databinding.ObservableStringListItemBinding;

/**
 * Created by makoto on 2016/02/15.
 */
public class ObservableStringListAdapter extends RecyclerView.Adapter<ObservableStringListAdapter.ItemViewHolder> {
    private ObservableArrayList<String> observableItemList;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.observable_string_list_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        String str = observableItemList.get(position);
        holder.getBinding().setVariable(BR.string, str);
        holder.getBinding().executePendingBindings();
    }

    public ObservableStringListAdapter(final ObservableArrayList<String> itemList) {
        observableItemList = itemList;
    }

    @Override
    public int getItemCount() {
        return observableItemList == null ? 0 : observableItemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ObservableStringListItemBinding mBinding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public ObservableStringListItemBinding getBinding() {
            return mBinding;
        }
    }
}
