package com.example.madlibs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InputActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TextView title;
    private Button generate_button;
    private ArrayList<EditText> entries_editText = new ArrayList<EditText>();
    private String story = "";
    private String[] values;
//    private ArrayList<Boolean> editText_complete = new ArrayList<Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        linearLayout = findViewById(R.id.linearLayout);
        title = findViewById(R.id.title_textView);
        generate_button = findViewById(R.id.generate_button);
//        editText_complete = new boolean[getIntent().getStringArrayExtra("input").length];

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        values = intent.getStringArrayExtra("value");
//        intent.getStringArrayExtra("input")
//        intent.getStringArrayExtra("value")
        for(int i = 0; i < intent.getStringArrayExtra("input").length; i++) {
            TextView textView = new TextView(this);
            textView.setText(intent.getStringArrayExtra("input")[i]);
            EditText editText = new EditText(this);
            entries_editText.add(editText);
            linearLayout.addView(textView);
            linearLayout.addView(editText);

        }

        generate_button.setOnClickListener(v -> {
            ArrayList<Boolean> editText_complete = new ArrayList<Boolean>();
            for(int i = 0; i < entries_editText.size(); i++) {
                if(entries_editText.get(i).getText().toString().equals("")) {
                    editText_complete.add(false);
                }
                else {
                    editText_complete.add(true);
                }
//
//            if (entries_editText.get(i).getText() == null) {
//               editText_complete.add(false);
//            }
//            else {
//                editText_complete.add(true);
//            }
            }
            if(editText_complete.contains(false)) {
                Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                launchNextActivity(v);
            }
        });
    }

    public void launchNextActivity(View view) {
        Intent intent = new Intent(InputActivity.this, DisplayActivity.class);
//
            createStory(entries_editText, values);
            intent.putExtra("story", story);
            startActivity(intent);

//        if(entries_editText.g) {
//            Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
//            toast.show();
//        }
//        else {
//            createStory(entries_editText, values);
//            intent.putExtra("story", story);
//            startActivity(intent);
//        }
    }

    public String createStory(ArrayList<EditText> user_inputs, String[] words) {
        for(int i = 0; i < user_inputs.size(); i++) {
            story += words[i] + user_inputs.get(i).getText().toString();
        }
        story += words[words.length - 1];
        return story;
    }
}
