package com.dialog.classes;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.astegicandroidexcercise.R;
import com.model.classes.FlickrFeeds;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by anush on 19/6/15.
 */
public class FeedDetailDialog extends Dialog implements View.OnClickListener {

    /**
     * FeedDetailDialog UI Members Declarations
     */
    private FeedDetailDialog mFeedDetailDialog;

    private TextView txtFeedTitle;
    private TextView txtAuthorName;
    private TextView txtAuthorTag;

    private ImageView imgFlickrFeed;
    private ImageButton btnClose;


    /**
     * FeedDetailDialog Members Declarations
     */
    private Context mContext;
    private ImageLoader mImageLoader;
    private DisplayImageOptions mDisplayImageOptions;
    private FlickrFeeds mFlickrFeeds;


    /**
     * Create a Dialog window that uses the default dialog frame style.
     *
     * @param context The Context the Dialog is to run it.  In particular, it
     *                uses the window manager and theme in this context to
     *                present its UI.
     */
    public FeedDetailDialog(Context context) {
        super(context);
        this.mContext = context;

    }

    public FeedDetailDialog(Context mContext, int dialog_style) {
        super(mContext, dialog_style);
    }


    /**
     * Method is used to Show Feed Detail Dialog
     *
     * @param mFlickrFeeds
     */
    public FeedDetailDialog showFeedDetailDialog(FlickrFeeds mFlickrFeeds) {
        this.mFlickrFeeds = mFlickrFeeds;

        if (mFeedDetailDialog != null && mFeedDetailDialog.isShowing()) {
            hideFeedDetailDialog(mContext);
        }

        mFeedDetailDialog = new FeedDetailDialog(mContext, R.style.dialog_style);
        mFeedDetailDialog.setTitle("");
        mFeedDetailDialog.setContentView(R.layout.dialog_feeddetail);
        mFeedDetailDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        mFeedDetailDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        WindowManager.LayoutParams mLayoutParams = mFeedDetailDialog.getWindow()
                .getAttributes();

        mLayoutParams.dimAmount = 0.2f;
        mFeedDetailDialog.getWindow().setAttributes(mLayoutParams);

        initMembers();

        initMainView();

        showFeedValues(mFlickrFeeds);

        mFeedDetailDialog.show();

        return mFeedDetailDialog;
    }

    /**
     * Method used to init members of the class
     */
    private void initMembers() {

        mImageLoader = ImageLoader.getInstance();
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory().cacheOnDisc().build();
    }

    /**
     * Method used to set values to corresponding View
     *
     * @param mFlickrFeeds
     */
    private void showFeedValues(FlickrFeeds mFlickrFeeds) {

        txtFeedTitle.setText(mFlickrFeeds.getStrFeedTitle());
        txtAuthorName.setText(mFlickrFeeds.getStrFeedAuthor());
        txtAuthorTag.setText(mFlickrFeeds.getStrFeedTag());

        mImageLoader.displayImage(mFlickrFeeds.getStrFeedImageURL(), imgFlickrFeed,
                mDisplayImageOptions);

    }

    /**
     * Method is used to init view members of class
     */
    private void initMainView() {

        txtFeedTitle = (TextView) mFeedDetailDialog.findViewById(R.id.txtFeedTitle);

        txtAuthorName = (TextView) mFeedDetailDialog.findViewById(R.id.txtAuthorName);

        txtAuthorTag = (TextView) mFeedDetailDialog.findViewById(R.id.txtAuthorTag);

        imgFlickrFeed = (ImageView) mFeedDetailDialog.findViewById(R.id.imgFeedImage);

        btnClose = (ImageButton) mFeedDetailDialog.findViewById(R.id.btnclose);
        btnClose.setOnClickListener(this);

    }

    /**
     * Method is used to hide Feed Detail Dialog
     *
     * @param mContext
     */
    public void hideFeedDetailDialog(Context mContext) {

        if (mFeedDetailDialog != null && mFeedDetailDialog.isShowing()) {

            mFeedDetailDialog.dismiss();
        } else {

            mFeedDetailDialog = new FeedDetailDialog(mContext);
            mFeedDetailDialog.dismiss();

        }

    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnclose:

                hideFeedDetailDialog(mContext);

                break;
        }
    }
}
