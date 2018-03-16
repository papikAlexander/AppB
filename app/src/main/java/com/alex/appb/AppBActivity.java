package com.alex.appb;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alex.appb.services.MyIntentService;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AppBActivity extends AppCompatActivity {

    final Uri LINK_URI =  Uri.parse("content://com.alex.appa.providers.dbA/links");
    private int status = 3;
    private String link;
    private String data;

    final String LINK_LINK = "link";
    final String LINK_DATA = "data";
    final String LINK_STATUS = "status";
    int tabId;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        link = getIntent().getStringExtra("link");
        webView = (WebView) findViewById(R.id.webViewLink);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){


            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                status = 2;
                Toast.makeText(AppBActivity.this, "error code : " + error.getErrorCode(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                tabId = getIntent().getIntExtra("id", 0);
                data = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss").format(new Date());

                if (tabId == 1){

                    ContentValues cv = new ContentValues();
                    cv.put(LINK_LINK, link);
                    cv.put(LINK_DATA, data);
                    cv.put(LINK_STATUS, status);
                    getContentResolver().insert(LINK_URI, cv);
                    Toast.makeText(AppBActivity.this, "" + tabId, Toast.LENGTH_LONG).show();

                } else if (tabId == 2){
                    Toast.makeText(AppBActivity.this, "" + tabId, Toast.LENGTH_LONG).show();

                    switch (getIntent().getIntExtra("status", 0)){
                        case 1:

                            Intent intent = new Intent(AppBActivity.this, MyIntentService.class);
                            intent.putExtra("link", link);
                            startService(intent);

                            break;
                        case 2:
                            if (status != 2){
                                ContentValues cv = new ContentValues();
                                cv.put(LINK_DATA, data);
                                cv.put(LINK_STATUS, status);
                                getContentResolver().update(LINK_URI, cv, "link = ?", new String[]{link});

                            }
                            break;
                        case 3:
                            if (status != 3){
                                ContentValues cv = new ContentValues();
                                cv.put(LINK_DATA, data);
                                cv.put(LINK_STATUS, status);
                                getContentResolver().update(LINK_URI, cv, "link = ?", new String[]{link});
                            }
                    }
                } else
                    Toast.makeText(AppBActivity.this, "" + tabId, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                status = 1;
            }
        });

        webView.loadUrl(link);


    }


}
