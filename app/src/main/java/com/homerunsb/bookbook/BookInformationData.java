package com.homerunsb.bookbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by homer on 2016-06-13.
 */
public class BookInformationData implements Serializable {
    private static final String TAG = BookInformationData.class.getSimpleName();
    private String title, link, image, author, publisher, pubdate, description, price, discount, isbn;
    Context context;

    public BookInformationData(){}
    public BookInformationData(Context context){
        this.context = context;
    }
    public BookInformationData(String title, String link, String image, String author, String publisher
                             , String pubdate, String description, String price, String discount, String isbn) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.author = author;
        this.publisher = publisher;
        this.pubdate = pubdate;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.isbn = isbn;
    }

    public String getTitle() {
        Log.d(TAG, "///////////////////getTitle///////////// : " + title);
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPubdate() {
        return pubdate;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setTitle(String title) { this.title = title; }

    public void setLink(String link) {
        this.link = link;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    public boolean isNetworkConnected(){
        boolean isConnected = false;

        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        Log.d(TAG, "mobile isConnected : " + mobile.isConnected() );
        Log.d(TAG, "wifi isConnected : " + wifi.isConnected() );

        if (mobile.isConnected() || wifi.isConnected()){
            isConnected = true;
        }else{
            isConnected = false;
        }
        Log.d(TAG, "isConnected : " + isConnected );

        return isConnected;
    }

    protected String getNaverBookInfo(String isbn_id) {

        String clientId = "yd35ikZRLERQDz19SeSB";
        String clientSecret = "ZFe_6i2fF2";
        String urlString ;
        HttpsURLConnection conn = null;
        String result = null;
        int resCode ;

        try {
            String query = URLEncoder.encode("-", "UTF-8"); //필수값
            urlString = "https://openapi.naver.com/v1/search/book_adv.xml?query=" + query + "&d_isbn=" + isbn_id;
            URL url = new URL(urlString);
            Log.d(TAG, "************************************** urlString : " + urlString);

            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Naver-Client-Id", clientId);
            conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setRequestProperty("Accept", "*/*");


            Log.d(TAG, "************************************** conn : " + conn);
            Log.d(TAG, "************************************** conn method : " + conn.getRequestMethod());

            conn.setDoInput(true); //input을 사용하도록 설정 (default : true)

            //conn.setDoOutput(true); //output을 사용하도록 설정 (default : false)
            //conn.setConnectTimeout(60); //타임아웃 시간 설정 (default : 무한대기)
            //Log.d(TAG, "==========================" + !isNetworkConnected());

            if (!isNetworkConnected()) {
                //민준이한테 물어볼꺼 토스트 실행이 안됨
                //Can't create handler inside thread that has not called Looper.prepare()

                //Log.d(TAG, "==========================" + !isNetworkConnected());
                //Toast.makeText(context, "네트워크에 연결되지 않았습니다. 스캔한 정보를 불러오려면 네트워크에 연결되어야 합니다.",Toast.LENGTH_SHORT).show();
/*                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("네트워크 연결 오류").setMessage("네트워크 연결 상태 확인 후 다시 시도해 주십시요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick( DialogInterface dialog, int which )
                            {
                                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                context.startActivity(intent);
                            }
                        }).show();*/
            } else {
                conn.connect();
                resCode = conn.getResponseCode();
            }

            InputStream is = conn.getInputStream();        //input스트림 개방
            StringBuilder builder = new StringBuilder();   //문자열을 담기 위한 객체
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));  //문자열 셋 세팅
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            result = builder.toString();
            Log.d(TAG, "************************************** result :" + result);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }


}