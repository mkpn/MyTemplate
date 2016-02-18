package com.template.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

/**
 * Created by makoto on 2016/02/15.
 */
public class Items extends BaseObservable {
    @Bindable
    public ObservableArrayList<Item> items;

    public Items(ObservableArrayList<Item> items) {
        this.items = new ObservableArrayList<>();
    }

    public void addItem(String firstName, String lastName){
        this.items.add(new Item(firstName, lastName));
    }
}
