package com.template.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

/**
 * Created by makoto on 2016/02/15.
 */
public class ObservableItems extends BaseObservable {
    @Bindable
    public ObservableArrayList<Item> items;

    public ObservableItems(ObservableArrayList<Item> items) {
        this.items = items;
    }
}
