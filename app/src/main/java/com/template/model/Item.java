package com.template.model;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

/**
 * Created by makoto on 2015/11/18.
 */
public class Item extends BaseObservable {
    public final ObservableField<String> string = new ObservableField<>();
}