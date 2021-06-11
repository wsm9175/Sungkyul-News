


package com.example.news;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class InformationActivity extends AppCompatActivity {

    private TextView tv_id,tv_pass,tv_name,tv_email;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        tv_id = (TextView)findViewById(R.id.tv_id);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_email = (TextView)findViewById(R.id.tv_email);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        final String userID = user.getId();
        final String userName = user.getName();
        final String userEmail = user.getEmail();

        tv_id.setText(userID);
        tv_name.setText(userName);
        tv_email.setText(userEmail);
    }
}

