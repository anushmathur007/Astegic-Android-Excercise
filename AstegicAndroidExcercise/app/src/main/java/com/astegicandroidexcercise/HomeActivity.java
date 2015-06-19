package com.astegicandroidexcercise;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.adapter.classes.FeedsAdapter;
import com.apps.interfaces.OnReceiveResponse;
import com.async.classes.GetAsync;
import com.contant.classes.AstegicConstant;
import com.dialog.classes.FeedDetailDialog;
import com.helper.classes.CheckInternet;
import com.model.classes.FlickrFeeds;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeActivity extends ActionBarActivity implements OnReceiveResponse {

    /**
     * HomeActivity UI Members Declarations
     */
    private android.support.v7.app.ActionBar mActionBar;

    private View mActionbarView;

    private ImageButton btnSearchFeed;

    private GridView gridFlickrFeeds;

    private View.OnClickListener mFeedClickListner;

    private ViewGroup mParentLayout;

    private ViewGroup mMainLayout;

    private EditText edtSearch;

    private Button btnSearch;

    private ViewGroup mSearchLayoutGroup;

    private ProgressBar mProgressBar;


    /**
     * HomeActivity Members Declarations
     */

    private AstegicConstant objConstant;

    private FeedsAdapter mFeedsAdapter;

    private String strSearchFeed = "";

    private LayoutInflater mLayoutInflater;

    private CheckInternet mCheckInternet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mLayoutInflater = LayoutInflater.from(HomeActivity.this);


        setupActionBar();

        initViewMembers();

        initMembers();


        mFeedClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FlickrFeeds mFlickrFeeds = (FlickrFeeds) v.getTag();

                Log.d("anush", "Image Click : " + mFlickrFeeds);

                if (mFlickrFeeds != null) {

                    FeedDetailDialog mFeedDetailDialog = new FeedDetailDialog(HomeActivity.this);
                    mFeedDetailDialog.showFeedDetailDialog(mFlickrFeeds);

                }

            }
        };

        initAdapterClass(mFeedClickListner);

        if (mCheckInternet.hasConnection()) {
            getAllFeeds("");
        } else {
            mCheckInternet.showInternetWarning();
        }

    }

    /**
     * Method is used to execute Server Request
     */
    private void getAllFeeds(String strTag) {

        mProgressBar.setVisibility(View.VISIBLE);

        GetAsync objGetAsync = new GetAsync(HomeActivity.this, this, objConstant
                .strFeedRequestURL, "FeedsAPI", strTag);
        objGetAsync.execute();

    }

    /**
     * Method is used to init FeedsAdapter Class
     *
     * @param mFeedClickListner
     */
    private void initAdapterClass(View.OnClickListener mFeedClickListner) {

        mFeedsAdapter = new FeedsAdapter(HomeActivity.this, objConstant.arrFlickrFeeds,
                mFeedClickListner);
        gridFlickrFeeds.setAdapter(mFeedsAdapter);

        ImageLoader mImageLoader = ImageLoader.getInstance();

        PauseOnScrollListener mPauseOnScrollListener = new PauseOnScrollListener(mImageLoader,
                true, true);
        gridFlickrFeeds.setOnScrollListener(mPauseOnScrollListener);

    }

    /**
     * Method is used to init UI elements of this class
     */
    private void initViewMembers() {


        mProgressBar = (ProgressBar) mActionbarView.findViewById(R.id.progressBar2);
        mProgressBar.setVisibility(View.GONE);

        btnSearchFeed = (ImageButton) mActionbarView.findViewById(R.id.btnSearchFeeds);
        btnSearchFeed.setOnClickListener(this);

        gridFlickrFeeds = (GridView) findViewById(R.id.gridFlickrFeeds);

        mParentLayout = (ViewGroup) findViewById(R.id.rootcontainer);

        mMainLayout = (ViewGroup) findViewById(R.id.mainlayout);

        mSearchLayoutGroup = (ViewGroup) mLayoutInflater.inflate(R.layout.searchlayout,
                mParentLayout, false);

        mSearchLayoutGroup.setVisibility(View.INVISIBLE);


        mParentLayout.addView(mSearchLayoutGroup);

        edtSearch = (EditText) mSearchLayoutGroup.findViewById(R.id.edtsearchfeed);

        btnSearch = (Button) mSearchLayoutGroup.findViewById(R.id.btnsearch);
        btnSearch.setOnClickListener(this);


    }

    /**
     * Method is used to setup Actionbar
     */
    private void setupActionBar() {


        mActionbarView = mLayoutInflater.inflate(R.layout.action_header, null);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(mActionbarView);


    }

    /**
     * Method is used to init Members of Class
     */
    private void initMembers() {

        objConstant = AstegicConstant.getInstance();
        objConstant.arrFlickrFeeds = new ArrayList<FlickrFeeds>();

        mCheckInternet = CheckInternet.getmInstance(HomeActivity.this);

    }


    /**
     * Below Method is used to hide SoftInput Keyboard
     */
    public void HideKeyBoard() {
        final InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearchFeeds:

                //mActionBar.hide();

                if (mSearchLayoutGroup.getVisibility() == View.INVISIBLE) {
                    startAnimForSearchPanel();
                    mSearchLayoutGroup.setVisibility(View.VISIBLE);
                }


                break;

            case R.id.btnsearch:


                HideKeyBoard();
                mActionBar.show();

                strSearchFeed = edtSearch.getText().toString();


                if (mCheckInternet.hasConnection() && strSearchFeed != null && strSearchFeed.length
                        () > 0) {
                    getAllFeeds(strSearchFeed);
                } else {
                    mCheckInternet.showInternetWarning();
                }

                mSearchLayoutGroup.setVisibility(View.INVISIBLE);
                hideAnimForSearchPanel();


                break;
        }
    }

    /**
     * Method is used to Show Search panel With ANimation
     */
    private void startAnimForSearchPanel() {

        int pannelHeight = mSearchLayoutGroup.getHeight();

        ObjectAnimator anim = ObjectAnimator.ofFloat(mSearchLayoutGroup, "y",
                -pannelHeight, 0);
        anim.setDuration(250); // Duration in milliseconds
        anim.setInterpolator(new LinearInterpolator());
        anim.start();

        anim = ObjectAnimator.ofFloat(mMainLayout, "y", 0f,
                pannelHeight);
        anim.setDuration(250); // Duration in milliseconds
        anim.setInterpolator(new LinearInterpolator());
        anim.start();

    }

    /**
     * Method is used to Hide Search Panel With Animation
     */
    private void hideAnimForSearchPanel() {

        int pannelHeight2 = mSearchLayoutGroup.getHeight();

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mSearchLayoutGroup, "y", 0,
                -pannelHeight2);
        anim2.setDuration(250); // Duration in milliseconds
        anim2.setInterpolator(new LinearInterpolator());
        anim2.start();

        anim2 = ObjectAnimator.ofFloat(mMainLayout, "y", pannelHeight2, 0);
        anim2.setDuration(250); // Duration in milliseconds
        anim2.setInterpolator(new LinearInterpolator()); // E.g. Linear
        anim2.start();

    }

    @Override
    public void onReceiveServerResponse(String strAPIName, String strResult) {

        if (strAPIName.equalsIgnoreCase("FeedsAPI")) {

            if (strResult != null && strResult.length() > 0) {

                strResult = strResult.substring(strResult.indexOf("(") + 1);
                strResult = strResult.substring(0, strResult.length() - 1);

                try {

                    JSONObject mJsonObject = new JSONObject(strResult);
                    JSONArray mJsonArray = mJsonObject.getJSONArray("items");

                    if (objConstant.arrFlickrFeeds != null) {
                        objConstant.arrFlickrFeeds.clear();
                    } else {
                        objConstant.arrFlickrFeeds = new ArrayList<FlickrFeeds>();
                    }

                    for (int i = 0; i < mJsonArray.length(); i++) {

                        FlickrFeeds mFlickrFeedsObj = new FlickrFeeds();
                        JSONObject mItemObj = mJsonArray.getJSONObject(i);

                        /*
                        Title Tag Parsing
                         */
                        String strTitle = mItemObj.getString("title");
                        if (strTitle != null) {
                            mFlickrFeedsObj.setStrFeedTitle(strTitle);
                        } else {
                            mFlickrFeedsObj.setStrFeedTitle("");
                        }

                        /*
                        Link Tag Parsing
                         */
                        String strLink = mItemObj.getString("link");
                        if (strLink != null) {
                            mFlickrFeedsObj.setStrFeedLink(strLink);
                        } else {
                            mFlickrFeedsObj.setStrFeedLink("");
                        }

                        /*
                        Media Object Parsing
                         */
                        JSONObject objMedia = mItemObj.getJSONObject("media");

                        /*
                        Image URL Tag Parsing
                         */
                        String strImageLink = objMedia.getString("m");
                        if (strImageLink != null && strImageLink.length() > 0) {
                            strImageLink = strImageLink.substring(0, strImageLink.length() - 6) +
                                    ".jpg";
                            Log.d("anush", "Image Link URL : " + strImageLink);
                            mFlickrFeedsObj.setStrFeedImageURL(strImageLink);
                        } else {
                            mFlickrFeedsObj.setStrFeedImageURL("");
                        }

                        /*
                        Author Name Tag Parsing
                         */
                        String strAuthor = mItemObj.getString("author");
                        if (strAuthor != null) {
                            mFlickrFeedsObj.setStrFeedAuthor(strAuthor);
                        } else {
                            mFlickrFeedsObj.setStrFeedAuthor("");
                        }

                        /*
                        Author Tag Parsing
                         */
                        String strAuthorTag = mItemObj.getString("tags");
                        if (strAuthorTag != null) {
                            mFlickrFeedsObj.setStrFeedTag(strAuthorTag);
                        } else {
                            mFlickrFeedsObj.setStrFeedTag("");
                        }

                        objConstant.arrFlickrFeeds.add(mFlickrFeedsObj);
                    }


                } catch (Exception e) {

                    e.printStackTrace();

                }


                mFeedsAdapter.notifyDataSetChanged();
                gridFlickrFeeds.invalidate();

            }
            mProgressBar.setVisibility(View.GONE);

        }

    }
}
