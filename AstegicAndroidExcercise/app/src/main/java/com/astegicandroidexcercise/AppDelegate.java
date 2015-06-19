package com.astegicandroidexcercise;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by anush on 18/6/15.
 */
public class AppDelegate extends Application {

    private static AppDelegate mInstance;

    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();

        mImageLoader = ImageLoader.getInstance();

        mImageLoader.init(ImageLoaderConfiguration
                .createDefault(getApplicationContext()));

    }

    public static AppDelegate getInstance(Context mContext) {

        synchronized (AppDelegate.class) {
            if (mInstance == null) {
                mInstance = (AppDelegate) mContext.getApplicationContext();
            }
        }
        return mInstance;

    }


    /**
     * Method to Convert InputStream to String
     *
     * @param InputStream mInputStream
     * @return StringBuilder.toString();
     */
    public String convertStreamToString(InputStream mInputStream) {

        BufferedReader mBufferedReader = new BufferedReader(
                new InputStreamReader(mInputStream));
        StringBuilder mStringBuilder = new StringBuilder();

        String str_line = null;
        try {
            while ((str_line = mBufferedReader.readLine()) != null) {
                mStringBuilder.append(str_line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                mInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mStringBuilder.toString();
    }


}
