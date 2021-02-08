package com.example.madlibs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private Button button_start;

    private static final String api_url = "http://madlibz.herokuapp.com/api/random";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize button to go to next activity
        button_start = findViewById(R.id.started_button);

        // create log to keep track of error
        Log.d("MainActivity", "Error when clicking button");
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNextActivity(v);
            }
        });
    }

    public void launchNextActivity(View view) {
        // create a new intent
//        Intent intent  = new Intent(this, InputActivity.class);

        // set the header because of the api endpoint
        client.addHeader("Accept", "application/json");

        // send a get request to the api url
        client.get(api_url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    String[] intent_blanks = new String[json.getJSONArray("blanks").length()];
                    String[] value = new String[json.getJSONArray("value").length() - 1];
                    Intent intent = new Intent(MainActivity.this, InputActivity.class);
//                    Intent second_intent = new Intent(MainActivity.this, DisplayActivity.class);
                    intent.putExtra("title", json.getString("title"));
                    for(int i = 0; i < value.length; i++) {
                        if(i < value.length- 1) { // i < 10
                            intent_blanks[i] = json.getJSONArray("blanks").getString(i); // length = 10
                        }
                        value[i] = json.getJSONArray("value").getString(i); // length = 11

                    }
//                    intent.putExtra("input", json.getString("blanks"));
                    intent.putExtra("input", intent_blanks);
                    intent.putExtra("value", value);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("api_error", "error with grabbing JSON object");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("activity_fail", "error launching next activity");
            }
        });
    }
}