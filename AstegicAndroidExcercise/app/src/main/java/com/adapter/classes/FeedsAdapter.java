package com.adapter.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.astegicandroidexcercise.R;
import com.custom.widgets.RoundedImageView;
import com.model.classes.FlickrFeeds;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by anush on 18/6/15.
 */
public class FeedsAdapter extends BaseAdapter {

    /**
     * FeedsAdapter Members Declarations
     */
    private Context mContext;
    private ArrayList<FlickrFeeds> arrFlickrFeeds;
    private View.OnClickListener mFeedClickListener;
    private ImageLoader mImageLoader;
    private DisplayImageOptions mDisplayImageOptions;


    public FeedsAdapter(Context context, ArrayList<FlickrFeeds> mFlickrFeedsArray, View
            .OnClickListener mOnClickListener) {

        this.mContext = context;
        this.arrFlickrFeeds = mFlickrFeedsArray;
        this.mFeedClickListener = mOnClickListener;
        mImageLoader = ImageLoader.getInstance();
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher).cacheInMemory()
                .cacheOnDisc().build();

    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return arrFlickrFeeds.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return arrFlickrFeeds.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder mViewHodler;

        if (convertView == null) {

            LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
            mViewHodler = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.feedschildview, null);

            mViewHodler.txtFeedTitle = (TextView) convertView.findViewById(R.id.txtFlickrFeedTitle);

            mViewHodler.imgFlickrFeed = (RoundedImageView) convertView.findViewById(R.id
                    .imgflickrfeed);
            mViewHodler.imgFlickrFeed.setOnClickListener(mFeedClickListener);


            convertView.setTag(mViewHodler);

        } else {

            mViewHodler = (ViewHolder) convertView.getTag();
        }

        if (arrFlickrFeeds != null) {


            mViewHodler.imgFlickrFeed.setTag(arrFlickrFeeds.get(position));

            mViewHodler.txtFeedTitle.setText(arrFlickrFeeds.get(position).getStrFeedTitle());

            mImageLoader.displayImage(arrFlickrFeeds.get(position).getStrFeedImageURL(),
                    mViewHodler.imgFlickrFeed, mDisplayImageOptions);

        }
        return convertView;
    }


    /**
     * Stub Class
     */
    private class ViewHolder {

        private RoundedImageView imgFlickrFeed;

        private TextView txtFeedTitle;

    }
}
