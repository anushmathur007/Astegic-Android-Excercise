package com.contant.classes;

import com.model.classes.FlickrFeeds;

import java.util.ArrayList;

/**
 * Created by anush on 18/6/15.
 */
public class AstegicConstant {

    /**
     * AstegicConstant Members Declarations
     */
    private static AstegicConstant mInstance;

    public ArrayList<FlickrFeeds> arrFlickrFeeds;

    public String strFeedRequestURL = "http://api.flickr.com/services/feeds/photos_public.gne?format=json&tags=";

    /**
     * Method is used to get Unique Instance of the Class
     *
     * @return AstegicConstant Instance
     */
    public static AstegicConstant getInstance() {
        synchronized (AstegicConstant.class) {
            if (mInstance == null) {
                mInstance = new AstegicConstant();
            }
        }
        return mInstance;
    }


}
