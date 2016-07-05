package com.sloper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;

public class DownloadImage extends AsyncTask<String, Integer, Drawable> {

    @Override
    protected Drawable doInBackground(String... arg0) {
        // This is done in a background thread
        return downloadImage(arg0[0]);
    }
    ImageView src;
    DownloadImage(ImageView in) {
        src = in;
    }


    /**
     * Called after the image has been downloaded
     * -> this calls a function on the main thread again
     */
    protected void onPostExecute(Drawable image)
    {
        src.setImageDrawable(image);
    }


    /**
     * Actually download the Image from the _url
     * @param _url
     * @return
     */
    private Drawable downloadImage(String _url)
    {
        //Prepare to download image
        URL url;
        BufferedOutputStream out;
        InputStream in;
        BufferedInputStream buf;

        //BufferedInputStream buf;
        try {
            url = new URL(_url);
            in = url.openStream();
            // Read the inputstream
            buf = new BufferedInputStream(in);

            // Convert the BufferedInputStream to a Bitmap
            Bitmap bMap = BitmapFactory.decodeStream(buf);
            if (in != null) {
                in.close();
            }
            if (buf != null) {
                buf.close();
            }

            return new BitmapDrawable(bMap);

        } catch (Exception e) {
            Log.e("Error reading file", e.toString());
        }

        return null;
    }

}
