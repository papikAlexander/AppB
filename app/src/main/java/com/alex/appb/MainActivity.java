package com.alex.appb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Timer timer;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        timer.execute();

        textView = (TextView) findViewById(R.id.textView);
        text = textView.getText().toString();

    }

    class Timer extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... voids) {


            for (int i = 10; i >= 0; i--){

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            textView.setText(text + " " + values[0] + "c");

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finishAffinity();
        }
    }
}
