package com.template.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.template.BR;

/**
 * Created by makoto on 2015/11/27.
 */
public class Cheese extends BaseObservable {
    private String mName;
    private int mResId;

    public Cheese(final String name, final int resId) {
        mName = name;
        mResId = resId;
    }

    @Bindable
    public String getmName() {
        return mName;
    }

    @Bindable
    public int getResId() {
        return mResId;
    }


    public void setName(String firstName) {
        mName = firstName;

        // setterで、値が変わったことを通知する
        // BR.〇〇 は @Bindable を付けたgetterに依存している
//        notifyPropertyChanged(jp.eno314.databindingdemo.BR.firstName);
        notifyPropertyChanged(BR.firstName);
    }

    public void setResId(int resId) {
         mResId = resId;
//        notifyPropertyChanged(jp.eno314.databindingdemo.BR.lastName);
        notifyPropertyChanged(BR.resId);
    }
}
