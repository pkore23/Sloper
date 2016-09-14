package com.sloper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sloper.ProductFragment.OnListFragmentInteractionListener;
import com.sloper.dummy.DummyContent.DummyItem;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link ProductFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyProductRecyclerViewAdapter extends RecyclerView.Adapter<MyProductRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    ViewGroup mParent;

    public MyProductRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product, parent, false);
        mParent=parent;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).title);
        holder.mContentView.setText(mValues.get(position).price);
        final BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(mParent.getResources(),R.drawable.wait,opt);
        int imgHeight = opt.outHeight;
        int imgWidth = opt.outWidth;
        opt.inSampleSize = calculateSize(imgHeight,imgWidth,holder.mImage.getHeight(),holder.mImage.getWidth());
        opt.inJustDecodeBounds = false;
        BitmapFactory.decodeResource(mParent.getResources(),R.drawable.wait,opt);
        DownloadImageTask downloadImageTask=new DownloadImageTask(holder.mImage);
        downloadImageTask.execute("http://sloper.nakodatextiles.com/uploads/"+mValues.get(position).title+"/base.jpg");
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem,v);
                }
            }
        });
    }

    private int calculateSize(int imgHeight, int imgWidth, int height, int width) {
        int sample=1;
        if(imgHeight>height||imgWidth>width){
            final int halfImgH = imgHeight/2;
            final int halfImgW = imgWidth/2;
            while((halfImgH/sample)>height&&(halfImgW/sample)>width)
                sample *=2;
        }
        return sample;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImage;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.price);
            mImage = (ImageView) view.findViewById(R.id.photo);
        }



        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        String msg;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap image = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                image = BitmapFactory.decodeStream(in);
                final BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inSampleSize = 4;
                opt.inJustDecodeBounds = false;
                image = BitmapFactory.decodeStream(in,null,opt);

            } catch (Exception e) {
                Log.e("ImageDownloadError", e.getMessage());
                e.printStackTrace();
            }
            return image;
        }

        protected void onPostExecute(Bitmap result) {

            if(result!=null)
                bmImage.setImageBitmap(result);
        }

    }
}
