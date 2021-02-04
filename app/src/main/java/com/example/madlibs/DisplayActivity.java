package com.example.madlibs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {

    private TextView story;
    private Button goHome_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        story = findViewById(R.id.story_textview);
        goHome_button = findViewById(R.id.goHome_button);

        Intent intent = getIntent();
        story.setText(intent.getStringExtra("story"));

        goHome_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchNextActivity(v);
                }
            });
    }

    public void launchNextActivity(View view) {
        Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
