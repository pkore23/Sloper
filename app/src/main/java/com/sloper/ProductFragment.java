package com.sloper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.sloper.dummy.DummyContent;
import com.sloper.dummy.DummyContent.DummyItem;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ProductFragment extends Fragment implements View.OnClickListener {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_CALL_ID = "call-id";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private int mCallId = R.id.nav_camera;
    private OnListFragmentInteractionListener mListener;
    RecyclerView mRecyclerView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProductFragment newInstance(int columnCount, int callerID) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_CALL_ID,callerID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mCallId = getArguments().getInt(ARG_CALL_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        DummyContent dc = new DummyContent();
        if(mCallId==R.id.nav_manage){
            view=inflater.inflate(R.layout.fragment_categories,container,false);
        }
        else if(mCallId==R.id.nav_camera){
            view = inflater.inflate(R.layout.fragment_home, container, false);
            initHome(view);
        }
        else{
            view = inflater.inflate(R.layout.fragment_product_list, container, false);
            dc = new DummyContent();
            if(mCallId==R.id.nav_cat1)
                dc.addItems("category1");
            else if(mCallId==R.id.nav_cat2)
                dc.addItems("category2");
            else if(mCallId==R.id.nav_cat3)
                dc.addItems("category3");
            else if(mCallId==R.id.nav_cat4)
                dc.addItems("category4");
            else
                dc.addItems("all");
            setHasOptionsMenu(true);
            initViewFliper(dc,inflater,view,container);
        }
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                LinearLayoutManager llm = new LinearLayoutManager(context);
                llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                mRecyclerView.setLayoutManager(llm);
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mRecyclerView.setAdapter(new MyProductRecyclerViewAdapter(dc.ITEMS, mListener));
        }

        return view;
    }

    private void initViewFliper(DummyContent dc, LayoutInflater inflater, View view, ViewGroup container) {
        ViewFlipper vf = (ViewFlipper) view.findViewById(R.id.product_flipper_main);
        for(DummyItem item: dc.ITEMS){
            final DummyItem item1 = item;
            View holder = inflater.inflate(R.layout.fragment_product,container,false);
            ((TextView) holder.findViewById(R.id.title)).setText(item.title);
            ((TextView) holder.findViewById(R.id.price)).setText(item.price);
            final BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(),item.imgRes,opt);
            int imgHeight = opt.outHeight;
            int imgWidth = opt.outWidth;
            opt.inSampleSize = calculateSize(imgHeight,imgWidth,(holder.findViewById(R.id.photo)).getHeight(),(holder.findViewById(R.id.photo)).getWidth());
            opt.inSampleSize = 4;
            opt.inJustDecodeBounds = false;
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.wait,opt);
            DownloadImageTask downloadImageTask=new DownloadImageTask(((ImageView) holder.findViewById(R.id.photo)));
            downloadImageTask.execute("http://sloper.nakodatextiles.com/uploads/"+item.title+"/base.jpg");
            holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onListFragmentInteraction(item1,view);
                }
            });
            vf.addView(holder);
        }
    }

    private int calculateSize(int imgHeight, int imgWidth, int height, int width) {
        int sample=1;
        if(imgHeight>height||imgWidth>width){
            final int halfImgH = imgHeight/2;
            final int halfImgW = imgWidth/2;
            while((halfImgH/sample)>height&&(halfImgW/sample)>width)
                sample *=2;
        }
        return sample<=4?sample:4;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.view_all,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.view_all_menu) {
            if(((String) item.getTitle()).equalsIgnoreCase("view all")) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_container,ProductFragment2.newInstance(1,mCallId));
                ft.commit();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initHome(View view) {
        FrameLayout img;
        img = (FrameLayout) view.findViewById(R.id.catg1);
        img.setOnClickListener(this);
        img = (FrameLayout) view.findViewById(R.id.catg2);
        img.setOnClickListener(this);
        img = (FrameLayout) view.findViewById(R.id.catg3);
        img.setOnClickListener(this);
        img = (FrameLayout) view.findViewById(R.id.catg4);
        img.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            default:
            case R.id.catg1: callDetails(R.id.nav_cat1);
                break;
            case R.id.catg2: callDetails(R.id.nav_cat2);
                break;
            case R.id.catg3: callDetails(R.id.nav_cat3);
                break;
            case R.id.catg4: callDetails(R.id.nav_cat4);
                break;
        }
    }

    private void callDetails(int it){
        Intent i = new Intent(getContext(),MainActivity.class);
        i.putExtra("callId",it);
        getActivity().finish();
        startActivity(i);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item, View view);
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
