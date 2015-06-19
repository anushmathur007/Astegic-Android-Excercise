package com.async.classes;

import android.content.Context;
import android.os.AsyncTask;

import com.apps.interfaces.OnReceiveResponse;
import com.astegicandroidexcercise.AppDelegate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.InputStream;

/**
 * GetAsync class is used to execute GET Request
 */
public class GetAsync extends AsyncTask<String, Void, String> {

    /**
     * GetAsync Members Declarations
     */
    private Context mContext;
    private OnReceiveResponse mOnReciveServerResponse;
    private String strRequestURL;
    private String strApiName;
    private String strTag;
    private AppDelegate mAppDelegate;

    /**
     * Constructor Implementation
     */
    public GetAsync(Context context,
                    OnReceiveResponse onReceiveResponse,
                    String mRequestURL, String mApiName, String mTag) {
        this.mContext = context;
        this.mOnReciveServerResponse = onReceiveResponse;
        this.strRequestURL = mRequestURL;
        this.strApiName = mApiName;
        this.strTag = mTag;
        mAppDelegate = AppDelegate.getInstance(mContext);

    }

    /**
     * This Method is called before the execution start
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * This Method is called during the execution
     */
    @Override
    protected String doInBackground(String... params) {

        /**
         * Establishing Connection with Server
         */
        try {
            HttpClient mHttpClient = new DefaultHttpClient();
            HttpContext mHttpContext = new BasicHttpContext();
            HttpGet mHttpGet = new HttpGet(strRequestURL+strTag);
            HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet,
                    mHttpContext);
            if (mHttpResponse != null) {
                HttpEntity mHttpEntity = mHttpResponse.getEntity();
                InputStream mInputStream = mHttpEntity.getContent();
                if (mInputStream != null) {

                    String mGetResult = mAppDelegate
                            .convertStreamToString(mInputStream);
                    if (mGetResult != null) {
                        return mGetResult;
                    } else {
                        return null;
                    }

                } else {
                    return "Network Error";
                }
            } else {
                return "Network Error";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This Method is called after the execution finish
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        this.mOnReciveServerResponse.onReceiveServerResponse(strApiName, result);

    }
}
