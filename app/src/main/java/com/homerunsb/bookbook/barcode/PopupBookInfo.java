package com.homerunsb.bookbook.barcode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.homerunsb.bookbook.BackgroundTask;
import com.homerunsb.bookbook.BackgroundTaskForImage;
import com.homerunsb.bookbook.BookInformationData;
import com.homerunsb.bookbook.R;

public class PopupBookInfo extends Activity {
    static final String TAG = "PopupBookInfo";
    ImageView imgBookCover;
    TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_book_info);
        textview = (TextView)findViewById(R.id.textBookInfo);
        imgBookCover = (ImageView)findViewById(R.id.imgBookCover);

        Intent intent =  getIntent();
        //ArrayList<BookInformationData> resultArrayList = (ArrayList<BookInformationData>) intent.getSerializableExtra("resultArrayList");
        //Log.d(TAG, "전달받은 인텐트 안의 어레이 리스트 사이즈 1  : " + resultArrayList.size());


        BookInformationData bookInformationData = (BookInformationData) intent.getSerializableExtra("resultBookInfoData");
        String imgUrl = bookInformationData.getImage();
        BackgroundTaskForImage bt = new BackgroundTaskForImage();
        Bitmap bitmap = null;

        try{
            bitmap = bt.execute(imgUrl).get();
        }catch(Exception e){
            e.printStackTrace();
        }

        //Log.d(TAG, "타이틀  : " + bookInformationData.getImage());

        imgBookCover.setImageBitmap(bitmap);
        textview.setText(bookInformationData.getTitle());

    }
}