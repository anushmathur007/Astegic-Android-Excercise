package com.custom.widgets;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundedImageView extends ImageView {
    private static String TAG = "Rounded";

    public RoundedImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Config.ARGB_8888, true);

        bitmap = getScaledImage(bitmap);

        // Utility.DEBUG_LOG(1, TAG, "Width :" + w + "     Height :" + h);
        // if (w > h) {
        // w = h;
        //
        // }
        //
        // bitmap = Utility.getScaledImage(bitmap);
        //
        // Utility.DEBUG_LOG(1, TAG, "Width :" + w + "     Height :" + h);
        //
        // Bitmap roundBitmap = getCroppedBitmap(bitmap, w);

        int w = getWidth(), h = getHeight();
        int radius = w < h ? w : h;

	/*	Utility.DEBUG_LOG(1, TAG, "Width :" + w + "     Height :" + h
                + "   Radius :" + radius);*/

        Bitmap roundBitmap = getCroppedBitmap(bitmap, radius, w, h);

        canvas.drawBitmap(roundBitmap, 0, 0, null);

    }

    // public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
    // Bitmap sbmp;
    // if (bmp.getWidth() != radius || bmp.getHeight() != radius)
    // sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
    // else
    // sbmp = bmp;
    // Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
    // Config.ARGB_8888);
    // Canvas canvas = new Canvas(output);
    //
    // // final int color = 0xffa19774;
    // final Paint paint = new Paint();
    // final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());
    //
    // paint.setAntiAlias(true);
    // paint.setFilterBitmap(true);
    // paint.setDither(true);
    // canvas.drawARGB(0, 0, 0, 0);
    // paint.setColor(Color.parseColor("#BAB399"));
    // canvas.drawCircle(sbmp.getWidth() / 2 + 0.6f,
    // sbmp.getHeight() / 2 + 0.6f, sbmp.getWidth() / 2 + 0.1f, paint);
    // paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
    // canvas.drawBitmap(sbmp, rect, rect, paint);
    //
    // return output;
    // }

    public Bitmap getCroppedBitmap(Bitmap bmp, int radius, int w, int h) {
        Bitmap sbmp;
        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            float _w_rate = 1.0f * radius / bmp.getWidth();
            float _h_rate = 1.0f * radius / bmp.getHeight();
            float _rate = _w_rate < _h_rate ? _h_rate : _w_rate;
            sbmp = Bitmap.createScaledBitmap(bmp,
                    (int) (bmp.getWidth() * _rate),
                    (int) (bmp.getHeight() * _rate), false);
        } else
            sbmp = bmp;

        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));


        // Add border to circular image
        Paint p = new Paint();
        p.setAntiAlias(true);
        // canvas.drawARGB(0, 0, 0, 0);
        p.setStyle(Style.FILL);
        p.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        p.setXfermode(null);
        p.setStyle(Style.STROKE);
        p.setColor(Color.TRANSPARENT);
        p.setStrokeWidth(3);

        //Utility.DEBUG_LOG(1, TAG, "Width :" + w + "     Height :" + h);
        canvas.drawCircle(w / 2, h / 2, ((w < h ? w : h) / 2) - 1, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        canvas.drawBitmap(sbmp, rect, rect, paint);
        canvas.drawCircle(w / 2, h / 2, ((w < h ? w : h) / 2) - 1, p);

        return output;
    }

    public static Bitmap getScaledImage(Bitmap mBitmap) {

        // Bitmap resultBitmap = Bitmap.createBitmap(mBitmap.getHeight(),
        // mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //
        // Canvas c = new Canvas(resultBitmap);
        //
        // Rect sourceRect = new Rect(0, 0, mBitmap.getWidth(),
        // mBitmap.getHeight());
        // Rect destinationRect = new Rect(
        // (resultBitmap.getWidth() - mBitmap.getWidth()) / 2, 0,
        // (resultBitmap.getWidth() + mBitmap.getWidth()) / 2,
        // mBitmap.getHeight());
        // c.drawBitmap(mBitmap, sourceRect, destinationRect, null);

        Bitmap resultBitmap;
        if (mBitmap.getWidth() >= mBitmap.getHeight()) {

            resultBitmap = Bitmap.createBitmap(mBitmap, mBitmap.getWidth() / 2
                            - mBitmap.getHeight() / 2, 0, mBitmap.getHeight(),
                    mBitmap.getHeight());

        } else {

            resultBitmap = Bitmap.createBitmap(mBitmap, 0, mBitmap.getHeight()
                            / 2 - mBitmap.getWidth() / 2, mBitmap.getWidth(),
                    mBitmap.getWidth());
        }

        return resultBitmap;

    }
}