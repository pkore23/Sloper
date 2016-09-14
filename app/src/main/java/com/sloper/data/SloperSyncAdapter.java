package com.sloper.data;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.sloper.MainActivity;
import com.sloper.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Pradnesh Kore on 20-07-2016.
 */
public class SloperSyncAdapter extends AbstractThreadedSyncAdapter {
    ContentResolver mContentResolver;
    Context mContext;
    public final String LOG_TAG = SloperSyncAdapter.class.getSimpleName();
    // Interval at which to sync with the weather, in seconds.
    // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_INTERVAL = 60 * 720;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
    private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;


    public SloperSyncAdapter(Context context, boolean autoInit) {
        super(context, autoInit);
        mContentResolver = context.getContentResolver();
        mContext = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        SharedPreferences pref = mContext.getSharedPreferences("sloper_prefs", Context.MODE_PRIVATE);
//        Toast.makeText(mContext,"Sloper Sync Initiated",Toast.LENGTH_SHORT).show();
        final Long lastUpdate = pref.getLong("last_update", 0);
        Log.d(LOG_TAG, "Start Sync...");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String data = "";
        long newUpdate = 0;
        try {
            final String SLOPER_API = "http://sloper.nakodatextiles.com/index.php";
            Uri uri = Uri.parse(SLOPER_API).buildUpon().appendQueryParameter("app_id", getContext().getString(R.string.sloper_api))
                    .appendQueryParameter("service", "update").build();
            URL url = new URL(uri.toString());

            // Create the request to server, and open the connection
            Log.d(LOG_TAG, uri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                Log.d(LOG_TAG, "No data found from server");
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                Log.e(LOG_TAG, "Could not get update message");
                return;
            }
            data = buffer.toString();
            JSONObject jObject = new JSONObject(data);
            newUpdate = jObject.getLong("updateDate");
            if (lastUpdate < newUpdate) {
                //read new data
                uri = Uri.parse(SLOPER_API).buildUpon().appendQueryParameter("app_id", getContext().getString(R.string.sloper_api))
                        .appendQueryParameter("service", "data")
                        .build();
                url = new URL(uri.toString());

                // Create the request to server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // Read the input stream into a String
                inputStream = urlConnection.getInputStream();
                buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    Log.d(LOG_TAG, "No data found from server");
                    return;
                }
                String state = Environment.getExternalStorageState();
                boolean dirMount = state.equals(Environment.MEDIA_MOUNTED);

                if (dirMount) {
                    File filePath = mContext.getExternalFilesDir(null);
                    File JSonFile = new File(filePath, "json_data.json");
                    if (!JSonFile.exists()) {
                        JSonFile.createNewFile();
                    }
                    if (JSonFile != null && JSonFile.exists()) {
                        JSonFile.setWritable(true);
                        OutputStream os = new FileOutputStream(JSonFile, false);
                        byte[] data1 = new byte[1024];
                        int len = 0;
                        while ((len = inputStream.read(data1)) > 0) {
                            os.write(data1);
                        }
                        os.close();
                        getImages();
                    }
                } else {
                    Log.e(LOG_TAG + "_SAVE_FAIL", "Media not mounted try again later.");
                    return;
                }
            }

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG + "_FAILED", "Incorrect Url");
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.e(LOG_TAG + "_FAILED", "Protocol Exception");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(LOG_TAG + "_FAILED", "IO Exception occured");
            e.printStackTrace();
            return;
        } catch (JSONException e) {
            Log.e(LOG_TAG + "_FAILED", "Json Exception: " + e.getMessage());
            Log.d(LOG_TAG + "_DATA", data);
            e.printStackTrace();
            return;
        } catch (Exception e) {
            Log.e(LOG_TAG + "_FAILED", "Unexpected error!");
            e.printStackTrace();
            return;
        }
        pref.edit().putLong("last_update", newUpdate).apply();
    }

    private void getImages() {
        File fPath = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        HttpURLConnection urlConnection = null;
        InputStream inputStream;
        BufferedReader reader = null;
        StringBuilder buffer;
        try {
            String SLOPER_API = "http://192.168.1.103/index.php";
            String SLOPER_IMAGES = "http://192.168.1.103/uploads/";
            Uri uri = Uri.parse(SLOPER_API).buildUpon().appendQueryParameter("app_id", getContext().getString(R.string.sloper_api))
                    .appendQueryParameter("service", "images").build();
            URL url;
            File dataPath = mContext.getExternalFilesDir(null);
            File dataFile = new File(dataPath, "json_data.json");
            FileInputStream fis = new FileInputStream(dataFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            StringBuilder buffer1 = new StringBuilder("");
            String line;
            while ((line = br.readLine()) != null) {
                buffer1.append(line);
            }
            JSONObject root = new JSONObject(buffer1.toString());
            JSONArray productsJson = root.getJSONArray("products");
            int i = 0;
            for (i = 0; i < productsJson.length(); i++) {
                JSONObject obj = (JSONObject) productsJson.get(i);
                String pid = obj.getString("pid");
                uri = uri.buildUpon()
                        .appendQueryParameter("pid", pid)
                        .build();
                url = new URL(uri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                StringBuffer imgListBuffer = new StringBuffer();
                BufferedReader imgs = new BufferedReader(new InputStreamReader(inputStream));
                String iLine;
                while ((iLine = imgs.readLine()) != null) {
                    imgListBuffer.append(iLine);
                    imgListBuffer.append("\n");
                }
                JSONObject images = new JSONObject(imgs.toString());
                JSONArray imges = images.getJSONArray("images");
                for (int iI = 0; iI < imges.length(); iI++) {
                    Uri u = Uri.parse(SLOPER_IMAGES).buildUpon()
                            .appendPath(pid)
                            .appendPath(imges.getString(iI))
                            .build();
                    Log.d(LOG_TAG,u.toString());
                    URL newImg = new URL(u.toString());
                    urlConnection = (HttpURLConnection) newImg.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    inputStream = urlConnection.getInputStream();
                    File img = new File(fPath.toString()+"/"+pid,"IMG_"+iI+".jpg");
                    if(img.exists())
                        img.delete();
                    img.mkdirs();
                    img.createNewFile();
                    FileOutputStream fOut = new FileOutputStream(img);
                    byte[] bufferData = new byte[1024];
                    long total = 0;
                    int count;
                    while((count=inputStream.read(bufferData))!=-1){
                        total+=count;
                        fOut.write(bufferData);
                    }
                    fOut.close();
                }
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}