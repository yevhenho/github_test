package com.example.yevhenho.github_test;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class GitTestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // init ImageLoader
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
    }

}
