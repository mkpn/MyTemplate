package com.template.di.module;

import com.template.service.ContentsLoadService;
import com.template.service.YouTubeService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by makoto on 2016/03/09.
 */
@Module
public class UiModule {
    @Provides
    @Singleton
    public YouTubeService provideYouTubeService(){
        return new YouTubeService();
    }

    @Provides
    @Singleton
    public ContentsLoadService provideContentsLoadService(){
        return new ContentsLoadService();
    }
}

