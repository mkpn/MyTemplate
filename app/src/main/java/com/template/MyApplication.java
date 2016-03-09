package com.template;

import android.app.Application;

import com.template.di.component.AppComponent;
import com.template.di.component.DaggerAppComponent;
import com.template.di.module.AppModule;

/**
 * Created by makoto on 2016/03/09.
 */
public class MyApplication extends Application {
    private AppComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        applicationComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getApplicationComponent() {
        return applicationComponent;
    }
}
