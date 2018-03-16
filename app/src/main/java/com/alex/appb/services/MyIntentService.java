package com.alex.appb.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;



public class MyIntentService extends IntentService {

    final Uri LINK_URI =  Uri.parse("content://com.alex.appa.providers.dbA/links");
    Handler mHandler;
    String link;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {


            try {
                Thread.sleep(15000);

                link  = intent.getStringExtra("link");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyIntentService.this, "Ссылка " + link + " была удалена из базы данных", Toast.LENGTH_LONG).show();
                    }
                });


                getContentResolver().delete(LINK_URI, "link = ?", new String[]{link});
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
    }
}
