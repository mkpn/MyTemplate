package com.template.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.template.R;
import com.template.databinding.SongListItemBinding;
import com.template.entity.Song;
import com.template.event.SongSelectEvent;

/**
 * Created by makoto on 2016/02/15.
 */
public class ObservableSongListAdapter extends RecyclerView.Adapter<ObservableSongListAdapter.ItemViewHolder> {
    private ObservableArrayList<Song> observableItemList;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Song song = observableItemList.get(position);
        holder.getBinding().setSong(song);
        holder.getBinding().executePendingBindings();
        holder.getBinding().getRoot().setOnClickListener(v -> {
            org.greenrobot.eventbus.EventBus.getDefault().post(new SongSelectEvent((position)));
        });
    }

    public ObservableSongListAdapter(final ObservableArrayList<Song> songs) {
        observableItemList = songs;
    }

    @Override
    public int getItemCount() {
        return observableItemList == null ? 0 : observableItemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private SongListItemBinding mBinding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public SongListItemBinding getBinding() {
            return mBinding;
        }
    }
}
