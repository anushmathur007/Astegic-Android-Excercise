package com.apps.interfaces;

import android.view.View;

/**
 * Created by anush on 18/6/15.
 */
public interface OnReceiveResponse extends View.OnClickListener {

    public void onReceiveServerResponse(String strAPIName, String strResult);

}
