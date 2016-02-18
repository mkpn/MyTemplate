package com.template.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.template.BR;

/**
 * Created by makoto on 2015/11/18.
 */
public class Item extends BaseObservable {

    private String mFirstName;

    private String mLastName;

    public Item(String firstName, String lastName) {
        mFirstName = firstName;
        mLastName = lastName;
    }

    // getterにはBindableアノテーションを付ける
    @Bindable
    public String getFirstName() {
        return mFirstName;
    }

    @Bindable
    public String getLastName() {
        return mLastName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;

        // setterで、値が変わったことを通知する
        // BR.〇〇 は @Bindable を付けたgetterに依存している
        notifyPropertyChanged(BR.firstName);
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
//        notifyPropertyChanged(jp.eno314.databindingdemo.BR.lastName);
        notifyPropertyChanged(BR.lastName);
    }
}