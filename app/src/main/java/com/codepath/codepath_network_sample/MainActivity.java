package com.codepath.codepath_network_sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivSample;
    private Button btnAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        ivSample = (ImageView) findViewById(R.id.ivSample);
        btnAction = (Button) findViewById(R.id.btnAction);
        btnAction.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        getImageFromAPI();

    }

    private void getImageFromAPI()  {

        //The links that we can fetch image.
        URL targetUrl = null;
        HttpURLConnection conn = null;
        InputStream in = null;
        try {
            targetUrl = new URL("http://httpbin.org/image");

            //1. Step 1, open an connection linked to the specific url.
            conn = (HttpURLConnection) targetUrl.openConnection();

            //2. Step 2, open an input stream so that we can fetch data from target url.
            in = conn.getInputStream();

            //3. Step 3,Get the data and display it.
            Bitmap bmp = BitmapFactory.decodeStream(in);
            ivSample.setImageBitmap(bmp);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Release stream
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Release the httpConnection
            if(conn!=null){
                conn.disconnect();
            }

        }

    }
}
