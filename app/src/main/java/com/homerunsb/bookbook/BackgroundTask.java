package com.homerunsb.bookbook;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by homer on 2016-06-16.
 */
public class BackgroundTask extends AsyncTask<String, Integer, BookInformationData> {
    // Params, Progress, Result
    // doInBackground에 String 파라미터로 전달하고, 진행을 Float로 보고하고, 결과를 Boolean으로 반환하고 싶다면,
    // <String, Float, Boolean>
    static final String TAG = BackgroundTask.class.getSimpleName();
    BookInformationData resultBookInfoData;
    String xml;
    Context context;

    BackgroundTask(){}
    BackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected BookInformationData doInBackground(String ... isbn_id) {
        resultBookInfoData = new BookInformationData(this.context);
        Log.d(TAG, "************************************** AsyncTask doInBackground : start");
        xml = resultBookInfoData.getNaverBookInfo(isbn_id[0]);
        try {
            BookXmlPullParser xpp = new BookXmlPullParser();
            resultBookInfoData = xpp.parseXml(xml);
        } catch (Exception e) {
        }
        Log.d(TAG, "************************************** AsyncTask doInBackground : end");
        return resultBookInfoData;
    }

    @Override
    protected void onPostExecute(BookInformationData bookInformationData) {
        //Log.d(TAG, "************************************** AsyncTask onPostExecute : " + bookInformationData.getTitle());
        super.onPostExecute(bookInformationData);
    }

}

