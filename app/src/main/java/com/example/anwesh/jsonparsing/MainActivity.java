package com.example.anwesh.jsonparsing;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void loadRoomies(View view) {
        Log.i("Button" , "Button pressed");
        DownloadTask task = new DownloadTask();
        task.execute("http://10.0.2.2:8086/testjson");
    }


    public class DownloadTask extends AsyncTask<String, Void ,String>{

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data != -1){
                    char current = (char)data;
                    result += current;
                    data = reader.read();

                }
                return result;
            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {

                JSONObject jsonObject = new JSONObject(result);
                String roomies = jsonObject.getString("Roomies");
                Log.i("Website content" , roomies);
                JSONArray jsonArray = new JSONArray(roomies);
                TextView newText;
                for (int i=0 ; i < jsonArray.length() ; i++) {
                    JSONObject roomie = jsonArray.getJSONObject(i);
                    Log.i("Roomie: " , roomie.getString("First Name") + " " + roomie.getString("Last Name"));
                    newText = (TextView)findViewById(R.id.roomie1+i);
                    newText.setText(roomie.getString("First Name") + " " + roomie.getString("Last Name") );
                }


            }
            catch(JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
