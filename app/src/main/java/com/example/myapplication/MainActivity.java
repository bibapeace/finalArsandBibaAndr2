package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText user_field;
    private Button main_btn;
    private TextView result_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_field = findViewById(R.id.user_field);
        main_btn = findViewById(R.id.main_btn);
        result_info = findViewById(R.id.result_info);

        main_btn.setOnClickListener(view -> {
            if(user_field.getText().toString().trim().equals(""))
                Toast.makeText(MainActivity.this, "Enter city", Toast.LENGTH_LONG).show();
            else{
                String city = user_field.getText().toString();
                String key = "f334eaa84d20f89a67c8ba8c7515e6e3";
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid="+ key + "&units=metric";

                new GetURLData().execute(url);
            }
        });
    }
    private class GetURLData extends AsyncTask<String, String, String> {

        protected void onPreExecute(){
            super.onPreExecute();
            result_info.setText("kute turshy...");
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                InputStream stream = connection.getInputStream();
                reader =  new BufferedReader( new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while((line = reader.readLine()) != null )
                    buffer.append(line).append("\n");
                return buffer.toString();

            }catch(MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if( connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }

            return null;
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result){
            try {
                JSONObject jsonObject = new JSONObject(result);
                result_info.setText("Temperature: " + jsonObject.getJSONObject("main").getDouble("temp")+"°" + "\n Feels like: " + jsonObject.getJSONObject("main").getDouble("feels_like") + "°" + "\n Wind speed: " + jsonObject.getJSONObject("wind").getDouble("speed") + "km/h" );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);


        }
    }
}