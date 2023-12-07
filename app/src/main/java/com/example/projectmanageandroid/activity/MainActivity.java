package com.example.projectmanageandroid.activity;

import android.content.Intent;
import android.view.View;
import com.example.projectmanageandroid.R;
import com.google.android.material.button.MaterialButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaterialButton buttonProjectActivity = findViewById(R.id.button_project_activity);
        buttonProjectActivity.setBackgroundColor(Color.parseColor("#4CAF50"));
        buttonProjectActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProjectActivity.class);
                startActivity(intent);
            }
        });
    }
}
