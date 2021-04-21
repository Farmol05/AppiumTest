package com.example.e_event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent user = getIntent();
        String e = user.getStringExtra("name");
        tv = findViewById(R.id.home_tv);
        tv.setText("Welcome "+e);

    }


}