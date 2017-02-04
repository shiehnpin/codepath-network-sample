package com.codepath.codepath_network_sample;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ImageView ivSample;
    private Button btnGetImage;
    private static final String IMG_URL =
            "http://wallpaperswide.com/download/beautiful_space_view-wallpaper-2560x1600.jpg";

    private void disableNetworkOnUiThreadRestrict() {
        StrictMode.ThreadPolicy threadPolicy =
                new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(threadPolicy);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivSample = (ImageView) findViewById(R.id.ivSample);
        btnGetImage = (Button) findViewById(R.id.btnGetImage);
        btnGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo
            }
        });

    }

}
