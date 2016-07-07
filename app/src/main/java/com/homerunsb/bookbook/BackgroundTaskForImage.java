package com.homerunsb.bookbook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by homer on 2016-06-16.
 */
public class BackgroundTaskForImage extends AsyncTask<String, Integer, Bitmap> {
    static final String TAG = BackgroundTaskForImage.class.getSimpleName();
    Bitmap bitmap;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        try{
            URL url = new URL(urls[0]);
            Log.d(TAG, "url : " + url);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bmImg) {
        super.onPostExecute(bmImg);
    }
}
