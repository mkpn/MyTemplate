package com.template.di.component;

import com.template.di.module.AppModule;
import com.template.di.module.UiModule;
import com.template.ui.activity.YouTubeSearchActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by makoto on 2016/03/09.
 */
@Singleton
@Component(modules = {AppModule.class, UiModule.class})
public interface AppComponent {
    void inject(YouTubeSearchActivity activity);
}
