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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

    private class GetJSONObjectAsyncTask extends AsyncTask<String,Integer,JSONObject>{

        private final int id = IDX++;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            //create a progress dialog
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Proceeding");

            //Allow user to cancel by touch outside the dialog
            dialog.setCanceledOnTouchOutside(true);

            //Call cancel method to cancel this task.
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    cancel(true);
                }
            });
            dialog.setMessage("0%");
            dialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... params) {
            Log.d("AsyncTask","JsonTask#"+id+" - doInBackground "+Thread.currentThread().getName());
            String url = params[0];

            for(int i=1;i<=100;i+=10) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Update the progress; This will invoke onProgressUpdate method on Ui thread.
                publishProgress(i);
            }

            return getJsonFromAPI(url);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            //Refresht while progress is updated.
            dialog.setMessage(values[0]+"%...");
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            //Dismiss the dialog.
            dialog.dismiss();
            Log.d("AsyncTask","JsonTask#"+id+" - onPostExecute "+Thread.currentThread().getName());
            try {
                txIp.setText(jsonObject.getString("origin"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled() {
            //Dismiss the dialog and show toast to notify user the task has been canceled.
            dialog.dismiss();
            Toast.makeText(MainActivity.this,"Cancel",Toast.LENGTH_SHORT).show();
        }
    }

    private class GetBitmapAsyncTask extends AsyncTask<String,Void,Bitmap>{

        private final int id = IDX++;

        @Override
        protected Bitmap doInBackground(String... params) {
            Log.d("AsyncTask","BmpTask#"+id+" - doInBackground "+Thread.currentThread().getName());
            String url = params[0];

            return getImageFromAPI(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d("AsyncTask","BmpTask#"+id+" - onPostExecute "+Thread.currentThread().getName());
            ivSample.setImageBitmap(bitmap);
        }
    }



    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnGetImage) {
            new GetBitmapAsyncTask().execute("http://httpbin.org/image");
        }else if(v.getId() == R.id.btnGetIp) {
            new GetJSONObjectAsyncTask().execute("http://httpbin.org/get");
        }

    }

    /*
    Data url: http://httpbin.org/get
    JSON sample:
    {
        "args": {...},
        "headers": {...},
        "origin": "123.51.183.156",
        "url": "http://httpbin.org/get"
    }
    */

    private JSONObject getJsonFromAPI(String url) {
        //The links that we can fetch json data.
        URL targetUrl = null;
        HttpURLConnection conn = null;
        InputStream in = null;
        JSONObject object = null;
        try {

            targetUrl = new URL(url);
//            targetUrl = new URL("http://httpbin.org/get");


            //1. Step 1, open an connection linked to the specific url.
            conn = (HttpURLConnection) targetUrl.openConnection();

            //2. Step 2, open an input stream so that we can fetch data from target url.
            in = conn.getInputStream();

            //3. Step 3, Read, concat and parse data into JsonObject through stream.
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String tmp ;
            while ((tmp = reader.readLine())!=null){
                stringBuilder.append(tmp);
            }
            object = (JSONObject) new JSONTokener(stringBuilder.toString()).nextValue();

        } catch (IOException | JSONException e) {
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

        return object;

    }

    //Image url: http://httpbin.org/image
    private Bitmap getImageFromAPI(String url)  {

        //The links that we can fetch image.
        URL targetUrl = null;
        HttpURLConnection conn = null;
        InputStream in = null;
        Bitmap bmp = null;
        try {
            targetUrl = new URL(url);

            //1. Step 1, open an connection linked to the specific url.
            conn = (HttpURLConnection) targetUrl.openConnection();

            //2. Step 2, open an input stream so that we can fetch data from target url.
            in = conn.getInputStream();

            //3. Step 3,Get the data and display it.
            bmp = BitmapFactory.decodeStream(in);



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

        return bmp;

    }
}
