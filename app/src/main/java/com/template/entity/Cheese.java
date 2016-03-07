package com.template.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.template.BR;

/**
 * Created by makoto on 2015/11/27.
 */
public class Cheese extends BaseObservable {
    private String name;
    private int resId;

    public Cheese(final String name, final int resId) {
        this.name = name;
        this.resId = resId;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public int getResId() {
        return resId;
    }


    public void setName(String firstName) {
        name = firstName;

        // setterで、値が変わったことを通知する
        // BR.〇〇 は @Bindable を付けたgetterに依存している
//        notifyPropertyChanged(jp.eno314.databindingdemo.BR.string);
        notifyPropertyChanged(BR.name);
    }

    public void setResId(int resId) {
         this.resId = resId;
//        notifyPropertyChanged(jp.eno314.databindingdemo.BR.lastName);
        notifyPropertyChanged(BR.resId);
    }
}
