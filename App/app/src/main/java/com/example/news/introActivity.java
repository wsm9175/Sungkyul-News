<<<<<<< HEAD:App/app/src/main/java/com/skuniv/myapplication/introActivity.java
package com.skuniv.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.skuniv.myapplication.LoginActivity;
import com.example.news.R;

public class introActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //액션 바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //2초 후 인트로 제거
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(com.skuniv.myapplication.introActivity.this, LoginActivity.class);
                startActivity(intent);

                finish();
            }
        }, 2000);

    }
}
=======
package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class introActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //액션 바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //2초 후 인트로 제거
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(com.example.news.introActivity.this, LoginActivity.class);
                startActivity(intent);

                finish();
            }
        }, 2000);

    }
}
>>>>>>> W_branch:App/app/src/main/java/com/example/news/introActivity.java
