package com.homerunsb.bookbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.homerunsb.bookbook.barcode.PopupBookInfo;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {
    static final String TAG = MainActivity.class.getSimpleName();
    String isbn_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_scan = (Button) findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent("com.homerunsb.bookbook.SCAN");
                //매니페스트에 intent-filter 부분 호출.
                intent.putExtra("SCAN_MODE", "ALL");
                startActivityForResult(intent, 0);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                isbn_id = data.getStringExtra("SCAN_RESULT"); //scan result
                Toast.makeText(this, isbn_id, Toast.LENGTH_LONG).show();
                try {
                    BackgroundTask backgroundTask = new BackgroundTask(this);
                    BookInformationData bd = backgroundTask.execute(isbn_id).get();

                    //Log.d(TAG, "====================DATA 얻기 완료========================");
                    //이미 commit 후 돌리기 테스트
                    Intent intent = new Intent(MainActivity.this, PopupBookInfo.class);
                    intent.putExtra("resultBookInfoData", bd);
                    startActivity(intent);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
