package com.sloper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    ImageView mImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mImg= (ImageView) findViewById(R.id.view_pager);
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("PROD_ID");
        String name = extras.getString("PROD_NAME");
        String price = extras.getString("PROD_PRICE");
        Drawable imgRes = (Drawable) extras.get("IMG");
        String size = extras.getString("PROD_SIZ");
        String colors = extras.getString("PROD_COL");
        mImg.setImageDrawable(imgRes);
        if(mImg.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.wait))){
            DownloadImageTask downloadImageTask=new DownloadImageTask(mImg);
            downloadImageTask.execute("http://sloper.nakodatextiles.com/uploads/"+name+"/base.jpg");
        }
        TextView tv = (TextView)findViewById(R.id.product_name);
        tv.setText(name);
        tv = (TextView) findViewById(R.id.price);
        tv.setText(price+" INR");
        tv = (TextView)findViewById(R.id.description);
        tv.setText("Available colors:"+colors+"\n"+"Sizes:"+size+"\nProduct Description goes here");
        LinearLayoutCompat ll = (LinearLayoutCompat) findViewById(R.id.thumbnails);
        for(int i=0;i<3;i++){
            ImageView img = new ImageView(this);
            img.setImageDrawable(imgRes);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Clicked on thumbnail",Toast.LENGTH_SHORT).show();
                }
            });
            ll.addView(img);
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
