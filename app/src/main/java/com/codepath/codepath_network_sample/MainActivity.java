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
import java.net.URL;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static int IDX = 0;
    private TextView txIp;
    private ImageView ivSample;
    private Button btnGetImage;
    private Button btnGetJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ivSample = (ImageView) findViewById(R.id.ivSample);
        txIp = (TextView) findViewById(R.id.txIP);
        btnGetImage = (Button) findViewById(R.id.btnGetImage);
        btnGetJson = (Button) findViewById(R.id.btnGetIp);
        btnGetImage.setOnClickListener(this);
        btnGetJson.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnGetImage) {
            Picasso.with(MainActivity.this).load("http://httpbin.org/image").into(ivSample);
        }else if(v.getId() == R.id.btnGetIp) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://httpbin.org/get",new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        txIp.setText(response.getString("origin"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
