package com.template.view;

import android.support.v7.app.AppCompatActivity;

import com.template.MyApplication;
import com.template.di.component.AppComponent;

public abstract class BaseActivity extends AppCompatActivity {
    protected AppComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }
}
